/*
 * ******************************************************************************************
 *                    TEORIA Y CONCEPTOS - UT12 FICHEROS EN JAVA (RELLENABLE)
 * ******************************************************************************************
 * Esta version esta pensada para practicar.
 * El alumno debe completar el codigo dentro de los metodos.
 *
 * Objetivos:
 * - Crear un fichero.
 * - Escribir en un fichero.
 * - Leer un fichero.
 * - Mostrar informacion basica.
 *
 * Instruccion de trabajo:
 * - Lee el comentario de cada metodo.
 * - Completa los bloques marcados con TODO.
 * - Prueba opcion por opcion.
 * ******************************************************************************************
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UT12_Ficheros_Rellenable {

    private static final String CARPETA_BASE = "datos_ut12_rellenable";
    private static final Scanner SC = new Scanner(System.in);

    /*
     * ========================================================================================
     * TEORIA GLOBAL
     * ========================================================================================
     * Esta practica usa un menu sencillo.
     * Cada opcion llama a un metodo corto.
     * La idea es que el alumno complete cada metodo de forma independiente.
     */
    public static void main(String[] args) {
        int opcion;

        asegurarCarpetaBase();

        do {
            mostrarMenu();
            opcion = leerEnteroEnRango("Elige una opcion: ", 0, 5);

            switch (opcion) {
                case 1:
                    crearFichero();
                    break;
                case 2:
                    escribirFichero();
                    break;
                case 3:
                    leerFichero();
                    break;
                case 4:
                    mostrarInformacion();
                    break;
                case 5:
                    ejercicioGuiado();
                    break;
                case 0:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);

        SC.close();
    }

    // *INFO: Este metodo muestra las opciones del menu para que el alumno pueda probar cada bloque por separado.
    // !IMPORTANT: Separar el menu del main evita mezclar la logica del programa con la salida por consola.
    // TODO: Si quieres ampliar la practica, anade una opcion nueva para copiar un fichero.
    public static void mostrarMenu() {
        System.out.println("\n========== UT12 FICHEROS RELLENABLE ==========");
        System.out.println("1. Crear fichero");
        System.out.println("2. Escribir fichero");
        System.out.println("3. Leer fichero");
        System.out.println("4. Mostrar informacion");
        System.out.println("5. Ejercicio guiado");
        System.out.println("0. Salir");
        System.out.println("==============================================");
    }

    /*
     * MICRO-TEORIA
     * La clase File representa una ruta.
     * createNewFile() crea el archivo si no existia.
     */
    // *INFO: Aqui el alumno debe practicar la creacion de un fichero usando File y createNewFile().
    // !IMPORTANT: Si el fichero ya existe, no debe dar error ni borrarlo; ese es un fallo tipico al empezar.
    // TODO: Pide el nombre del fichero, crea un objeto File y usa createNewFile() dentro de un try-catch.
    public static void crearFichero() {
        String nombre = leerTextoNoVacio("Nombre del fichero a crear: ");
        File archivo = new File(CARPETA_BASE, nombre);

        try {
            archivo.createNewFile();
            // TODO: Completa este bloque.
            // Sugerencia:
            // 1. Usa archivo.createNewFile()
            // 2. Muestra un mensaje si se crea
            // 3. Muestra otro mensaje si ya existia
            System.out.println("TODO: completar crearFichero()");
        } catch (IOException e) {
            System.out.println("Error al crear el fichero: " + e.getMessage());
        }
    }

    /*
     * MICRO-TEORIA
     * FileWriter escribe texto en un fichero.
     * BufferedWriter facilita la escritura de varias lineas.
     */
    // *INFO: Este metodo sirve para practicar la escritura en modo sobrescritura.
    // !IMPORTANT: Abrir FileWriter sin append borra el contenido anterior; hay que entender bien ese comportamiento.
    // TODO: Pide un nombre de fichero, pide una linea de texto y guardala usando BufferedWriter.
    public static void escribirFichero() {
        String nombre = leerTextoNoVacio("Nombre del fichero a escribir: ");
        File archivo = new File(CARPETA_BASE, nombre);
        String texto = leerTextoNoVacio("Escribe una linea de texto: ");

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
            // TODO: Completa este bloque.
            // Sugerencia:
            // 1. escritor.write(texto);
            // 2. escritor.newLine();
            // 3. muestra mensaje final
            System.out.println("TODO: completar escribirFichero()");
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero: " + e.getMessage());
        }
    }

    /*
     * MICRO-TEORIA
     * FileReader abre un fichero para lectura.
     * BufferedReader permite usar readLine().
     */
    // *INFO: El objetivo es leer el contenido del fichero y mostrarlo por pantalla.
    // !IMPORTANT: Antes de leer conviene comprobar si el fichero existe para evitar errores faciles de entender pero molestos en practica.
    // TODO: Comprueba si existe el fichero y luego leelo linea a linea con un while.
    public static void leerFichero() {
        String nombre = leerTextoNoVacio("Nombre del fichero a leer: ");
        File archivo = new File(CARPETA_BASE, nombre);

        // TODO: Si el fichero no existe, muestra un mensaje y haz return.

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            // TODO: Completa este bloque.
            // Sugerencia:
            // 1. Declara String linea;
            // 2. Usa while ((linea = lector.readLine()) != null)
            // 3. Muestra cada linea
            System.out.println("TODO: completar leerFichero()");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    /*
     * MICRO-TEORIA
     * File permite consultar datos del fichero como nombre, ruta y tamano.
     */
    // *INFO: Este metodo ayuda a practicar metadatos del fichero sin entrar todavia en operaciones complejas.
    // !IMPORTANT: Muchos alumnos confunden contenido con informacion del fichero; aqui se trabaja solo la segunda parte.
    // TODO: Muestra nombre, ruta absoluta, existencia y tamano del fichero.
    public static void mostrarInformacion() {
        String nombre = leerTextoNoVacio("Nombre del fichero para ver informacion: ");
        File archivo = new File(CARPETA_BASE, nombre);

        // TODO: Completa este metodo mostrando:
        // - archivo.getName()
        // - archivo.getAbsolutePath()
        // - archivo.exists()
        // - archivo.length()
        System.out.println("TODO: completar mostrarInformacion()");
    }

    /*
     * MICRO-TEORIA
     * Este metodo no resuelve el ejercicio por el alumno.
     * Solo recuerda los pasos logicos que debe seguir.
     */
    // *INFO: Sirve como ayuda rapida si el alumno se bloquea y necesita recordar el orden de trabajo.
    // !IMPORTANT: La idea es orientar sin regalar toda la solucion; por eso solo se muestran pistas.
    // TODO: Cuando acabes los metodos anteriores, cambia este texto y explica tus propios pasos.
    public static void ejercicioGuiado() {
        System.out.println("\nGUIA RAPIDA");
        System.out.println("1. Crear un objeto File con la carpeta base y el nombre.");
        System.out.println("2. Usar try-catch en metodos con operaciones de entrada/salida.");
        System.out.println("3. Para escribir, usar BufferedWriter y FileWriter.");
        System.out.println("4. Para leer, usar BufferedReader y FileReader.");
        System.out.println("5. Para comprobar existencia, usar archivo.exists().");
        System.out.println("6. Completa cada TODO antes de pasar al siguiente metodo.");
    }

    // *INFO: Este metodo valida la opcion del menu para que el programa no falle si el usuario escribe texto en vez de numeros.
    // !IMPORTANT: Sin esta validacion, el programa puede lanzar errores de entrada y bloquear la practica.
    // TODO: Reutiliza este patron en otras unidades cuando tengas menus por consola.
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

                System.out.println("Numero fuera de rango.");
            } else {
                System.out.println("Entrada incorrecta. Debes escribir un numero.");
                SC.nextLine();
            }
        }
    }

    // *INFO: Este metodo obliga a introducir texto no vacio y evita nombres de fichero invalidos o mensajes vacios.
    // !IMPORTANT: Validar desde el principio evita errores pequeños que luego confunden mucho al alumno.
    // TODO: Intenta adaptar este metodo para que no permita extensiones no validas.
    public static String leerTextoNoVacio(String mensaje) {
        String texto;

        do {
            System.out.print(mensaje);
            texto = SC.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("No se admite texto vacio.");
            }
        } while (texto.isEmpty());

        return texto;
    }

    // *INFO: Este metodo crea la carpeta base donde se guardaran los archivos del ejercicio.
    // !IMPORTANT: Si la carpeta no existe, algunas operaciones de escritura fallaran aunque el codigo del alumno este bien.
    // TODO: Cambia el nombre de la carpeta base y comprueba que todo sigue funcionando.
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
     * 1. Completa crearFichero().
     * 2. Completa escribirFichero().
     * 3. Completa leerFichero().
     * 4. Completa mostrarInformacion().
     * 5. Anade una nueva opcion de menu para anadir texto al final del fichero.
     *
     * Parte de entendimiento:
     * - Explica la diferencia entre File y FileWriter.
     * - Explica la diferencia entre leer y mostrar informacion del fichero.
     */
}
