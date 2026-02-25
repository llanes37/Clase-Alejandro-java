/******************************************************************************************
 *  📚 CURSO DE PROGRAMACIÓN EN JAVA - GESTOR DE FICHEROS PDF
 *  📅 FECHA: 2025
 *  🔹 CLASE: GestorFicherosPDF - Manejo completo de archivos con menú interactivo
 *  🎯 OBJETIVO: Crear, escribir y leer archivos de texto de forma estructurada
 *  🔐 REPOSITORIO EDUCATIVO
 ******************************************************************************************/

import java.io.File;                  // ? Para trabajar con archivos
import java.io.FileWriter;            // ? Para escribir en archivos
import java.io.IOException;           // ? Para manejar errores
import java.io.FileReader;            // ? Para leer archivos
import java.io.BufferedReader;        // ? Para leer líneas completas
import java.util.Scanner;             // ? Para leer entrada del usuario
import java.text.SimpleDateFormat;    // ? Para formatear fechas
import java.util.Date;                // ? Para obtener la fecha actual

public class GestorFicherosPDF {

    // 🎯 Variables globales para el programa
    private static final String CARPETA = "DocumentosPDF/";  // Carpeta donde se guardarán los archivos
    private static Scanner sc = new Scanner(System.in);      // Scanner para entrada del usuario

    /**
     * 🔧 Método auxiliar para repetir caracteres (compatible con Java 8)
     * @param car Carácter a repetir
     * @param cantidad Cantidad de repeticiones
     * @return String con el carácter repetido
     */
    private static String repetir(char car, int cantidad) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cantidad; i++) {
            sb.append(car);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // 📌 PASO 1: Crear la carpeta si no existe
        crearCarpeta();

        int opcion;

        // 📌 PASO 2: Menú principal - Bucle que se repite hasta que el usuario salga
        do {
            mostrarMenuPrincipal();
            opcion = obtenerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 0);

        System.out.println("\n👋 ¡Gracias por usar el gestor de ficheros! Hasta luego.");
        sc.close();
    }

    // ═══════════════════════════════════════════════════════════════════════════════════
    // 📊 MENÚ PRINCIPAL
    // ═══════════════════════════════════════════════════════════════════════════════════

    /**
     * 🎨 Muestra el menú principal de opciones
     */
    private static void mostrarMenuPrincipal() {
        System.out.println("\n" + repetir('═', 60));
        System.out.println("      📂 GESTOR DE FICHEROS - MENÚ PRINCIPAL 📂");
        System.out.println(repetir('═', 60));
        System.out.println("  1️⃣  Crear nuevo archivo de texto");
        System.out.println("  2️⃣  Escribir contenido en un archivo");
        System.out.println("  3️⃣  Leer contenido de un archivo");
        System.out.println("  4️⃣  Comprobar información del archivo");
        System.out.println("  5️⃣  Listar todos los archivos creados");
        System.out.println("  0️⃣  Salir del programa");
        System.out.println(repetir('═', 60));
        System.out.print("👉 Elige una opción: ");
    }

    /**
     * 🔢 Obtiene y valida la opción del usuario
     * @return La opción elegida
     */
    private static int obtenerOpcion() {
        try {
            int opcion = sc.nextInt();
            sc.nextLine(); // 🧹 Limpia el buffer
            return opcion;
        } catch (java.util.InputMismatchException e) {
            sc.nextLine(); // 🧹 Limpia el buffer en caso de error
            System.out.println("❌ Debes ingresar un número válido.");
            return -1;
        }
    }

    /**
     * 🎛️ Procesa la opción seleccionada por el usuario
     * @param opcion La opción elegida
     */
    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                menuCrearArchivo();
                break;
            case 2:
                menuEscribir();
                break;
            case 3:
                menuLeer();
                break;
            case 4:
                menuComprobarArchivo();
                break;
            case 5:
                listarArchivos();
                break;
            case 0:
                // Salir
                break;
            default:
                System.out.println("⚠️ Opción no válida. Intenta de nuevo.");
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════════════
    // 📌 FUNCIÓN 1: CREAR CARPETA
    // ═══════════════════════════════════════════════════════════════════════════════════

    /**
     * 📁 Crea la carpeta principal si no existe
     * 📖 TEORÍA: File permite crear directorios con mkdir()
     */
    private static void crearCarpeta() {
        File carpeta = new File(CARPETA);
        if (!carpeta.exists()) {
            if (carpeta.mkdir()) {
                System.out.println("✅ Carpeta '" + CARPETA + "' creada exitosamente.");
            } else {
                System.out.println("❌ No se pudo crear la carpeta.");
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════════════
    // 📌 FUNCIÓN 2: CREAR ARCHIVO
    // ═══════════════════════════════════════════════════════════════════════════════════

    /**
     * 📝 Menú para crear un nuevo archivo
     */
    private static void menuCrearArchivo() {
        System.out.print("\n📄 Ingresa el nombre del archivo (sin extensión): ");
        String nombre = sc.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("❌ El nombre no puede estar vacío.");
            return;
        }

        crearArchivo(nombre);
    }

    /**
     * 🔧 Crea un nuevo archivo de texto
     * 📖 TEORÍA:
     *    - File() crea un objeto que representa un archivo
     *    - createNewFile() lo crea físicamente en disco si no existe
     *    - Devuelve true si se creó, false si ya existía
     *
     * @param nombre El nombre del archivo (sin extensión)
     */
    private static void crearArchivo(String nombre) {
        try {
            // 📦 Construimos la ruta completa: carpeta + nombre + extensión
            String rutaCompleta = CARPETA + nombre + ".txt";
            File archivo = new File(rutaCompleta);

            // ✅ Intentamos crear el archivo
            if (archivo.createNewFile()) {
                System.out.println("\n✅ ¡Archivo creado exitosamente!");
                System.out.println("📍 Ruta: " + archivo.getAbsolutePath());
            } else {
                System.out.println("\nℹ️ El archivo '" + nombre + ".txt' ya existe.");
            }
        } catch (IOException e) {
            System.out.println("❌ Error al crear el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════════════
    // 📌 FUNCIÓN 3: ESCRIBIR EN ARCHIVO
    // ═══════════════════════════════════════════════════════════════════════════════════

    /**
     * ✍️ Menú para escribir contenido en un archivo
     */
    private static void menuEscribir() {
        System.out.print("\n📝 Ingresa el nombre del archivo donde escribir: ");
        String nombre = sc.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("❌ El nombre no puede estar vacío.");
            return;
        }

        System.out.println("\n🎯 Elige un modo de escritura:");
        System.out.println("1. Sobrescribir (reemplazar contenido actual)");
        System.out.println("2. Añadir (agregar al final del archivo)");
        System.out.print("👉 Opción: ");

        int modo = obtenerOpcion();
        boolean append = (modo == 2);

        System.out.println("\n📝 Escribe el contenido (escribe 'FIN' en una línea para terminar):");
        escribirEnArchivo(nombre, append);
    }

    /**
     * 🔧 Escribe contenido en un archivo
     * 📖 TEORÍA:
     *    - FileWriter abre un archivo para escritura
     *    - El constructor puede recibir un segundo parámetro boolean:
     *      * true = modo de adición (append)
     *      * false = sobrescritura
     *    - write() añade contenido
     *    - close() cierra el archivo (¡IMPORTANTE!)
     *
     * @param nombre El nombre del archivo
     * @param append true para añadir, false para sobrescribir
     */
    private static void escribirEnArchivo(String nombre, boolean append) {
        try {
            String rutaCompleta = CARPETA + nombre + ".txt";
            File archivo = new File(rutaCompleta);

            // ✅ Verificamos que el archivo exista
            if (!archivo.exists()) {
                System.out.println("❌ El archivo no existe. Créalo primero.");
                return;
            }

            // 📝 Abrimos el archivo en el modo especificado
            FileWriter escritor = new FileWriter(rutaCompleta, append);

            // 📅 Si es sobrescritura, añadimos fecha y hora
            if (!append) {
                String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                escritor.write("=== Documento creado: " + fecha + " ===\n\n");
            } else {
                escritor.write("\n\n--- Contenido añadido ---\n");
            }

            // ✍️ Leemos líneas del usuario hasta que escriba "FIN"
            String linea;
            while (true) {
                linea = sc.nextLine();
                if (linea.equalsIgnoreCase("FIN")) {
                    break;
                }
                escritor.write(linea + "\n");
            }

            // 🔐 Cerramos el archivo (OBLIGATORIO)
            escritor.close();
            System.out.println("\n✅ Contenido guardado exitosamente.");

        } catch (IOException e) {
            System.out.println("❌ Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════════════
    // 📌 FUNCIÓN 4: LEER ARCHIVO
    // ═══════════════════════════════════════════════════════════════════════════════════

    /**
     * 📖 Menú para leer un archivo
     */
    private static void menuLeer() {
        System.out.print("\n📖 Ingresa el nombre del archivo a leer: ");
        String nombre = sc.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("❌ El nombre no puede estar vacío.");
            return;
        }

        leerArchivo(nombre);
    }

    /**
     * 🔧 Lee y muestra el contenido de un archivo
     * 📖 TEORÍA:
     *    - FileReader abre un archivo para lectura
     *    - BufferedReader permite leer línea por línea (más eficiente)
     *    - readLine() devuelve null cuando llegamos al final del archivo
     *    - Necesitamos cerrar los flujos después de usarlos
     *
     * @param nombre El nombre del archivo a leer
     */
    private static void leerArchivo(String nombre) {
        try {
            String rutaCompleta = CARPETA + nombre + ".txt";
            File archivo = new File(rutaCompleta);

            // ✅ Verificamos que el archivo exista
            if (!archivo.exists()) {
                System.out.println("❌ El archivo no existe.");
                return;
            }

            // 📖 Abrimos el archivo para lectura
            FileReader lector = new FileReader(rutaCompleta);
            BufferedReader buffer = new BufferedReader(lector);

            System.out.println("\n" + repetir('─', 60));
            System.out.println("📖 CONTENIDO DEL ARCHIVO: " + nombre + ".txt");
            System.out.println(repetir('─', 60) + "\n");

            // 🔁 Leemos línea por línea hasta el final
            String linea;
            int numeroLinea = 1;
            while ((linea = buffer.readLine()) != null) {
                System.out.println(numeroLinea + " | " + linea);
                numeroLinea++;
            }

            // 🔐 Cerramos el buffer y el lector
            buffer.close();
            lector.close();

            System.out.println("\n" + repetir('─', 60) + "\n");

        } catch (IOException e) {
            System.out.println("❌ Error al leer el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════════════
    // 📌 FUNCIÓN 5: COMPROBAR INFORMACIÓN DEL ARCHIVO
    // ═══════════════════════════════════════════════════════════════════════════════════

    /**
     * 🔍 Menú para comprobar información de un archivo
     */
    private static void menuComprobarArchivo() {
        System.out.print("\n🔍 Ingresa el nombre del archivo a comprobar: ");
        String nombre = sc.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("❌ El nombre no puede estar vacío.");
            return;
        }

        comprobarInformacionArchivo(nombre);
    }

    /**
     * 🔧 Muestra información detallada de un archivo
     * 📖 TEORÍA:
     *    - exists() verifica si el archivo existe
     *    - length() devuelve el tamaño en bytes
     *    - getAbsolutePath() devuelve la ruta completa
     *    - lastModified() devuelve la última fecha de modificación
     *    - canRead() y canWrite() verifican permisos
     *
     * @param nombre El nombre del archivo
     */
    private static void comprobarInformacionArchivo(String nombre) {
        try {
            String rutaCompleta = CARPETA + nombre + ".txt";
            File archivo = new File(rutaCompleta);

            System.out.println("\n" + repetir('═', 60));
            System.out.println("🔍 INFORMACIÓN DEL ARCHIVO: " + nombre + ".txt");
            System.out.println(repetir('═', 60));

            if (archivo.exists()) {
                System.out.println("✅ Estado: EXISTE");
                System.out.println("📏 Tamaño: " + archivo.length() + " bytes");
                System.out.println("📍 Ruta absoluta: " + archivo.getAbsolutePath());
                System.out.println("✏️ Readable: " + (archivo.canRead() ? "Sí" : "No"));
                System.out.println("📝 Writable: " + (archivo.canWrite() ? "Sí" : "No"));
                System.out.println("🏷️ Nombre: " + archivo.getName());

                // Convertimos la fecha de modificación
                long tiempoMs = archivo.lastModified();
                if (tiempoMs > 0) {
                    Date fecha = new Date(tiempoMs);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    System.out.println("📅 Última modificación: " + sdf.format(fecha));
                }
            } else {
                System.out.println("❌ Estado: NO EXISTE");
                System.out.println("💡 Crea el archivo primero usando la opción 1.");
            }

            System.out.println(repetir('═', 60) + "\n");

        } catch (Exception e) {
            System.out.println("❌ Error al comprobar el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════════════
    // 📌 FUNCIÓN 6: LISTAR ARCHIVOS
    // ═══════════════════════════════════════════════════════════════════════════════════

    /**
     * 📋 Lista todos los archivos en la carpeta DocumentosPDF
     * 📖 TEORÍA:
     *    - File(ruta).listFiles() devuelve un array con todos los archivos
     *    - Podemos iterar sobre ellos y obtener información
     */
    private static void listarArchivos() {
        try {
            File carpeta = new File(CARPETA);
            File[] archivos = carpeta.listFiles();

            System.out.println("\n" + repetir('═', 60));
            System.out.println("📋 ARCHIVOS EN LA CARPETA: " + CARPETA);
            System.out.println(repetir('═', 60));

            if (archivos == null || archivos.length == 0) {
                System.out.println("📭 La carpeta está vacía.");
            } else {
                int contador = 1;
                for (File archivo : archivos) {
                    if (archivo.isFile()) { // Solo mostramos archivos, no carpetas
                        System.out.println(contador + ". 📄 " + archivo.getName() + 
                                         " (" + archivo.length() + " bytes)");
                        contador++;
                    }
                }
            }

            System.out.println(repetir('═', 60) + "\n");

        } catch (Exception e) {
            System.out.println("❌ Error al listar archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
