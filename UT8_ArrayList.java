/*
 * ******************************************************************************************
 *                        TEORIA Y CONCEPTOS: ARRAYLIST EN JAVA
 * ------------------------------------------------------------------------------------------
 * En esta practica aprenderas a:
 * - Crear y recorrer un ArrayList.
 * - Anadir, actualizar y eliminar elementos.
 * - Buscar elementos sin errores comunes.
 * - Ordenar y convertir una lista en array.
 *
 * Objetivo de clase:
 * Dominar ArrayList con un flujo guiado y ejercicios progresivos.
 ******************************************************************************************
 */

/*
 * TEORIA GLOBAL: ARRAY VS ARRAYLIST
 * ---------------------------------
 * 1) Array: tamano fijo, util cuando conoces la cantidad exacta.
 * 2) ArrayList: tamano dinamico, util para datos que crecen o cambian.
 * 3) ArrayList permite operaciones CRUD de forma clara en consola.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class UT8_ArrayList {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> tareas = new ArrayList<>();
        int opcion;

        // *INFO: Este menu organiza el aprendizaje de menos a mas en una sola sesion.
        // !IMPORTANT: Usamos un do-while para repetir practica y evitar cerrar el programa tras una accion.
        // ?QUESTION: Que pasaria si no validamos el rango de opcion del menu?
        do {
            mostrarMenu();
            opcion = leerEnteroEnRango(sc, "Elige una opcion: ", 0, 8);

            // *INFO: Usamos switch clasico para compatibilidad con Java 8/11 en entorno educativo.
            // !IMPORTANT: Cada case termina en break para evitar fall-through accidental entre opciones.
            // ?QUESTION: Que error aparece si en tu version de Java usas "case 1 ->" sin soporte?
            switch (opcion) {
                case 1:
                    anadirElemento(sc, tareas);
                    break;
                case 2:
                    mostrarElementos(tareas);
                    break;
                case 3:
                    actualizarElemento(sc, tareas);
                    break;
                case 4:
                    eliminarElemento(sc, tareas);
                    break;
                case 5:
                    buscarElemento(sc, tareas);
                    break;
                case 6:
                    ordenarLista(tareas);
                    break;
                case 7:
                    convertirAArray(tareas);
                    break;
                case 8:
                    mostrarCiudadesEjemplo();
                    break;
                case 0:
                    System.out.println("Fin de practica de ArrayList.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }

            if (opcion != 0) {
                System.out.println("\nPulsa ENTER para continuar...");
                sc.nextLine();
            }
        } while (opcion != 0);

        sc.close();
    }

    // *INFO: Muestra acciones basicas para practicar CRUD y utilidades de ArrayList.
    public static void mostrarMenu() {
        System.out.println("\n===== UNIDAD ARRAYLIST =====");
        System.out.println("1. Anadir elemento");
        System.out.println("2. Mostrar elementos");
        System.out.println("3. Actualizar por indice");
        System.out.println("4. Eliminar por texto");
        System.out.println("5. Buscar elemento");
        System.out.println("6. Ordenar lista");
        System.out.println("7. Convertir a array");
        System.out.println("8. Ejemplo de ciudades");
        System.out.println("0. Salir");
    }

    // *INFO: Anade un nuevo dato al final de la lista para mantener un comportamiento predecible.
    // !IMPORTANT: Validamos texto vacio para evitar "elementos fantasma" que luego cuestan depurar.
    // ?QUESTION: Cuando tendria sentido insertar en una posicion concreta y no al final?
    public static void anadirElemento(Scanner sc, ArrayList<String> lista) {
        System.out.print("Escribe la tarea a anadir: ");
        String texto = sc.nextLine().trim();

        if (texto.isEmpty()) {
            System.out.println("No se anadio nada: el texto estaba vacio.");
            return;
        }

        lista.add("Elemento anadido: " + texto);
        System.out.println("Elemento anadido correctamente.");
    }

    // *INFO: Lista los elementos con su indice para relacionar datos y posicion.
    // !IMPORTANT: Avisamos si esta vacia para evitar confundir al alumno con una salida "sin respuesta".
    // TODO: Mostrar tambien el numero total de caracteres almacenados en la lista.
    public static void mostrarElementos(ArrayList<String> lista) {
        if (lista.isEmpty()) {
            System.out.println("La lista esta vacia.");
            return;
        }
        

        System.out.println("Contenido actual:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(i + " -> " + lista.get(i));
        }
    }

    // *INFO: Cambia un elemento por indice para practicar set(indice, valor).
    // !IMPORTANT: Validamos indice para evitar IndexOutOfBoundsException.
    // ?QUESTION: Que diferencia hay entre set() y add(indice, valor)?
    public static void actualizarElemento(Scanner sc, ArrayList<String> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay datos para actualizar.");
            return;
        }

        mostrarElementos(lista);
        int indice = leerEnteroEnRango(sc, "Indice a actualizar: ", 0, lista.size() - 1);

        System.out.print("Nuevo texto: ");
        String nuevoTexto = sc.nextLine().trim();
        if (nuevoTexto.isEmpty()) {
            System.out.println("Cancelado: no se permiten textos vacios.");
            return;
        }

        lista.set(indice, nuevoTexto);
        System.out.println("Elemento actualizado correctamente.");
    }

    // *INFO: Elimina por texto ignorando mayusculas/minusculas para hacerlo mas usable en consola.
    // !IMPORTANT: Recorremos por indice para evitar borrar por error un valor distinto al normalizado.
    // TODO: Crear una version alternativa que elimine por indice directamente.
    public static void eliminarElemento(Scanner sc, ArrayList<String> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay datos para eliminar.");
            return;
        }

        System.out.print("Texto a eliminar: ");
        String texto = normalizarTexto(sc.nextLine());
        boolean eliminado = false;

        for (int i = 0; i < lista.size(); i++) {
            if (normalizarTexto(lista.get(i)).equals(texto)) {
                lista.remove(i);
                eliminado = true;
                break;
            }
        }

        System.out.println(eliminado ? "Elemento eliminado." : "No existe ese texto en la lista.");
    }

    // *INFO: Busca con contains para resolver consultas sencillas de presencia.
    // !IMPORTANT: Usamos busqueda parcial para evitar el error tipico de exigir coincidencia exacta.
    // ?QUESTION: En que escenarios te interesa busqueda exacta en vez de parcial?
    public static void buscarElemento(Scanner sc, ArrayList<String> lista) {
        if (lista.isEmpty()) {
            System.out.println("La lista esta vacia.");
            return;
        }

        System.out.print("Texto a buscar: ");
        String texto = normalizarTexto(sc.nextLine());

        boolean encontrado = false;
        for (String elemento : lista) {
            if (normalizarTexto(elemento).contains(texto)) {
                encontrado = true;
                break;
            }
        }

        System.out.println(encontrado ? "Si existe en la lista." : "No existe en la lista.");
    }

    // *INFO: Ordena alfabeticamente ignorando mayusculas para una salida mas natural en clase.
    // !IMPORTANT: Ordenar una lista vacia no falla, pero avisamos para no ocultar el estado real.
    // TODO: Probar orden inverso con Collections.reverseOrder().
    public static void ordenarLista(ArrayList<String> lista) {
        if (lista.isEmpty()) {
            System.out.println("La lista esta vacia.");
            return;
        }

        Collections.sort(lista, String.CASE_INSENSITIVE_ORDER);
        System.out.println("Lista ordenada alfabeticamente.");
        mostrarElementos(lista);
    }

    // *INFO: Convierte la lista a array para comparar estructuras estaticas y dinamicas.
    // !IMPORTANT: Usamos new String[0] por claridad y compatibilidad con toArray en Java moderno.
    // ?QUESTION: Que ventaja tiene quedarte en ArrayList frente a pasar a array?
    public static void convertirAArray(ArrayList<String> lista) {
        String[] array = lista.toArray(new String[0]);

        System.out.println("Array generado (indice -> valor):");
        for (int i = 0; i < array.length; i++) {
            System.out.println(i + " -> " + array[i]);
        }

        if (array.length == 0) {
            System.out.println("(Array vacio)");
        }
    }

    // *INFO: Crea un ArrayList de ciudades para practicar recorrido y visualizacion.
    // !IMPORTANT: Mostramos dos recorridos (indice y for-each) para reforzar ambas tecnicas basicas.
    // ?QUESTION: En que caso te interesa mas usar indice que for-each?
    public static void mostrarCiudadesEjemplo() {
        ArrayList<String> ciudades = new ArrayList<>();
        ArrayList <String> amigos = new ArrayList<>();
        amigos.add("Sergio");
        amigos.add("Alejandro");
        amigos.remove("Sergio");
        for (int i = 0 ; i < amigos.size() ; i++){
            System.out.println(i + "-> " + amigos.get(i) );
        }

        ciudades.add("Madrid");
        ciudades.add("Sevilla");
        ciudades.add("Valencia");
        ciudades.add("Bilbao");
        ciudades.add("Salamanca");

        // *INFO: Recorremos toda la lista para localizar una ciudad concreta.
        // !IMPORTANT: Mostramos solo "Sevilla" para evitar sacar por pantalla otras ciudades por error.
        // ?QUESTION: Que cambiarias si quisieras imprimir todas las coincidencias en vez de solo una?
        System.out.println("Busqueda de la ciudad valencia:");
        for (int i = 0; i < ciudades.size(); i++) {
            if (ciudades.get(i).equalsIgnoreCase("valencia")) {
                System.out.println("Valencia");
                break;
            }
        }

    }

    // *INFO: Lee enteros con validacion de tipo y rango para evitar bucles rotos.
    // !IMPORTANT: Limpiamos buffer tras nextInt para evitar que nextLine lea saltos pendientes.
    // TODO: Reutilizar este metodo en otras unidades para mantener coherencia docente.
    public static int leerEnteroEnRango(Scanner sc, String mensaje, int min, int max) {
        int valor;

        while (true) {
            System.out.print(mensaje);
            if (!sc.hasNextInt()) {
                System.out.println("Error: debes escribir un numero entero.");
                sc.nextLine();
                continue;
            }

            valor = sc.nextInt();
            sc.nextLine();

            if (valor < min || valor > max) {
                System.out.println("Error: valor fuera de rango (" + min + "-" + max + ").");
                continue;
            }

            return valor;
        }
    }

    // *INFO: Centraliza la normalizacion de texto para reutilizar en busquedas y borrados.
    // !IMPORTANT: Evita errores de comparacion por espacios laterales o mezcla de mayusculas.
    // ?QUESTION: Que cambiarias aqui para permitir busquedas con tildes equivalentes?
    public static String normalizarTexto(String texto) {
        return texto == null ? "" : texto.trim().toLowerCase();
    }

    /*
     * TAREAS PARA EL ALUMNO:
     * 1) Evitar duplicados al anadir usando contains().
     * 2) Implementar eliminar por indice.
     * 3) Mostrar cuantos elementos empiezan por una letra dada.
     */
}
