/*
 * ******************************************************************************************
 *                        TEORIA Y CONCEPTOS: FICHEROS EN JAVA (RELLENABLE)
 * ------------------------------------------------------------------------------------------
 * En esta practica aprenderas a:
 * - Crear archivos con la clase File.
 * - Escribir texto usando FileWriter.
 * - Leer lineas con FileReader + BufferedReader.
 * - Comprobar existencia, tamano y ruta de un archivo.
 *
 * Objetivo de clase:
 * Completar cada metodo guiado para dominar el manejo de ficheros en Java.
 ******************************************************************************************
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UT12_Ficheros_Rellenable {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        // *INFO: Menu principal para practicar cada operacion de ficheros por separado.
        // !IMPORTANT: Se usa do-while para repetir y consolidar aprendizaje por intentos.
        // ?QUESTION: Que error tipico ocurre si no limpias el buffer tras nextInt()?
        do {
            mostrarMenu();
            opcion = leerEnteroEnRango(sc, "Elige una opcion: ", 0, 4);

            switch (opcion) {
                case 1:
                    crearArchivo();
                    break;
                case 2:
                    escribirEnArchivo(sc);
                    break;
                case 3:
                    leerArchivo();
                    break;
                case 4:
                    comprobarArchivo();
                    break;
                case 0:
                    System.out.println("Fin de practica.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }

            if (opcion != 0) {
                System.out.println("\nPulsa ENTER para volver al menu...");
                sc.nextLine();
            }
        } while (opcion != 0);

        sc.close();
    }

    // *INFO: Muestra un menu corto para mantener foco en operaciones de ficheros.
    public static void mostrarMenu() {
        System.out.println("\n===== UT12 - FICHEROS (RELLENABLE) =====");
        System.out.println("1. Crear archivo");
        System.out.println("2. Escribir en archivo");
        System.out.println("3. Leer archivo");
        System.out.println("4. Comprobar archivo");
        System.out.println("0. Salir");
    }

    // *INFO: En este metodo debes crear un archivo en disco con File.
    // !IMPORTANT: Controla IOException para evitar que el programa termine con error.
    // TODO: Completa este metodo para crear un archivo llamado "notas.txt".
    public static void crearArchivo() {
        /*
         * ENUNCIADO:
         * 1) Crea un objeto File con nombre "notas.txt".
         * 2) Usa createNewFile() dentro de un try-catch.
         * 3) Si se crea, muestra "Archivo creado".
         * 4) Si ya existe, muestra "El archivo ya existe".
         *
         * CASO LIMITE:
         * - Si hay error de permisos o ruta, muestra mensaje de error.
         */
        // *INFO: File representa la ruta; no crea nada en disco hasta que llamamos a createNewFile().
        // !IMPORTANT: El archivo se crea en la carpeta desde la que ejecutas el programa (ruta relativa); si ejecutas desde otra, "no lo veras" donde esperas.
        // TODO: Prueba a cambiar "notas.txt" por una ruta dentro de una carpeta (por ejemplo "datos/notas.txt") y observa que necesitas crear la carpeta.
        File archivo = new File("notas.txt");

        try {
            // *INFO: createNewFile() devuelve true si lo crea y false si ya existia.
            // !IMPORTANT: No confundas "false" con error: significa "ya existia"; el error real salta como IOException.
            boolean creado = archivo.createNewFile();

            if (creado) {
                System.out.println("Archivo creado");
            } else {
                System.out.println("El archivo ya existe");
            }
        } catch (IOException e) {
            // *INFO: Aqui cae el caso limite: permisos, ruta invalida o bloqueo del sistema.
            // !IMPORTANT: Mostrar e.getMessage() ayuda a diagnosticar; ocultarlo es un error tipico que hace imposible saber que ha pasado.
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    // *INFO: En este metodo debes escribir texto en un archivo con FileWriter.
    // !IMPORTANT: Recuerda cerrar el recurso para no perder datos en buffer.
    // TODO: Completa este metodo para escribir la frase que introduzca el usuario.
    public static void escribirEnArchivo(Scanner sc) {
        /*
         * ENUNCIADO:
         * 1) Pide al usuario una frase por teclado.
         * 2) Abre FileWriter sobre "notas.txt" (modo append = true).
         * 3) Escribe la frase y un salto de linea.
         * 4) Cierra el FileWriter.
         *
         * CASO LIMITE:
         * - Si la frase esta vacia, decide si no escribes nada o guardas una marca.
         */
    }

    // *INFO: En este metodo debes leer todas las lineas del archivo y mostrarlas.
    // !IMPORTANT: Usa BufferedReader para leer linea a linea sin complicar la logica.
    // TODO: Completa este metodo para leer "notas.txt".
    public static void leerArchivo() {
        /*
         * ENUNCIADO:
         * 1) Abre un FileReader para "notas.txt".
         * 2) Envuélvelo en BufferedReader.
         * 3) Recorre con while hasta que readLine() sea null.
         * 4) Muestra cada linea por consola.
         * 5) Cierra el BufferedReader.
         *
         * CASO LIMITE:
         * - Si el archivo no existe, captura excepcion y muestra mensaje claro.
         */
    }

    // *INFO: En este metodo debes comprobar si existe el archivo y mostrar metadatos.
    // !IMPORTANT: Verifica exists() antes de consultar length() o ruta para evitar confusiones.
    // TODO: Completa este metodo para comprobar "notas.txt".
    public static void comprobarArchivo() {
        /*
         * ENUNCIADO:
         * 1) Crea File con "notas.txt".
         * 2) Si existe, muestra:
         *    - nombre
         *    - tamano en bytes
         *    - ruta absoluta
         * 3) Si no existe, muestra "El archivo no existe".
         *
         * CASO LIMITE:
         * - Si el archivo existe pero esta vacio, el tamano sera 0 bytes.
         */
    }

    // *INFO: Metodo auxiliar para validar enteros y evitar errores de entrada.
    // !IMPORTANT: Limpia el buffer tras nextInt para que nextLine no lea basura.
    // ?QUESTION: Como cambiarias este metodo para permitir volver atras con una letra?
    public static int leerEnteroEnRango(Scanner sc, String mensaje, int min, int max) {
        int valor;
        while (true) {
            System.out.print(mensaje);
            if (!sc.hasNextInt()) {
                System.out.println("Error: introduce un numero entero.");
                sc.nextLine();
                continue;
            }

            valor = sc.nextInt();
            sc.nextLine();

            if (valor < min || valor > max) {
                System.out.println("Error: elige un valor entre " + min + " y " + max + ".");
                continue;
            }
            return valor;
        }
    }

    /*
     * TAREAS EXTRA PARA EL ALUMNO:
     * 1) Crear opcion para borrar un archivo.
     * 2) Leer un archivo que indique el usuario por teclado.
     * 3) Contar cuantas lineas tiene "notas.txt".
     */
}
