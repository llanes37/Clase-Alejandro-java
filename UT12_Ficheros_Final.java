/*
 * ******************************************************************************************
 *                    TEORIA Y CONCEPTOS - UT12 FICHEROS EN JAVA
 * ******************************************************************************************
 * En esta unidad vamos a trabajar el manejo de ficheros en Java con un enfoque practico.
 *
 * Ideas clave de la unidad:
 * 1. Un fichero permite guardar informacion de forma persistente.
 * 2. La clase File representa rutas, ficheros y carpetas del sistema.
 * 3. FileWriter y BufferedWriter sirven para escribir texto.
 * 4. FileReader y BufferedReader sirven para leer texto.
 * 5. Siempre hay que controlar errores de entrada/salida con try-catch.
 * 6. Cerrar recursos evita perdida de datos y bloqueos del fichero.
 * 7. Un menu con metodos pequenos facilita aprender, probar y depurar.
 *
 * Codigo de ejemplo rapido:
 *
 * File archivo = new File("datos_ut12/notas.txt");
 * archivo.createNewFile();
 *
 * try (FileWriter escritor = new FileWriter(archivo)) {
 *     escritor.write("Primera linea");
 * }
 *
 * try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
 *     String linea = lector.readLine();
 *     System.out.println(linea);
 * }
 *
 * Objetivo didactico:
 * - Entender que hace cada clase.
 * - Diferenciar sobrescribir y anadir contenido.
 * - Leer linea a linea.
 * - Consultar metadatos del fichero.
 * - Copiar contenido entre ficheros.
 * - Recorrer una carpeta y analizar archivos de texto.
 * ******************************************************************************************
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UT12_Ficheros_Final {

    private static final String CARPETA_BASE = "datos_ut12";
    private static final Scanner SC = new Scanner(System.in);

    /*
     * ========================================================================================
     * TEORIA GLOBAL
     * ========================================================================================
     * Un programa real no suele trabajar con un unico fichero fijo.
     * Por eso, esta practica usa un menu con varias operaciones.
     *
     * Ventajas del enfoque por menu:
     * - Cada opcion explica una tecnica concreta.
     * - Cada opcion llama a un metodo corto y facil de revisar.
     * - El alumno puede repetir una prueba varias veces sin reiniciar codigo.
     *
     * Flujo de aprendizaje recomendado para una clase de 1 hora:
     * 1. Crear carpeta y fichero.
     * 2. Escribir contenido nuevo.
     * 3. Anadir contenido sin borrar lo anterior.
     * 4. Leer y revisar el resultado.
     * 5. Ver informacion del fichero.
     * 6. Copiarlo a otro archivo.
     * 7. Listar archivos de una carpeta.
     * 8. Analizar lineas, palabras y caracteres.
     */
    public static void main(String[] args) {
        int opcion;

        // *INFO: Preparamos la carpeta de trabajo al inicio para que todas las operaciones compartan el mismo entorno y no dependan de ficheros creados a mano.
        // !IMPORTANT: Si no existe la carpeta base, algunas escrituras fallarian por ruta invalida; este paso evita el error tipico de "no encuentra el fichero".
        asegurarCarpetaBase();

        do {
            mostrarMenu();
            opcion = leerEnteroEnRango("Elige una opcion: ", 0, 9);
            ejecutarOpcion(opcion);
        } while (opcion != 0);

        // ?QUESTION: Que diferencia hay entre cerrar solo un fichero y cerrar tambien el Scanner del programa al final?
        // TODO: Anade una opcion extra que permita borrar un fichero confirmando antes con una respuesta S/N.
        SC.close();
        System.out.println("Programa finalizado.");
    }

    // *INFO: Este metodo concentra la impresion del menu para no repetir texto en el main y para mantener clara la estructura del programa.
    // !IMPORTANT: Tener el menu separado evita mezclar logica de negocio con salida por consola, un error tipico cuando el main crece demasiado.
    // ?QUESTION: Por que conviene que cada opcion del menu llame a un metodo distinto y no meter todo el codigo dentro del switch?
    // TODO: Personaliza el menu con nuevas operaciones si quieres convertir esta practica en una mini aplicacion.
    public static void mostrarMenu() {
        System.out.println("\n==================== UT12 - FICHEROS EN JAVA ====================");
        System.out.println("Carpeta de trabajo: " + new File(CARPETA_BASE).getAbsolutePath());
        System.out.println("1. Crear un fichero vacio");
        System.out.println("2. Escribir texto sobrescribiendo");
        System.out.println("3. Anadir texto al final del fichero");
        System.out.println("4. Leer un fichero linea a linea");
        System.out.println("5. Mostrar informacion del fichero");
        System.out.println("6. Copiar contenido a otro fichero");
        System.out.println("7. Listar archivos .txt de la carpeta");
        System.out.println("8. Analizar lineas, palabras y caracteres");
        System.out.println("9. Ejecutar demostracion guiada");
        System.out.println("0. Salir");
        System.out.println("================================================================");
    }

    // *INFO: El switch actua como controlador central y delega el trabajo real en metodos pequenos y reutilizables.
    // !IMPORTANT: Delegar reduce errores por duplicacion y hace mas facil localizar fallos cuando una opcion concreta no funciona.
    // TODO: Sustituye algun case por una llamada a un submenu si quieres ampliar la unidad didactica.
    public static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                crearFicheroVacio();
                break;
            case 2:
                escribirSobrescribiendo();
                break;
            case 3:
                anadirContenido();
                break;
            case 4:
                leerFicheroLineaALinea();
                break;
            case 5:
                mostrarInformacionFichero();
                break;
            case 6:
                copiarFicheroTexto();
                break;
            case 7:
                listarArchivosTxt();
                break;
            case 8:
                analizarFicheroTexto();
                break;
            case 9:
                ejecutarDemostracionGuiada();
                break;
            case 0:
                System.out.println("Has elegido salir.");
                break;
            default:
                System.out.println("Opcion no valida.");
        }
    }

    /*
     * MICRO-TEORIA
     * File no escribe ni lee por si solo.
     * Solo representa una ruta y permite comprobar si un fichero existe.
     * createNewFile() crea el fichero fisico si todavia no estaba creado.
     */
    // *INFO: Este metodo crea un fichero vacio dentro de la carpeta base para practicar la idea de ruta relativa y existencia previa.
    // !IMPORTANT: createNewFile() devuelve true si lo crea y false si ya existia; muchos alumnos creen que false significa error, y no es asi.
    // ?QUESTION: Que pasa si la ruta apunta a una carpeta que aun no existe?
    // TODO: Modifica el metodo para que tambien cree subcarpetas como "datos_ut12/pruebas/enero".
    public static void crearFicheroVacio() {
        // String nombre = leerTextoNoVacio("Nombre del nuevo fichero (ejemplo notas.txt): ");
        //  File archivo = resolverArchivo(nombre);
        File archivo = new File("Alejandr.txt");

        try {
            // *INFO: Intentamos crear el fichero solo una vez y mostramos un mensaje distinto segun el resultado para que el alumno vea ambos escenarios.
            // !IMPORTANT: No se debe asumir que siempre se crea; si ya existe, Java no lo borra ni lo reinicia automaticamente, evitando perdida accidental de datos.
            if (archivo.createNewFile()) {
                archivo.getName();
                System.out.println("Fichero creado correctamente: " + archivo.getAbsolutePath());
            } else {
                System.out.println("El fichero ya existia: " + archivo.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Error al crear el fichero: " + e.getMessage());
        }
    }

    /*
     * MICRO-TEORIA
     * FileWriter en modo normal sobrescribe el contenido previo.
     * Es una operacion util cuando queremos dejar el archivo "como nuevo".
     */
    // *INFO: Este metodo pide varias lineas y las guarda sustituyendo el contenido anterior del fichero elegido.
    // !IMPORTANT: Abrir FileWriter sin el modo append borra lo que habia; este es el error tipico mas importante cuando se empieza con ficheros.
    // ?QUESTION: En que situaciones reales si interesa sobrescribir y no anadir?
    // TODO: Cambia el formato para guardar una cabecera con fecha y autor antes del texto introducido.
    public static void escribirSobrescribiendo() {
        File archivo = new File("Alejandro.txt");
        //String nombre = leerTextoNoVacio("Nombre del fichero a escribir: ");
        //File archivo = resolverArchivo(nombre);
        //String contenido = leerBloqueTexto();

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
            // *INFO: BufferedWriter mejora la escritura de texto y el try-with-resources cierra el recurso incluso si aparece una excepcion.
            // !IMPORTANT: Escribir todo dentro del try evita el error tipico de usar un escritor ya cerrado o de olvidar el close().
            escritor.write(" contenido ");
            escritor.newLine();
            escritor.write("HOla buenos dias");
            System.out.println("Contenido guardado sobrescribiendo el fichero.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero: " + e.getMessage());
        }
    }

    /*
     * MICRO-TEORIA
     * FileWriter tiene un segundo parametro boolean.
     * Si ese parametro es true, la escritura se hace al final del fichero.
     */
    // *INFO: Este metodo ensena la diferencia entre sustituir el contenido y agregar texto nuevo al final del fichero.
    // !IMPORTANT: El modo append conserva la informacion anterior y evita el error de borrar datos cuando solo queriamos registrar una nueva linea.
    // TODO: Haz que cada linea anadida empiece por un contador automatico o por una marca de tiempo.
    public static void anadirContenido() {
       // String nombre = leerTextoNoVacio("Nombre del fichero al que anadir texto: ");
       // File archivo = resolverArchivo(nombre);
        // String contenido = leerBloqueTexto();
        File archivo = new File("Alejandro.txt");

      try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo, true))) {
            escritor.write("Linea anadida al final del fichero.");
            escritor.newLine();
            System.out.println("Contenido anadido al final del fichero.");
        } catch (IOException e) {
            System.out.println("Error al anadir contenido: " + e.getMessage());

      }
    }

    /*
     * MICRO-TEORIA
     * FileReader abre un flujo de lectura de caracteres.
     * BufferedReader permite leer linea a linea con readLine().
     */
    // *INFO: Leer linea a linea ayuda a entender como recorrer un fichero de texto de forma ordenada y controlada.
    // !IMPORTANT: Este enfoque funciona para texto plano; intentar leer un PDF o una imagen como si fueran texto dara resultados incorrectos o ilegibles.
    // ?QUESTION: Por que es mejor leer con un bucle while hasta null que asumir un numero fijo de lineas?
    // TODO: Muestra tambien el numero de linea delante de cada linea leida.
    public static void leerFicheroLineaALinea() {
        //String nombre = leerTextoNoVacio("Nombre del fichero a leer: ");
        //File archivo = resolverArchivo(nombre);
        File archivo = new File("Alejandro.txt");

        if (!archivo.exists()) {
            System.out.println("No existe el fichero indicado.");
            return;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;

            // *INFO: El bucle termina cuando readLine() devuelve null, lo que indica fin de fichero sin provocar error.
            // !IMPORTANT: Comprobar null es la forma correcta; usar una condicion incorrecta puede causar bucles infinitos o perder la ultima linea.
            System.out.println("\nContenido del fichero:");
            while ((linea = lector.readLine()) != null) {
                System.out.println("-> " + linea);
            }
            
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    /*
     * MICRO-TEORIA
     * La clase File permite consultar propiedades basicas:
     * nombre, ruta, tamano, permisos y tipo de elemento.
     */
    // *INFO: Este metodo muestra metadatos utiles para que el alumno vea que un fichero no es solo texto, tambien tiene propiedades del sistema.
    // !IMPORTANT: Antes de leer propiedades conviene comprobar exists(); si no, muchos datos no tendran sentido y el alumno puede interpretar mal la salida.
    // TODO: Anade tambien la fecha de ultima modificacion usando lastModified().
    public static void mostrarInformacionFichero() {
        String nombre = leerTextoNoVacio("Nombre del fichero para ver informacion: ");
        File archivo = resolverArchivo(nombre);

        if (!archivo.exists()) {
            System.out.println("No existe el fichero indicado.");
            return;
        }

        // *INFO: Se muestran propiedades tipicas de clase para relacionar teoria con una salida real del sistema.
        // !IMPORTANT: isFile() e isDirectory() ayudan a evitar el error de tratar una carpeta como si fuera un archivo de texto.
        System.out.println("\nInformacion del fichero");
        System.out.println("Nombre: " + archivo.getName());
        System.out.println("Ruta absoluta: " + archivo.getAbsolutePath());
        System.out.println("Existe: " + archivo.exists());
        System.out.println("Es fichero: " + archivo.isFile());
        System.out.println("Es carpeta: " + archivo.isDirectory());
        System.out.println("Se puede leer: " + archivo.canRead());
        System.out.println("Se puede escribir: " + archivo.canWrite());
        System.out.println("Tamano en bytes: " + archivo.length());
    }

    /*
     * MICRO-TEORIA
     * Copiar un fichero de texto consiste en leerlo y escribir su contenido en otro.
     * Aqui lo hacemos linea a linea para mantener el ejemplo sencillo y legible.
     */
    // *INFO: Este metodo combina lectura y escritura en un mismo ejercicio, lo que lo convierte en una practica integradora muy util.
    // !IMPORTANT: La copia manual presentada aqui esta pensada para texto; para binarios habria que usar streams de bytes, evitando corrupciones.
    // ?QUESTION: Que diferencia conceptual hay entre copiar contenido y renombrar un fichero?
    // TODO: Pregunta al usuario si desea sobrescribir el destino cuando ya exista.
    public static void copiarFicheroTexto() {
        String origenNombre = leerTextoNoVacio("Fichero origen: ");
        String destinoNombre = leerTextoNoVacio("Fichero destino: ");
        File origen = resolverArchivo(origenNombre);
        File destino = resolverArchivo(destinoNombre);

        if (!origen.exists()) {
            System.out.println("El fichero origen no existe.");
            return;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(origen));
             BufferedWriter escritor = new BufferedWriter(new FileWriter(destino))) {
            String linea;

            // *INFO: Cada linea leida se escribe en el nuevo fichero conservando la estructura del texto original.
            // !IMPORTANT: newLine() evita un error tipico: perder los saltos de linea y terminar con todo el texto pegado en una sola linea.
            while ((linea = lector.readLine()) != null) {
                escritor.write(linea);
                escritor.newLine();
            }
            System.out.println("Copia realizada en: " + destino.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al copiar el fichero: " + e.getMessage());
        }
    }

    /*
     * MICRO-TEORIA
     * Una carpeta puede contener varios ficheros.
     * listFiles() devuelve un array con los elementos encontrados.
     */
    // *INFO: Listar archivos de una carpeta ayuda a relacionar la teoria de File con una situacion muy comun en aplicaciones reales.
    // !IMPORTANT: listFiles() puede devolver null si la ruta no es valida; ignorarlo produce NullPointerException, un error muy frecuente.
    // TODO: Ordena alfabeticamente los archivos antes de mostrarlos.
    public static void listarArchivosTxt() {
        File carpeta = new File(CARPETA_BASE);
        File[] elementos = carpeta.listFiles();

        if (elementos == null) {
            System.out.println("No se pudo acceder a la carpeta base.");
            return;
        }

        int contador = 0;

        // *INFO: Recorremos el array y filtramos solo ficheros .txt para que el alumno vea una seleccion simple por extension.
        // !IMPORTANT: Comprobar isFile() evita confundir carpetas con documentos de texto, algo habitual cuando se empieza a listar contenido.
        System.out.println("\nArchivos .txt encontrados:");
        for (File elemento : elementos) {
            if (elemento.isFile() && elemento.getName().toLowerCase().endsWith(".txt")) {
                contador++;
                System.out.println(contador + ". " + elemento.getName() + " (" + elemento.length() + " bytes)");
            }
        }

        if (contador == 0) {
            System.out.println("No hay archivos .txt en la carpeta base.");
        }
    }

    /*
     * MICRO-TEORIA
     * Analizar un fichero significa extraer informacion resumida.
     * En este caso contamos lineas, palabras y caracteres.
     */
    // *INFO: Este metodo convierte la lectura en un problema de procesamiento de texto muy util para ejercitar bucles y validaciones.
    // !IMPORTANT: split(\"\\\\s+\") funciona bien con texto normal, pero hay que controlar lineas vacias para no contar una palabra inexistente.
    // ?QUESTION: Los espacios dobles deberian contar como varias palabras o como un unico separador?
    // TODO: Anade el conteo de vocales o de veces que aparece una palabra concreta.
    public static void analizarFicheroTexto() {
        String nombre = leerTextoNoVacio("Nombre del fichero a analizar: ");
        File archivo = resolverArchivo(nombre);

        if (!archivo.exists()) {
            System.out.println("No existe el fichero indicado.");
            return;
        }

        int lineas = 0;
        int palabras = 0;
        int caracteres = 0;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;

            // *INFO: Sumamos una linea por iteracion y calculamos palabras y caracteres a partir del texto leido en cada vuelta.
            // !IMPORTANT: Hacer trim() antes de split evita contar una palabra fantasma cuando la linea solo contiene espacios en blanco.
            while ((linea = lector.readLine()) != null) {
                lineas++;
                caracteres += linea.length();

                String lineaLimpia = linea.trim();
                if (!lineaLimpia.isEmpty()) {
                    palabras += lineaLimpia.split("\\s+").length;
                }
            }

            System.out.println("\nResumen del analisis");
            System.out.println("Lineas: " + lineas);
            System.out.println("Palabras aproximadas: " + palabras);
            System.out.println("Caracteres sin contar saltos de linea: " + caracteres);
        } catch (IOException e) {
            System.out.println("Error al analizar el fichero: " + e.getMessage());
        }
    }

    /*
     * MICRO-TEORIA
     * Una demostracion guiada sirve para explicar el flujo completo de trabajo.
     * Aqui automatizamos una secuencia para usarla directamente en clase.
     */
    // *INFO: Esta opcion genera archivos de ejemplo y ejecuta varias acciones seguidas para tener material rapido de demostracion en una clase real.
    // !IMPORTANT: Automatizar ejemplos evita perder tiempo escribiendo demasiadas entradas manuales en directo, un problema comun en sesiones cortas.
    // TODO: Convierte esta demo en un guion evaluable pidiendo al alumno que complete una parte del proceso.
    public static void ejecutarDemostracionGuiada() {
        File origen = resolverArchivo("demo_clase.txt");
        File copia = resolverArchivo("demo_clase_copia.txt");

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(origen))) {
            // *INFO: Creamos un fichero de ejemplo con varias lineas para que luego sirva en lectura, analisis y copia.
            // !IMPORTANT: Es mejor generar datos de prueba controlados que depender de un fichero externo que podria no existir en otro ordenador.
            escritor.write("UT12 - Ejemplo guiado de ficheros");
            escritor.newLine();
            escritor.write("Linea 2: Java permite trabajar con texto plano.");
            escritor.newLine();
            escritor.write("Linea 3: Esta demostracion resume la clase.");
            escritor.newLine();
        } catch (IOException e) {
            System.out.println("No se pudo preparar la demostracion: " + e.getMessage());
            return;
        }

        System.out.println("\nDemostracion guiada preparada.");
        System.out.println("1. Se ha creado el fichero demo_clase.txt");
        mostrarInformacionDirecta(origen);
        leerFicheroDirecto(origen);
        copiarDirectamente(origen, copia);
        analizarDirectamente(origen);
    }

    // *INFO: Este apoyo interno muestra informacion sin volver a pedir datos al usuario y simplifica la demostracion guiada.
    // !IMPORTANT: Separar utilidades internas evita duplicar codigo y reduce fallos por copiar y pegar bloques parecidos.
    public static void mostrarInformacionDirecta(File archivo) {
        System.out.println("\nInformacion rapida de la demo");
        System.out.println("Nombre: " + archivo.getName());
        System.out.println("Ruta: " + archivo.getAbsolutePath());
        System.out.println("Tamano: " + archivo.length() + " bytes");
    }

    // *INFO: Este metodo reutiliza la idea de lectura linea a linea para mostrar rapidamente el contenido del fichero de demostracion.
    // !IMPORTANT: Aunque es un apoyo interno, mantiene try-with-resources para no dejar recursos abiertos por descuido.
    public static void leerFicheroDirecto(File archivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("\nLectura automatica de la demo");
            while ((linea = lector.readLine()) != null) {
                System.out.println("-> " + linea);
            }
        } catch (IOException e) {
            System.out.println("Error en lectura automatica: " + e.getMessage());
        }
    }

    // *INFO: Copia un fichero de apoyo para que la demostracion muestre una operacion completa de lectura y escritura encadenadas.
    // !IMPORTANT: Esta version interna deja el destino preparado sin pedir confirmaciones, porque su objetivo es ser rapida y repetible en clase.
    public static void copiarDirectamente(File origen, File destino) {
        try (BufferedReader lector = new BufferedReader(new FileReader(origen));
             BufferedWriter escritor = new BufferedWriter(new FileWriter(destino))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                escritor.write(linea);
                escritor.newLine();
            }
            System.out.println("\nCopia automatica creada: " + destino.getName());
        } catch (IOException e) {
            System.out.println("Error en copia automatica: " + e.getMessage());
        }
    }

    // *INFO: Resume el contenido del fichero demo para mostrar una salida cuantitativa que complemente la lectura visual.
    // !IMPORTANT: Repetir el patron de analisis ayuda al alumno a reconocer estructuras comunes entre distintos metodos.
    public static void analizarDirectamente(File archivo) {
        int lineas = 0;
        int palabras = 0;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                lineas++;
                String limpia = linea.trim();
                if (!limpia.isEmpty()) {
                    palabras += limpia.split("\\s+").length;
                }
            }
            System.out.println("Analisis rapido -> lineas: " + lineas + ", palabras: " + palabras);
        } catch (IOException e) {
            System.out.println("Error en analisis automatico: " + e.getMessage());
        }
    }

    // *INFO: Este lector validado de enteros protege el menu frente a entradas incorrectas del usuario.
    // !IMPORTANT: Usar hasNextInt() evita InputMismatchException, uno de los errores mas comunes cuando se mezcla teclado y menu numerico.
    // TODO: Crea una version similar para leer decimales o respuestas tipo S/N.
    public static int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        int numero;

        while (true) {
            System.out.print(mensaje);
            if (SC.hasNextInt()) {
                numero = SC.nextInt();
                SC.nextLine();

                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }

                System.out.println("Entrada fuera de rango. Debe estar entre " + minimo + " y " + maximo + ".");
            } else {
                // *INFO: Limpiamos la entrada no valida para que el scanner no quede bloqueado leyendo siempre el mismo valor erroneo.
                // !IMPORTANT: Si no se limpia el buffer, el programa repetira el fallo en bucle y el alumno pensara que se ha colgado.
                System.out.println("Entrada incorrecta. Debes escribir un numero entero.");
                SC.nextLine();
            }
        }
    }

    // *INFO: Este metodo obliga a introducir un texto no vacio para evitar rutas sin nombre y mensajes ambiguos en las operaciones de fichero.
    // !IMPORTANT: Validar desde el principio simplifica el resto del programa y evita errores posteriores mas dificiles de localizar.
    public static String leerTextoNoVacio(String mensaje) {
        String texto;

        do {
            System.out.print(mensaje);
            texto = SC.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("No se admite una cadena vacia.");
            }
        } while (texto.isEmpty());

        return texto;
    }

    // *INFO: Permite capturar varias lineas de texto seguidas, algo util para que la practica no se limite a una sola frase corta.
    // !IMPORTANT: La palabra FIN actua como centinela; sin una condicion clara de salida el alumno no sabria cuando termina el bloque de texto.
    // ?QUESTION: Que ventaja tiene usar una palabra centinela frente a pedir un numero fijo de lineas?
    public static String leerBloqueTexto() {
        StringBuilder contenido = new StringBuilder();

        System.out.println("Escribe el contenido. Teclea FIN en una linea independiente para terminar.");
        while (true) {
            String linea = SC.nextLine();
            if (linea.equalsIgnoreCase("FIN")) {
                break;
            }

            if (contenido.length() > 0) {
                contenido.append(System.lineSeparator());
            }
            contenido.append(linea);
        }

        return contenido.toString();
    }

    // *INFO: Centralizar la resolucion de rutas hace que todo el programa trabaje siempre en la misma carpeta y sea mas facil de entender.
    // !IMPORTANT: Evita el error tipico de crear unos archivos en una ruta y buscarlos despues en otra distinta.
    public static File resolverArchivo(String nombre) {
        return new File(CARPETA_BASE, nombre);
    }

    // *INFO: La carpeta base se crea una sola vez al inicio y sirve como espacio controlado para todas las practicas.
    // !IMPORTANT: mkdirs() evita fallos cuando la carpeta aun no existe, especialmente al mover el proyecto a otro ordenador o aula.
    public static void asegurarCarpetaBase() {
        File carpeta = new File(CARPETA_BASE);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    /*
     * ========================================================================================
     * TAREA PARA EL ALUMNO
     * ========================================================================================
     * 1. Crea una nueva opcion de menu que renombre un fichero.
     * 2. Modifica el analisis para contar tambien vocales.
     * 3. Haz que la copia pregunte si debe sobrescribir el destino si ya existe.
     * 4. Crea un fichero "agenda.txt" y guarda 5 contactos, uno por linea.
     * 5. Lee "agenda.txt" y muestra solo los contactos que empiecen por la letra A.
     *
     * Propuesta de entendimiento guiado:
     * - Parte A: explica con tus palabras la diferencia entre File y FileWriter.
     * - Parte B: completa tu mismo una opcion nueva usando el patron del switch.
     * - Parte C: modifica un metodo para mejorar una validacion.
     */
}
