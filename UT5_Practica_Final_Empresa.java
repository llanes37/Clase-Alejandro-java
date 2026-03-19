/*
 * ******************************************************************************************
 *                     TEORIA Y CONCEPTOS: CLASES, OBJETOS Y ARRAYS DE OBJETOS
 * ------------------------------------------------------------------------------------------
 * En esta practica vas a trabajar un ejercicio muy tipico de 1 de DAM:
 * - Crear varias clases relacionadas entre si.
 * - Instanciar objetos a partir de esas clases.
 * - Guardar objetos dentro de arrays.
 * - Usar una clase intermedia para modelar una relacion real.
 * - Separar responsabilidades en metodos cortos y claros.
 *
 * Enunciado base del ejercicio:
 * Queremos modelar una empresa. La empresa tiene varios departamentos y cada
 * departamento guarda un array fijo de empleados. Debes poder consultar datos,
 * anadir empleados, buscar empleados y calcular el gasto salarial.
 *
 * Idea clave:
 * Empresa -> contiene Departamentos[]
 * Departamento -> contiene Empleado[]
 * Empleado -> representa cada persona de la empresa
 *
 * Este patron aparece mucho en examenes porque obliga a entender:
 * - composicion entre objetos
 * - arrays de objetos
 * - encapsulacion
 * - constructores
 * - paso de objetos como parametros
 ******************************************************************************************
 */

/*
 * TEORIA GLOBAL
 * ------------------------------------------------------------------------------------------
 * 1) Una clase es el molde. Un objeto es la instancia real creada con ese molde.
 * 2) Un array de objetos no guarda "datos sueltos", sino referencias a objetos.
 * 3) La clase intermedia evita meter todo en una sola clase gigante.
 * 4) Cada clase debe tener una responsabilidad clara:
 *    - Empresa organiza departamentos.
 *    - Departamento organiza empleados.
 *    - Empleado guarda los datos de una persona.
 * 5) Como usamos arrays fijos, hay que controlar la posicion libre y el limite.
 */

import java.util.Scanner;

public class UT5_Practica_Final_Empresa {

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        Empresa empresa = crearEmpresaDemo();
        int opcion;

        // *INFO: El menu permite practicar cada operacion por separado y repetirla varias veces.
        // !IMPORTANT: Un do-while encaja muy bien en consola porque asegura que el menu se vea al menos una vez y evita salir antes de tiempo por error.
        // ?QUESTION: Que pasaria si metieras toda la logica del programa directamente dentro del switch del main?
        do {
            mostrarMenu();
            opcion = leerEnteroEnRango("Elige una opcion: ", 0, 6);

            switch (opcion) {
                case 1:
                    mostrarResumenEmpresa(empresa);
                    break;
                case 2:
                    mostrarDepartamentosYEmpleados(empresa);
                    break;
                case 3:
                    contratarEmpleado(empresa);
                    break;
                case 4:
                    buscarEmpleado(empresa);
                    break;
                case 5:
                    mostrarGastoSalarial(empresa);
                    break;
                case 6:
                    mostrarDepartamentoConMasEmpleados(empresa);
                    break;
                case 0:
                    System.out.println("Fin del programa.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }

            if (opcion != 0) {
                pausar();
            }
        } while (opcion != 0);

        SC.close();
    }

    /*
     * MICRO-TEORIA: crear datos de prueba
     * Este metodo monta objetos reales para no tener que introducir todo a mano
     * cada vez que ejecutes el programa.
     */
    public static Empresa crearEmpresaDemo() {
        // *INFO: Creamos la empresa y sus departamentos por codigo para que el alumno pueda empezar a probar desde el minuto uno.
        // !IMPORTANT: Si no inicializas los arrays con objetos reales, luego intentaras trabajar con null y saltaran errores tipicos como NullPointerException.
        // TODO: Cambia los nombres de departamentos o el numero maximo de empleados y observa como afecta al programa.
        Empresa empresa = new Empresa("TechNova", "B12345678", 3);

        Departamento desarrollo = new Departamento("Desarrollo", 4);
        Departamento sistemas = new Departamento("Sistemas", 3);
        Departamento marketing = new Departamento("Marketing", 3);

        desarrollo.agregarEmpleado(new Empleado(1, "Ana", 24, "Programadora Junior", 1850.0));
        desarrollo.agregarEmpleado(new Empleado(2, "Luis", 29, "Programador Backend", 2100.0));
        sistemas.agregarEmpleado(new Empleado(3, "Marta", 31, "Tecnica de sistemas", 2000.0));
        marketing.agregarEmpleado(new Empleado(4, "Diego", 27, "Community Manager", 1700.0));

        empresa.agregarDepartamento(desarrollo);
        empresa.agregarDepartamento(sistemas);
        empresa.agregarDepartamento(marketing);

        return empresa;
    }

    /*
     * MICRO-TEORIA: menu principal
     * Un menu separa la navegacion de la logica de negocio y hace el programa
     * mas facil de probar.
     */
    public static void mostrarMenu() {
        // *INFO: Las opciones estan pensadas para tocar las operaciones mas comunes de un examen practico.
        // !IMPORTANT: Incluir "0. Salir" es una convencion simple que evita confundir al usuario con formas raras de cierre.
        // TODO: Anade una opcion para borrar un empleado buscando por id.
        System.out.println("\n================ PRACTICA FINAL POO =================");
        System.out.println("1. Ver resumen de la empresa");
        System.out.println("2. Ver departamentos y empleados");
        System.out.println("3. Contratar empleado");
        System.out.println("4. Buscar empleado por nombre");
        System.out.println("5. Mostrar gasto salarial total");
        System.out.println("6. Ver departamento con mas empleados");
        System.out.println("0. Salir");
    }

    /*
     * MICRO-TEORIA: mostrar datos agregados
     * Un objeto principal puede delegar parte del trabajo en sus objetos internos.
     */
    public static void mostrarResumenEmpresa(Empresa empresa) {
        // *INFO: Este metodo enseña como pedir informacion global a un objeto compuesto por otros objetos.
        // !IMPORTANT: El resumen se apoya en metodos de Empresa para no duplicar calculos en varias partes del programa, que es un error tipico de diseno.
        // ?QUESTION: Por que es mejor llamar a empresa.calcularGastoSalarialTotal() que sumar salarios directamente aqui?
        System.out.println("\n--- RESUMEN DE EMPRESA ---");
        System.out.println("Nombre: " + empresa.getNombre());
        System.out.println("CIF: " + empresa.getCif());
        System.out.println("Departamentos creados: " + empresa.getTotalDepartamentos());
        System.out.println("Empleados totales: " + empresa.calcularTotalEmpleados());
        System.out.printf("Gasto salarial total: %.2f euros%n", empresa.calcularGastoSalarialTotal());
    }

    /*
     * MICRO-TEORIA: recorrer arrays de objetos
     * Para ver todos los datos, normalmente recorres primero el array principal
     * y luego el array interno de cada objeto.
     */
    public static void mostrarDepartamentosYEmpleados(Empresa empresa) {
        // *INFO: Aqui practicamos un recorrido en dos niveles: departamentos y, dentro, empleados.
        // !IMPORTANT: Solo recorremos hasta los elementos realmente ocupados; usar todo el length sin control mostraria null y confundiria mucho al alumno.
        // TODO: Muestra tambien el salario medio de cada departamento.
        System.out.println("\n--- DEPARTAMENTOS Y EMPLEADOS ---");

        for (int i = 0; i < empresa.getTotalDepartamentos(); i++) {
            Departamento departamento = empresa.getDepartamento(i);
            System.out.println("\nDepartamento " + (i + 1) + ": " + departamento.getNombre());
            System.out.println("Empleados guardados: " + departamento.getTotalEmpleados() + "/" + departamento.getCapacidadMaxima());
            departamento.mostrarEmpleados();
        }
    }

    /*
     * MICRO-TEORIA: alta de objetos nuevos
     * En consola, lo habitual es pedir datos, crear el objeto y enviarlo a la
     * clase que lo debe almacenar.
     */
    public static void contratarEmpleado(Empresa empresa) {
        // *INFO: Este metodo modela el flujo clasico de "leer datos -> crear objeto -> guardarlo en un array".
        // !IMPORTANT: La validacion previa evita crear empleados para un departamento lleno, que es un fallo tipico cuando se trabaja con arrays fijos.
        // ?QUESTION: Si usaras ArrayList en lugar de array, que parte de este metodo se simplificaria?
        System.out.println("\n--- CONTRATAR EMPLEADO ---");
        mostrarSoloDepartamentos(empresa);

        int opcionDepartamento = leerEnteroEnRango("Elige departamento: ", 1, empresa.getTotalDepartamentos());
        Departamento departamento = empresa.getDepartamento(opcionDepartamento - 1);

        if (departamento.estaLleno()) {
            System.out.println("No se puede anadir mas personal. El departamento esta completo.");
            return;
        }

        int id = leerEnteroMinimo("Introduce id del empleado: ", 1);
        String nombre = leerTextoNoVacio("Introduce nombre: ");
        int edad = leerEnteroMinimo("Introduce edad: ", 16);
        String puesto = leerTextoNoVacio("Introduce puesto: ");
        double salario = leerDoubleMinimo("Introduce salario: ", 0.0);

        Empleado nuevoEmpleado = new Empleado(id, nombre, edad, puesto, salario);
        boolean agregado = departamento.agregarEmpleado(nuevoEmpleado);

        if (agregado) {
            System.out.println("Empleado contratado correctamente en " + departamento.getNombre() + ".");
        } else {
            System.out.println("No se ha podido contratar al empleado.");
        }
    }

    /*
     * MICRO-TEORIA: busqueda secuencial
     * Cuando trabajas con arrays, lo mas habitual al principio es buscar
     * recorriendo posicion por posicion.
     */
    public static void buscarEmpleado(Empresa empresa) {
        // *INFO: La busqueda secuencial es la tecnica basica cuando aun no trabajas con estructuras mas avanzadas.
        // !IMPORTANT: Comparar con equalsIgnoreCase evita un error tipico: creer que "ana" y "Ana" son nombres distintos por la mayuscula inicial.
        // TODO: Cambia esta busqueda por id y compara cual te parece mas fiable.
        System.out.println("\n--- BUSCAR EMPLEADO ---");
        String nombreBuscado = leerTextoNoVacio("Introduce el nombre a buscar: ");

        Empleado empleadoEncontrado = empresa.buscarEmpleadoPorNombre(nombreBuscado);

        if (empleadoEncontrado == null) {
            System.out.println("No se ha encontrado ningun empleado con ese nombre.");
        } else {
            System.out.println("Empleado encontrado:");
            System.out.println(empleadoEncontrado);
        }
    }

    /*
     * MICRO-TEORIA: calculos agregados
     * Muchas veces un examen pide calcular totales a partir de los objetos
     * almacenados en un array.
     */
    public static void mostrarGastoSalarial(Empresa empresa) {
        // *INFO: El gasto salarial sale de sumar salarios de todos los empleados a traves de los departamentos.
        // !IMPORTANT: El calculo se deja dentro del modelo para mantener el main limpio y evitar repetir for anidados en varios sitios.
        // TODO: Crea otra opcion que muestre solo el gasto de un departamento concreto.
        System.out.println("\n--- GASTO SALARIAL ---");
        System.out.printf("La empresa gasta %.2f euros en salarios.%n", empresa.calcularGastoSalarialTotal());
    }

    /*
     * MICRO-TEORIA: comparar objetos del array
     * Otro ejercicio muy comun es localizar el elemento con un valor maximo o minimo.
     */
    public static void mostrarDepartamentoConMasEmpleados(Empresa empresa) {
        // *INFO: Se recorre el array de departamentos para localizar el que tiene mayor numero de empleados.
        // !IMPORTANT: Empezar tomando el primer elemento como referencia evita comparaciones contra null cuando ya sabes que hay al menos un departamento creado.
        // ?QUESTION: Como resolverias un empate entre dos departamentos con el mismo numero de empleados?
        Departamento departamento = empresa.obtenerDepartamentoConMasEmpleados();

        System.out.println("\n--- DEPARTAMENTO CON MAS EMPLEADOS ---");
        if (departamento == null) {
            System.out.println("No hay departamentos creados.");
            return;
        }

        System.out.println("Departamento: " + departamento.getNombre());
        System.out.println("Numero de empleados: " + departamento.getTotalEmpleados());
    }

    /*
     * MICRO-TEORIA: mostrar opciones numeradas
     * Es util extraer este trozo para reutilizarlo y no duplicar codigo.
     */
    public static void mostrarSoloDepartamentos(Empresa empresa) {
        // *INFO: El alumno ve claramente el indice humano 1, 2, 3 mientras el programa sigue usando indices reales desde 0.
        // !IMPORTANT: Separar indice visual e indice real evita el error tipico de acceder fuera de rango por olvidar que los arrays empiezan en 0.
        // TODO: Muestra tambien cuantas plazas libres tiene cada departamento.
        for (int i = 0; i < empresa.getTotalDepartamentos(); i++) {
            System.out.println((i + 1) + ". " + empresa.getDepartamento(i).getNombre());
        }
    }

    /*
     * MICRO-TEORIA: validacion de enteros en rango
     * Validar datos de entrada es obligatorio en programas de consola.
     */
    public static int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        // *INFO: El bucle repite la lectura hasta recibir un entero valido dentro del rango pedido.
        // !IMPORTANT: Si no limpias el buffer cuando falla la lectura, el programa se queda atrapado leyendo siempre el mismo dato incorrecto.
        // TODO: Adapta este metodo para permitir que el usuario escriba "salir" en lugar de un numero.
        while (true) {
            System.out.print(mensaje);
            if (!SC.hasNextInt()) {
                System.out.println("Entrada incorrecta. Debes escribir un numero entero.");
                SC.nextLine();
                continue;
            }

            int valor = SC.nextInt();
            SC.nextLine();

            if (valor < minimo || valor > maximo) {
                System.out.println("Valor fuera de rango. Debe estar entre " + minimo + " y " + maximo + ".");
                continue;
            }
            return valor;
        }
    }

    /*
     * MICRO-TEORIA: validacion de enteros minimos
     * Este metodo sirve cuando no hay un tope superior fijo.
     */
    public static int leerEnteroMinimo(String mensaje, int minimo) {
        // *INFO: Reutilizamos el mismo patron de validacion para no copiar logica varias veces.
        // !IMPORTANT: Centralizar la validacion evita inconsistencias como aceptar edades negativas en un sitio y rechazarlas en otro.
        // TODO: Usa este metodo para validar tambien el numero de telefono en otra practica.
        while (true) {
            System.out.print(mensaje);
            if (!SC.hasNextInt()) {
                System.out.println("Entrada incorrecta. Debes escribir un numero entero.");
                SC.nextLine();
                continue;
            }

            int valor = SC.nextInt();
            SC.nextLine();

            if (valor < minimo) {
                System.out.println("El valor no puede ser menor que " + minimo + ".");
                continue;
            }
            return valor;
        }
    }

    /*
     * MICRO-TEORIA: validacion de reales
     * Los salarios suelen modelarse con double en ejercicios basicos.
     */
    public static double leerDoubleMinimo(String mensaje, double minimo) {
        // *INFO: Se valida salario con double para permitir cantidades decimales, algo frecuente en problemas de empresa o nomina.
        // !IMPORTANT: No usar esta validacion provoca errores tipicos como permitir salarios negativos o bloquear el programa por texto.
        // TODO: Formatea el salario con dos decimales tambien al pedir confirmacion del alta.
        while (true) {
            System.out.print(mensaje);
            if (!SC.hasNextDouble()) {
                System.out.println("Entrada incorrecta. Debes escribir un numero.");
                SC.nextLine();
                continue;
            }

            double valor = SC.nextDouble();
            SC.nextLine();

            if (valor < minimo) {
                System.out.println("El valor no puede ser menor que " + minimo + ".");
                continue;
            }
            return valor;
        }
    }

    /*
     * MICRO-TEORIA: validacion de texto
     * Un dato de texto vacio suele significar que el usuario pulso Enter sin querer.
     */
    public static String leerTextoNoVacio(String mensaje) {
        // *INFO: Un trim() elimina espacios sobrantes y ayuda a detectar entradas vacias de verdad.
        // !IMPORTANT: Guardar cadenas vacias es un error tipico que luego ensucia listados, busquedas y mensajes en consola.
        // ?QUESTION: Por que trim() mejora la validacion respecto a comprobar solo equals(\"\")?
        while (true) {
            System.out.print(mensaje);
            String texto = SC.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("El texto no puede estar vacio.");
                continue;
            }
            return texto;
        }
    }

    /*
     * MICRO-TEORIA: pausa en consola
     * Permite leer el resultado antes de volver a pintar el menu.
     */
    public static void pausar() {
        // *INFO: La pausa mejora la experiencia de practica porque el alumno puede revisar la salida con calma.
        // !IMPORTANT: Sin esta pausa, el menu aparece enseguida y puede parecer que la opcion anterior no ha mostrado nada.
        // TODO: Sustituye esta pausa por una opcion de "continuar" dentro del menu si quieres un flujo mas profesional.
        System.out.println("\nPulsa ENTER para volver al menu...");
        SC.nextLine();
    }
}

class Empresa {
    private String nombre;
    private String cif;
    private Departamento[] departamentos;
    private int totalDepartamentos;

    public Empresa(String nombre, String cif, int capacidadDepartamentos) {
        // *INFO: La empresa reserva un array fijo de departamentos para practicar el funcionamiento real de un array de objetos.
        // !IMPORTANT: Guardar tambien un contador de posiciones usadas evita recorrer huecos null, que es uno de los fallos mas frecuentes con arrays.
        // TODO: Anade un setter validado si en otra practica quieres permitir cambiar el CIF.
        this.nombre = nombre;
        this.cif = cif;
        this.departamentos = new Departamento[capacidadDepartamentos];
        this.totalDepartamentos = 0;
    }

    public boolean agregarDepartamento(Departamento departamento) {
        // *INFO: Agregar significa colocar el nuevo objeto en la siguiente posicion libre del array.
        // !IMPORTANT: Siempre se comprueba el limite antes de insertar para evitar ArrayIndexOutOfBoundsException.
        // TODO: Evita departamentos repetidos comparando por nombre.
        if (totalDepartamentos >= departamentos.length) {
            return false;
        }
        departamentos[totalDepartamentos] = departamento;
        totalDepartamentos++;
        return true;
    }

    public Empleado buscarEmpleadoPorNombre(String nombreBuscado) {
        // *INFO: La busqueda global delega en cada departamento para no meter toda la logica de empleados dentro de Empresa.
        // !IMPORTANT: Delegar responsabilidades reduce acoplamiento y evita un error didactico muy comun: una clase "dios" que lo hace todo.
        // TODO: Implementa una version que devuelva tambien el departamento donde trabaja el empleado.
        for (int i = 0; i < totalDepartamentos; i++) {
            Empleado encontrado = departamentos[i].buscarEmpleadoPorNombre(nombreBuscado);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    public int calcularTotalEmpleados() {
        // *INFO: Este total se obtiene acumulando el numero de empleados de cada departamento.
        // !IMPORTANT: El contador se calcula desde el estado real de los departamentos y no de memoria, evitando datos desactualizados.
        // TODO: Crea otro metodo que calcule solo empleados mayores de 30 anios.
        int totalEmpleados = 0;
        for (int i = 0; i < totalDepartamentos; i++) {
            totalEmpleados += departamentos[i].getTotalEmpleados();
        }
        return totalEmpleados;
    }

    public double calcularGastoSalarialTotal() {
        // *INFO: Este metodo suma el gasto salarial parcial que devuelve cada departamento.
        // !IMPORTANT: Reutilizar metodos ya existentes hace el codigo mas mantenible y evita repetir bucles iguales con pequeno riesgo de errores.
        // TODO: Calcula tambien el salario medio de toda la empresa.
        double gastoTotal = 0.0;
        for (int i = 0; i < totalDepartamentos; i++) {
            gastoTotal += departamentos[i].calcularGastoSalarial();
        }
        return gastoTotal;
    }

    public Departamento obtenerDepartamentoConMasEmpleados() {
        // *INFO: Se busca el maximo comparando el numero de empleados de cada departamento.
        // !IMPORTANT: Si no hay departamentos, devolver null es mejor que inventar un objeto vacio que podria confundir al alumno.
        // TODO: Resuelve empate devolviendo el primero alfabeticamente.
        if (totalDepartamentos == 0) {
            return null;
        }

        Departamento departamentoMayor = departamentos[0];
        for (int i = 1; i < totalDepartamentos; i++) {
            if (departamentos[i].getTotalEmpleados() > departamentoMayor.getTotalEmpleados()) {
                departamentoMayor = departamentos[i];
            }
        }
        return departamentoMayor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCif() {
        return cif;
    }

    public int getTotalDepartamentos() {
        return totalDepartamentos;
    }

    public Departamento getDepartamento(int indice) {
        return departamentos[indice];
    }
}

class Departamento {
    private String nombre;
    private Empleado[] empleados;
    private int totalEmpleados;

    public Departamento(String nombre, int capacidadEmpleados) {
        // *INFO: Cada departamento tiene su propio array interno, por eso actua como clase intermedia entre empresa y empleado.
        // !IMPORTANT: Inicializar el array en el constructor evita null en cuanto se crea el departamento, que es un error muy tipico al empezar con objetos.
        // TODO: Guarda tambien un responsable del departamento como otra propiedad de tipo Empleado.
        this.nombre = nombre;
        this.empleados = new Empleado[capacidadEmpleados];
        this.totalEmpleados = 0;
    }

    public boolean agregarEmpleado(Empleado empleado) {
        // *INFO: Este metodo inserta un empleado en la siguiente posicion libre del array del departamento.
        // !IMPORTANT: Comprobar si esta lleno antes de guardar evita salirte de los limites del array, uno de los fallos clasicos del examen.
        // TODO: Impide ids repetidos dentro del mismo departamento.
        if (estaLleno()) {
            return false;
        }
        empleados[totalEmpleados] = empleado;
        totalEmpleados++;
        return true;
    }

    public Empleado buscarEmpleadoPorNombre(String nombreBuscado) {
        // *INFO: La busqueda se hace recorriendo solo las posiciones ocupadas del array.
        // !IMPORTANT: Usar equalsIgnoreCase en lugar de == evita un fallo tipico con Strings: comparar referencias en vez de contenido.
        // TODO: Permite buscar por coincidencia parcial usando contains().
        for (int i = 0; i < totalEmpleados; i++) {
            if (empleados[i].getNombre().equalsIgnoreCase(nombreBuscado)) {
                return empleados[i];
            }
        }
        return null;
    }

    public double calcularGastoSalarial() {
        // *INFO: Cada departamento sabe sumar los salarios de sus propios empleados.
        // !IMPORTANT: Esta responsabilidad pertenece a Departamento porque conoce directamente su array interno y evita romper encapsulacion.
        // TODO: Calcula tambien el salario maximo del departamento.
        double totalSalarios = 0.0;
        for (int i = 0; i < totalEmpleados; i++) {
            totalSalarios += empleados[i].getSalario();
        }
        return totalSalarios;
    }

    public void mostrarEmpleados() {
        // *INFO: Mostrar datos desde la propia clase ayuda a practicar cohesion: cada clase presenta su informacion.
        // !IMPORTANT: Si no controlas el caso de departamento vacio, el alumno podria pensar que el programa no hace nada o que ha fallado.
        // TODO: Muestra tambien la suma de salarios al final del listado.
        if (totalEmpleados == 0) {
            System.out.println("No hay empleados en este departamento.");
            return;
        }

        for (int i = 0; i < totalEmpleados; i++) {
            System.out.println("  - " + empleados[i].resumenCorto());
        }
    }

    public boolean estaLleno() {
        return totalEmpleados >= empleados.length;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTotalEmpleados() {
        return totalEmpleados;
    }

    public int getCapacidadMaxima() {
        return empleados.length;
    }
}

class Empleado {
    private int id;
    private String nombre;
    private int edad;
    private String puesto;
    private double salario;

    public Empleado(int id, String nombre, int edad, String puesto, double salario) {
        // *INFO: El constructor garantiza que el objeto nazca con todos sus datos principales ya cargados.
        // !IMPORTANT: Crear objetos completos desde el inicio evita estados inconsistentes, como empleados sin nombre o sin salario.
        // TODO: Valida que la edad tenga un maximo razonable, por ejemplo 70.
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.puesto = puesto;
        this.salario = salario;
    }

    public String resumenCorto() {
        // *INFO: Este formato corto viene bien para listados donde no quieres saturar la pantalla.
        // !IMPORTANT: Separar resumen corto y toString completo evita repetir cadenas y facilita cambiar la salida despues.
        // TODO: Incluye tambien la antiguedad si anades ese atributo en el futuro.
        return "ID: " + id + " | Nombre: " + nombre + " | Puesto: " + puesto + " | Salario: " + salario + " euros";
    }

    @Override
    public String toString() {
        // *INFO: toString sirve para mostrar el objeto completo de forma legible al imprimirlo directamente.
        // !IMPORTANT: Sobrescribir toString evita ver salidas poco utiles como Empleado@3f99bd52, un resultado muy tipico cuando aun no se domina POO.
        // TODO: Alinea la salida en columnas si quieres practicar formato de texto.
        return "ID: " + id
                + "\nNombre: " + nombre
                + "\nEdad: " + edad
                + "\nPuesto: " + puesto
                + "\nSalario: " + salario + " euros";
    }

    public String getNombre() {
        return nombre;
    }

    public double getSalario() {
        return salario;
    }
}

/*
 * TAREA PARA EL ALUMNO
 * ------------------------------------------------------------------------------------------
 * 1) Crea una opcion para borrar un empleado buscando por id.
 * 2) Modifica la busqueda para que encuentre por parte del nombre.
 * 3) Anade un metodo que calcule el salario medio de cada departamento.
 * 4) Explica por que Departamento es la clase intermedia de este ejercicio.
 * 5) Prueba a rehacer la practica usando ArrayList en lugar de arrays fijos.
 */
