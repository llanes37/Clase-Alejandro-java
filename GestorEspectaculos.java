import java.util.Scanner;

/**
 * ============================================================================
 * PROGRAMA DE GESTIÓN DE VENTA DE ENTRADAS PARA MUSICALES
 * ============================================================================
 * 
 * DESCRIPCIÓN:
 * Aplicación completa que gestiona una empresa de espectáculos teatrales.
 * Permite crear musicales, vender entradas, modificar precios y calcular ingresos potenciales.
 * 
 * FUNCIONALIDADES:
 * - Gestionar 15 musicales almacenados en un array estático
 * - Vender entradas (gestión de butacas)
 * - Modificar precios con descuentos
 * - Calcular ingresos máximos de la empresa
 * 
 * ESTRUCTURA DE CLASES:
 * - Codigo: representa el código único del espectáculo (4 dígitos + 2 letras)
 * - Butaca: representa cada asiento/butaca (número y estado libre/ocupada)
 * - Espectaculo: clase base abstracta para todos los tipos de espectáculos
 * - Musical: hereda de Espectaculo, representa un musical con detalles específicos
 * - GestorEspectaculos: clase principal con menú interactivo y gestión de datos
 * 
 * REQUISITOS CUMPLIDOS:
 * - Uso correcto de herencia (Musical extends Espectaculo)
 * - Programación orientada a objetos
 * - Validación de datos
 * - Array estático de 15 musicales
 * - Menú interactivo con Scanner
 * - Código comentado y modularizado
 * 
 * @author Alumno
 * @version 1.0
 * @since 2024
 */

/**
 * ============================================================================
 * ENUNCIADO LITERAL DEL EXAMEN (WORD)
 * ============================================================================
 *
 * Una empresa de espectáculos desea informatizar la venta de entradas para las
 * distintas obras que realiza.
 *
 * Se sabe que de cada ESPECTACULO se guarda la siguiente información: Código
 * (objeto de otra clase formado por 4 números y 2 letras mayúsculas), Tipo
 * (cadena de caracteres, que en nuestro caso serán del tipo Musical) y Público
 * (dato booleano que indica si el espectáculo es (True) o no (False) para todos
 * los públicos). Además en el caso de los MUSICALES se guarda el nombre del
 * musical (cadena de caracteres que no podrá estar repetida), el precio de la
 * entrada (dato numérico) y la información de las butacas existentes para dicho
 * espectáculo, sabiendo que de cada una de ellas se almacena (el número de la
 * butaca (que será un numero secuencial consecutivo y si esta libre/ocupada
 * (True/False). Inicialmente todas las butacas están libres).
 *
 * Realizar una aplicación en Java que permita en primer lugar almacenar en una
 * estructura estática de datos la información de los 15 Musicales que oferta la
 * empresa, y a continuación mediante un menú permitir las siguientes opciones:
 * (3.5 puntos)
 *
 * Gestionar la venta de entradas. Para ello, el usuario introducirá el nombre
 * del musical para el que desea obtener entradas y una vez validado que dicho
 * musical existe se procederá a solicitar al usuario el número de entradas
 * (butacas) que desea y tras comprobar que hay tantas butacas libres se procede
 * a realizar la venta y se le mostrará al usuario la cantidad de dinero que
 * debe pagar. Será necesario mostrar por pantalla todos los posibles mensajes,
 * tanto si la venta ha sido posible como sino. (3.5 puntos)
 *
 * Modificar el precio de la entrada para un determinado musical. La empresa, ha
 * detectado que uno de sus musicales no es rentable debido a la poca clientela,
 * por lo que ha decidido abaratar su precio para intentar atraer clientes. Para
 * ello, el usuario introducirá el nombre del musical para el que desea modificar
 * el precio y una vez validado que dicho musical existe se procederá a reducir
 * el precio del mismo en un 15% de cara a promocionarla. (1.5 puntos)
 *
 * Dado que en varias opciones se requiere pedir una cadena de caracteres, será
 * necesario implementar un subprograma para tal fin.
 *
 * Calcular ingresos. Permitirá calcular la cantidad de dinero que recaudaría la
 * empresa en el caso de que un día vendiese todos las entradas (butacas) de
 * todos los musicales que oferta. (1.5 puntos)
 *
 * Salir.
 *
 * Para que cada opción sea calificada correctamente será obligatorio que
 * funcione a la perfección y esté implementada de la forma más óptima posible.
 */

// ============================================================================
// CLASE CODIGO
// ============================================================================
/**
 * Clase Codigo
 * Representa el código único de un espectáculo.
 * Formato: 4 números seguidos de 2 letras mayúsculas (ej: 1234AB)
 */
class Codigo {
    private String codigo;
    
    /**
     * Constructor con validación del formato
     * @param codigo String con formato válido (4 dígitos + 2 letras mayúsculas)
     */
    public Codigo(String codigo) {
        if (validarFormato(codigo)) {
            this.codigo = codigo;
        } else {
            throw new IllegalArgumentException("Formato incorrecto. Debe ser: 4 números + 2 letras mayúsculas");
        }
    }
    
    /**
     * Valida que el código tenga el formato correcto
     */
    private boolean validarFormato(String codigo) {
        // Paso 1: validaciones rapidas para evitar errores y trabajo innecesario.
        // - Si viene null, no se puede analizar el contenido.
        // - Si no tiene 6 caracteres exactos, ya incumple el patron.
        if (codigo == null || codigo.length() != 6) {
            return false;
        }
        
        // Paso 2: comprobar posiciones 0..3 (los 4 primeros caracteres).
        // Deben ser digitos del 0 al 9.
        for (int i = 0; i < 4; i++) {
            // charAt(i) obtiene el caracter en esa posicion.
            // Character.isDigit(...) valida si ese caracter es numerico.
            if (!Character.isDigit(codigo.charAt(i))) {
                // Si uno falla, el codigo completo es invalido.
                return false;
            }
        }
        
        // Paso 3: comprobar posiciones 4..5 (los 2 ultimos caracteres).
        // Deben ser letras y, ademas, estar en mayusculas.
        for (int i = 4; i < 6; i++) {
            char c = codigo.charAt(i);
            if (!Character.isLetter(c) || !Character.isUpperCase(c)) {
                // Si no es letra o no esta en mayuscula, formato invalido.
                return false;
            }
        }
        
        // Si pasa todas las comprobaciones, el formato es correcto.
        return true;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    @Override
    public String toString() {
        return codigo;
    }
}

// ============================================================================
// CLASE BUTACA
// ============================================================================
/**
 * Clase Butaca
 * Representa una butaca (asiento) en un espectáculo.
 * Contiene un número secuencial y un estado (libre u ocupada).
 */
class Butaca {
    private int numero;
    private boolean libre;
    
    /**
     * Constructor que inicializa la butaca como libre
     */
    public Butaca(int numero) {
        this.numero = numero;
        this.libre = true;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public boolean isLibre() {
        return libre;
    }
    
    public void setLibre(boolean libre) {
        this.libre = libre;
    }
    
    @Override
    public String toString() {
        return "Butaca " + numero + " [" + (libre ? "LIBRE" : "OCUPADA") + "]";
    }
}

// ============================================================================
// CLASE ESPECTACULO
// ============================================================================
/**
 * Clase Espectaculo
 * Clase base que representa un espectáculo genérico.
 * Contiene los atributos comunes a todos los espectáculos.
 */
class Espectaculo {
    protected Codigo codigo;
    protected String tipo;
    protected boolean publico;
    
    /**
     * Constructor de la clase Espectaculo
     */
    public Espectaculo(Codigo codigo, String tipo, boolean publico) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.publico = publico;
    }
    
    public Codigo getCodigo() {
        return codigo;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public boolean isPublico() {
        return publico;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public void setPublico(boolean publico) {
        this.publico = publico;
    }
    
    @Override
    public String toString() {
        return "Código: " + codigo + " | Tipo: " + tipo + " | Público: " + (publico ? "Sí" : "No");
    }
}

// ============================================================================
// CLASE MUSICAL
// ============================================================================
/**
 * Clase Musical
 * Hereda de la clase Espectaculo.
 * Representa un musical con todas sus características específicas.
 * Gestiona el nombre, precio de entrada y butacas disponibles.
 */
class Musical extends Espectaculo {
    private String nombre;
    private double precioEntrada;
    private Butaca[] butacas;
    private static final int NUM_BUTACAS = 100;
    
    /**
     * Constructor de la clase Musical
     */
    public Musical(Codigo codigo, String tipo, boolean publico, String nombre, double precioEntrada) {
        super(codigo, tipo, publico);
        this.nombre = nombre;
        this.precioEntrada = precioEntrada;
        this.butacas = new Butaca[NUM_BUTACAS];
        
        // Inicializar todas las butacas como libres
        for (int i = 0; i < NUM_BUTACAS; i++) {
            butacas[i] = new Butaca(i + 1);
        }
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public double getPrecioEntrada() {
        return precioEntrada;
    }
    
    public Butaca[] getButacas() {
        return butacas;
    }
    
    /**
     * Vende un número específico de entradas
     * Marca las butacas libres como ocupadas
     */
    public boolean venderEntradas(int cantidad) {
        if (contarButacasLibres() < cantidad) {
            return false;
        }
        
        int vendidas = 0;
        for (int i = 0; i < butacas.length && vendidas < cantidad; i++) {
            if (butacas[i].isLibre()) {
                butacas[i].setLibre(false);
                vendidas++;
            }
        }
        
        return vendidas == cantidad;
    }
    
    /**
     * Calcula la recaudación total si se venden todas las butacas
     */
    public double calcularRecaudacionTotal() {
        return NUM_BUTACAS * precioEntrada;
    }
    
    /**
     * Cuenta el número de butacas libres disponibles
     */
    public int contarButacasLibres() {
        int libres = 0;
        for (Butaca butaca : butacas) {
            if (butaca.isLibre()) {
                libres++;
            }
        }
        return libres;
    }
    
    /**
     * Reduce el precio de entrada en un porcentaje específico
     */
    public void reducirPrecio(double porcentaje) {
        this.precioEntrada = precioEntrada * (1 - porcentaje / 100);
    }
    
    /**
     * Calcula el importe total para una venta específica
     */
    public double calcularImporteVenta(int cantidad) {
        return cantidad * precioEntrada;
    }
    
    @Override
    public String toString() {
        return "Musical: " + nombre + " | " + super.toString() + 
               " | Precio: " + String.format("%.2f€", precioEntrada) + 
               " | Butacas libres: " + contarButacasLibres() + "/" + NUM_BUTACAS;
    }
}

// ============================================================================
// CLASE PRINCIPAL - GESTOR DE ESPECTÁCULOS
// ============================================================================
/**
 * Clase GestorEspectaculos
 * Programa principal que gestiona la aplicación completa.
 * Contiene el array de musicales y el menú interactivo.
 */
public class GestorEspectaculos {
    
    // Array estático de 15 musicales
    private static final Musical[] musicales = new Musical[15];
    private static Scanner scanner;
    
    /**
     * Método main - punto de entrada del programa
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        
        // Inicializar los 15 musicales con datos de prueba
        inicializarMusicales();
        
        // Mostrar menú repetitivamente hasta que el usuario elija salir
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opción: ");
            
            switch (opcion) {
                case 1:
                    gestionarVentaEntradas();
                    break;
                case 2:
                    modificarPrecio();
                    break;
                case 3:
                    calcularIngresosMaximos();
                    break;
                case 4:
                    salir = true;
                    System.out.println("\n¡Gracias por usar el sistema! Adiós.\n");
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.\n");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Inicializa el array con 15 musicales de prueba
     */
    private static void inicializarMusicales() {
        musicales[0] = new Musical(new Codigo("0001AB"), "Comedia Musical", true, "El Fantasma de la Ópera", 45.50);
        musicales[1] = new Musical(new Codigo("0002CD"), "Drama Musical", true, "Les Misérables", 50.00);
        musicales[2] = new Musical(new Codigo("0003EF"), "Comedia Musical", false, "Chicago", 48.25);
        musicales[3] = new Musical(new Codigo("0004GH"), "Romance Musical", true, "La Bella y la Bestia", 42.00);
        musicales[4] = new Musical(new Codigo("0005IJ"), "Aventura Musical", true, "El Rey León", 55.00);
        musicales[5] = new Musical(new Codigo("0006KL"), "Comedia Musical", true, "Hairspray", 38.50);
        musicales[6] = new Musical(new Codigo("0007MN"), "Drama Musical", false, "Sunset Boulevard", 52.75);
        musicales[7] = new Musical(new Codigo("0008OP"), "Comedia Musical", true, "Kinky Boots", 44.00);
        musicales[8] = new Musical(new Codigo("0009QR"), "Romance Musical", true, "Mamma Mia", 46.00);
        musicales[9] = new Musical(new Codigo("0010ST"), "Aventura Musical", true, "Aladdin", 54.50);
        musicales[10] = new Musical(new Codigo("0011UV"), "Drama Musical", true, "Hamilton", 60.00);
        musicales[11] = new Musical(new Codigo("0012WX"), "Comedia Musical", true, "The Book of Mormon", 49.99);
        musicales[12] = new Musical(new Codigo("0013YZ"), "Romance Musical", false, "Phantom 2", 51.00);
        musicales[13] = new Musical(new Codigo("0014AA"), "Aventura Musical", true, "Frozen", 47.50);
        musicales[14] = new Musical(new Codigo("0015BB"), "Comedia Musical", true, "Matilda", 43.25);
    }
    
    /**
     * MÉTODO: mostrarMenu()
     * 
     * Muestra en consola el menú principal con todas las opciones disponibles.
     * Utiliza método auxiliar generarLinea() para evitar problemas con repeat().
     */
    private static void mostrarMenu() {
        System.out.println("\n" + generarLinea(60));
        System.out.println("           SISTEMA DE GESTIÓN DE MUSICALES");
        System.out.println(generarLinea(60));
        System.out.println("1. Gestionar venta de entradas");
        System.out.println("2. Modificar precio");
        System.out.println("3. Calcular ingresos máximos de la empresa");
        System.out.println("4. Salir");
        System.out.println(generarLinea(60));
    }
    
    /**
     * MÉTODO AUXILIAR: generarLinea(int longitud)
     * 
     * Genera una cadena de caracteres "=" repetidos.
     * Alternativa al método repeat() (disponible solo en Java 11+).
     * Compatible con versiones anteriores de Java.
     * 
     * @param longitud número de caracteres a generar
     * @return String con '=' repetidos
     */
    private static String generarLinea(int longitud) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append("=");
        }
        return sb.toString();
    }
    
    /**
     * OPCIÓN 1: Gestionar venta de entradas
     * 
     * Permite al usuario:
     * 1. Buscar un musical por nombre
     * 2. Elegir cantidad de entradas
     * 3. Verificar disponibilidad
     * 4. Registrar la venta
     * 5. Mostrar importe a pagar
     */
    private static void gestionarVentaEntradas() {
        System.out.println("\n--- VENTA DE ENTRADAS ---");
        
        // Pedir el nombre del musical
        String nombreBuscado = leerString("Introduce el nombre del musical: ");
        
        // Buscar el musical
        Musical musical = buscarMusicalPorNombre(nombreBuscado);
        
        if (musical == null) {
            System.out.println("ERROR: No existe un musical con ese nombre.\n");
            return;
        }
        
        // Mostrar información del musical
        System.out.println("\n" + musical.toString());
        
        // Pedir número de entradas
        int cantidad = leerEntero("¿Cuántas entradas deseas? ");
        
        // Verificar disponibilidad
        if (cantidad <= 0) {
            System.out.println("ERROR: La cantidad debe ser mayor que 0.\n");
            return;
        }
        
        if (cantidad > musical.contarButacasLibres()) {
            System.out.println("ERROR: Solo hay " + musical.contarButacasLibres() + " butacas disponibles.\n");
            return;
        }
        
        // Realizar la venta
        if (musical.venderEntradas(cantidad)) {
            double importe = musical.calcularImporteVenta(cantidad);
            System.out.println("\n✓ Venta realizada con éxito");
            System.out.println("  Entradas: " + cantidad);
            System.out.println("  Precio total: " + String.format("%.2f€", importe));
            System.out.println("  Butacas libres restantes: " + musical.contarButacasLibres() + "\n");
        } else {
            System.out.println("ERROR: No se pudo completar la venta.\n");
        }
    }
    
    /**
     * OPCIÓN 2: Modificar precio
     * 
     * Permite al usuario:
     * 1. Seleccionar un musical por nombre
     * 2. Aplicar descuento automático de 15%
     * 3. Ver precio anterior y nuevo
     * 4. Ver ahorro total
     */
    private static void modificarPrecio() {
        System.out.println("\n--- MODIFICAR PRECIO ---");
        
        // Pedir el nombre del musical
        String nombreBuscado = leerString("Introduce el nombre del musical: ");
        
        // Buscar el musical
        Musical musical = buscarMusicalPorNombre(nombreBuscado);
        
        if (musical == null) {
            System.out.println("ERROR: No existe un musical con ese nombre.\n");
            return;
        }
        
        // Mostrar precio actual
        System.out.println("Precio actual: " + String.format("%.2f€", musical.getPrecioEntrada()));
        
        // Reducir precio un 15%
        double precioAnterior = musical.getPrecioEntrada();
        musical.reducirPrecio(15);
        
        System.out.println("Nuevo precio (reducción del 15%): " + String.format("%.2f€", musical.getPrecioEntrada()));
        System.out.println("Ahorro: " + String.format("%.2f€", precioAnterior - musical.getPrecioEntrada()) + "\n");
    }
    
    /**
     * OPCIÓN 3: Calcular ingresos máximos
     * 
     * Calcula cuánto dinero ganaría la empresa si vendiera TODAS
     * las butacas de TODOS los musicales al precio actual (ideal teórico).
     * 
     * Muestra:
     * - Ingreso total combinado
     * - Desglose detallado por musical
     */
    private static void calcularIngresosMaximos() {
        System.out.println("\n--- CÁLCULO DE INGRESOS MÁXIMOS ---");
        
        double ingresoTotal = 0;
        
        // Sumar la recaudación potencial de todos los musicales
        for (Musical musical : musicales) {
            ingresoTotal += musical.calcularRecaudacionTotal();
        }
        
        System.out.println("Si se vendieran TODAS las butacas de TODOS los musicales:");
        System.out.println("Ingreso máximo posible: " + String.format("%.2f€", ingresoTotal) + "\n");
        
        // Mostrar desglose por musical
        System.out.println("Desglose por musical:");
        for (Musical musical : musicales) {
            System.out.println("  " + musical.getNombre() + ": " + 
                             String.format("%.2f€", musical.calcularRecaudacionTotal()));
        }
        System.out.println();
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    /**
     * MÉTODO AUXILIAR: buscarMusicalPorNombre(String nombre)
     * 
     * Busca un musical en el array por su nombre (búsqueda case-insensitive).
     * 
     * LÓGICA:
     * 1. Recorrer array de musicales
     * 2. Comparar nombre ignorando mayúsculas/minúsculas
     * 3. Retornar objeto si encuentra coincidencia
     * 
     * @param nombre nombre del musical a buscar
     * @return objeto Musical si lo encuentra, null en caso contrario
     */
    private static Musical buscarMusicalPorNombre(String nombre) {
        for (Musical musical : musicales) {
            if (musical.getNombre().equalsIgnoreCase(nombre)) {
                return musical;
            }
        }
        return null;
    }
    
    /**
     * MÉTODO AUXILIAR: leerString(String mensaje)
     * 
     * Lee una línea de texto del usuario desde la consola.
     * 
     * @param mensaje mensaje a mostrar al usuario
     * @return String introducido por el usuario (sin espacios al principio/final)
     */
    private static String leerString(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
    
    /**
     * MÉTODO AUXILIAR: leerEntero(String mensaje)
     * 
     * Lee un número entero del usuario desde la consola.
     * Incluye validación automática - repite solicitud si entrada no es numérica.
     * 
     * @param mensaje mensaje a mostrar al usuario
     * @return int introducido por el usuario
     */
    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debes introducir un número válido.\n");
            }
        }
    }
}

