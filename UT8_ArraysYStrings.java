/******************************************************************************************
 *  CURSO DE PROGRAMACION EN JAVA - UNIDAD 8
 *  TEMA: ARRAYS + COLECCIONES (DE MENOS A MAS)
 *  OBJETIVO: practicar estructuras tipicas de DAW con enfoque guiado.
 ******************************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class UT8_ArraysYStrings {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEnteroEnRango(sc, "Elige opcion: ", 0, 8);

            switch (opcion) {
                case 1 -> ejercicioArrayNotas(sc);
                case 2 -> ejercicioBusquedaArray(sc);
                case 3 -> ejercicioArrayAArrayList(sc);
                case 4 -> ejercicioListaAlumnos(sc);
                case 5 -> ejercicioSetModulos(sc);
                case 6 -> ejercicioMapNotas(sc);
                case 7 -> ejercicioTreeMapRanking(sc);
                case 8 -> ejercicioIntegrador(sc);
                case 0 -> System.out.println("Fin de la practica.");
                default -> System.out.println("Opcion no valida.");
            }

            if (opcion != 0) {
                System.out.println("\nPulsa ENTER para volver al menu...");
                sc.nextLine();
            }
        } while (opcion != 0);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== UNIDAD 8: ARRAYS Y COLECCIONES =====");
        System.out.println("1. Array de notas (basico)");
        System.out.println("2. Busqueda en array");
        System.out.println("3. Pasar de array a ArrayList");
        System.out.println("4. Gestion de alumnos con ArrayList");
        System.out.println("5. Modulos unicos con HashSet");
        System.out.println("6. Notas por alumno con HashMap");
        System.out.println("7. Ranking ordenado con TreeMap");
        System.out.println("8. Ejercicio integrador completo");
        System.out.println("0. Salir");
    }

    private static void ejercicioArrayNotas(Scanner sc) {
        System.out.println("\n--- EJERCICIO 1: ARRAY DE NOTAS ---");
        int[] notas = new int[5];

        // *INFO: Guardamos notas en array porque el tamano es fijo (5), lo que evita cambios de longitud a mitad de ejercicio.
        // *INFO: Error tipico que evitamos: usar indices fuera de rango (siempre 0..length-1).
        for (int i = 0; i < notas.length; i++) {
            notas[i] = leerEnteroEnRango(sc, "Nota " + (i + 1) + " (0-10): ", 0, 10);
        }

        int suma = 0;
        int max = notas[0];
        int min = notas[0];

        // !IMPORTANT: Recorremos una sola vez para calcular suma, max y min; asi el alumno ve optimizacion basica sin complicar.
        // !IMPORTANT: Error tipico que evitamos: inicializar max/min con 0 y fallar cuando todos los valores son menores o mayores.
        for (int nota : notas) {
            suma += nota;
            if (nota > max) {
                max = nota;
            }
            if (nota < min) {
                min = nota;
            }
        }

        double media = (double) suma / notas.length;
        System.out.println("Notas: " + Arrays.toString(notas));
        System.out.println("Media: " + media + " | Max: " + max + " | Min: " + min);
        System.out.println("Aprobadas (>=5): " + contarAprobadas(notas));
    }

    private static void ejercicioBusquedaArray(Scanner sc) {
        System.out.println("\n--- EJERCICIO 2: BUSQUEDA EN ARRAY ---");
        String[] lenguajes = {"java", "python", "javascript", "sql", "php"};
        System.out.println("Lista base: " + Arrays.toString(lenguajes));
        System.out.print("Que lenguaje quieres buscar? ");
        String objetivo = sc.nextLine().trim().toLowerCase();

        int posicion = -1;

        // *INFO: Hacemos busqueda lineal porque el array es pequeno y es la tecnica base que se ense?a primero en DAW.
        // *INFO: Error tipico que evitamos: comparar strings con == en vez de equals().
        for (int i = 0; i < lenguajes.length; i++) {
            if (lenguajes[i].equals(objetivo)) {
                posicion = i;
                break;
            }
        }

        if (posicion >= 0) {
            System.out.println("Encontrado en posicion " + posicion);
        } else {
            System.out.println("No encontrado");
        }
    }

    private static void ejercicioArrayAArrayList(Scanner sc) {
        System.out.println("\n--- EJERCICIO 3: ARRAY -> ARRAYLIST ---");
        String[] ciudadesArray = {"Madrid", "Sevilla", "Valencia", "Bilbao"};
        ArrayList<String> ciudadesList = new ArrayList<>(Arrays.asList(ciudadesArray));

        // !IMPORTANT: Convertimos array fijo a ArrayList para permitir add/remove segun necesidad del negocio.
        // !IMPORTANT: Error tipico que evitamos: intentar hacer add() sobre Arrays.asList() sin envolver en nuevo ArrayList.
        System.out.println("Antes: " + ciudadesList);
        System.out.print("Nueva ciudad para anadir: ");
        String nuevaCiudad = sc.nextLine().trim();
        if (!nuevaCiudad.isEmpty()) {
            ciudadesList.add(nuevaCiudad);
        }

        System.out.print("Ciudad a eliminar (exacta): ");
        String eliminar = sc.nextLine().trim();
        boolean borrada = ciudadesList.remove(eliminar);

        System.out.println("Despues: " + ciudadesList);
        System.out.println("Se elimino? " + borrada);
    }

    private static void ejercicioListaAlumnos(Scanner sc) {
        System.out.println("\n--- EJERCICIO 4: ARRAYLIST DE ALUMNOS ---");
        ArrayList<String> alumnos = new ArrayList<>();
        int opcion;

        do {
            System.out.println("\n1) Anadir alumno  2) Eliminar alumno  3) Listar  0) Salir");
            opcion = leerEnteroEnRango(sc, "Opcion: ", 0, 3);

            switch (opcion) {
                case 1 -> {
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine().trim();
                    if (!nombre.isEmpty()) {
                        alumnos.add(nombre);
                    }
                }
                case 2 -> {
                    System.out.print("Nombre exacto a eliminar: ");
                    String nombre = sc.nextLine().trim();
                    System.out.println(alumnos.remove(nombre) ? "Eliminado" : "No existe");
                }
                case 3 -> System.out.println("Alumnos: " + alumnos);
                case 0 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion no valida");
            }
        } while (opcion != 0);
    }

    private static void ejercicioSetModulos(Scanner sc) {
        System.out.println("\n--- EJERCICIO 5: HASHSET DE MODULOS ---");
        HashSet<String> modulos = new HashSet<>();

        // *INFO: HashSet elimina duplicados automaticamente; ideal para catalogos unicos (modulos, etiquetas, IDs).
        // *INFO: Error tipico que evitamos: usar ArrayList y tener que controlar duplicados manualmente.
        for (int i = 1; i <= 6; i++) {
            System.out.print("Modulo " + i + ": ");
            String modulo = sc.nextLine().trim().toUpperCase();
            if (!modulo.isEmpty()) {
                modulos.add(modulo);
            }
        }

        System.out.println("Modulos unicos: " + modulos);
        System.out.println("Cantidad unica: " + modulos.size());
    }

    private static void ejercicioMapNotas(Scanner sc) {
        System.out.println("\n--- EJERCICIO 6: HASHMAP ALUMNO -> NOTA ---");
        HashMap<String, Integer> notasPorAlumno = new HashMap<>();

        // !IMPORTANT: HashMap representa relacion clave-valor; aqui alumno->nota como caso tipico de DAW.
        // !IMPORTANT: Error tipico que evitamos: repetir claves sin saber que put() sobreescribe el valor previo.
        for (int i = 1; i <= 4; i++) {
            System.out.print("Alumno " + i + ": ");
            String alumno = sc.nextLine().trim();
            int nota = leerEnteroEnRango(sc, "Nota (0-10): ", 0, 10);
            if (!alumno.isEmpty()) {
                notasPorAlumno.put(alumno, nota);
            }
        }

        System.out.println("\nListado completo:");
        for (Map.Entry<String, Integer> entry : notasPorAlumno.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.print("Alumno a consultar: ");
        String buscar = sc.nextLine().trim();
        Integer nota = notasPorAlumno.get(buscar);
        System.out.println(nota == null ? "No existe en el mapa" : "Nota de " + buscar + ": " + nota);
    }

    private static void ejercicioTreeMapRanking(Scanner sc) {
        System.out.println("\n--- EJERCICIO 7: TREEMAP RANKING ORDENADO ---");
        TreeMap<Integer, String> ranking = new TreeMap<>();

        // ?QUESTION: Que pasa si dos alumnos tienen la misma nota y la nota es la clave?
        // *INFO: En TreeMap no puede haber clave repetida; combinamos nota con contador para no pisar datos.
        for (int i = 1; i <= 4; i++) {
            System.out.print("Alumno " + i + ": ");
            String nombre = sc.nextLine().trim();
            int nota = leerEnteroEnRango(sc, "Nota (0-10): ", 0, 10);

            int clave = nota * 100 + i; // evita colision de clave cuando hay empate de nota
            ranking.put(clave, nombre + " (nota " + nota + ")");
        }

        System.out.println("Ranking ascendente:");
        for (Map.Entry<Integer, String> entry : ranking.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    private static void ejercicioIntegrador(Scanner sc) {
        System.out.println("\n--- EJERCICIO 8: INTEGRADOR ARRAYS + COLECCIONES ---");
        int total = leerEnteroEnRango(sc, "Cuantos alumnos vas a registrar (3-10)? ", 3, 10);

        String[] alumnosArray = new String[total];
        ArrayList<String> alumnosList = new ArrayList<>();
        HashSet<String> modulosUnicos = new HashSet<>();
        HashMap<String, Integer> notaMedia = new HashMap<>();

        // !IMPORTANT: Flujo de menos a mas: capturamos en array, pasamos a lista y finalmente construimos set/map para casos reales.
        // !IMPORTANT: Error tipico que evitamos: mezclar entrada, validacion y calculo en un bloque imposible de mantener.
        for (int i = 0; i < total; i++) {
            System.out.print("Alumno " + (i + 1) + ": ");
            String alumno = sc.nextLine().trim();
            if (alumno.isEmpty()) {
                alumno = "Alumno" + (i + 1);
            }

            alumnosArray[i] = alumno;
            alumnosList.add(alumno);

            System.out.print("Modulo principal de " + alumno + ": ");
            String modulo = sc.nextLine().trim().toUpperCase();
            if (!modulo.isEmpty()) {
                modulosUnicos.add(modulo);
            }

            int nota = leerEnteroEnRango(sc, "Nota media de " + alumno + " (0-10): ", 0, 10);
            notaMedia.put(alumno, nota);
        }

        System.out.println("\nResumen final:");
        System.out.println("Array original: " + Arrays.toString(alumnosArray));
        System.out.println("ArrayList editable: " + alumnosList);
        System.out.println("Modulos unicos (Set): " + modulosUnicos);
        System.out.println("Notas (Map): " + notaMedia);

        System.out.println("\nAlumnos con nota >= 5:");
        for (String alumno : alumnosList) {
            Integer nota = notaMedia.get(alumno);
            if (nota != null && nota >= 5) {
                System.out.println("- " + alumno + " (" + nota + ")");
            }
        }

        // TODO: ampliar con menu CRUD completo (alta, baja, modificacion y consulta por nombre).
    }

    private static int contarAprobadas(int[] notas) {
        int aprobadas = 0;
        for (int nota : notas) {
            if (nota >= 5) {
                aprobadas++;
            }
        }
        return aprobadas;
    }

    private static int leerEnteroEnRango(Scanner sc, String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();

            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
                System.out.println("Valor fuera de rango [" + minimo + ", " + maximo + "]");
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida. Debes escribir un numero entero.");
            }
        }
    }
}
