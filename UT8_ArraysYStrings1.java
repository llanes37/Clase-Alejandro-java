/*
 * ******************************************************************************************
 *                        TEORIA Y CONCEPTOS: ARRAYS Y COLECCIONES EN JAVA
 * ------------------------------------------------------------------------------------------
 * En esta practica aprenderas a:
 *
 * - Trabajar con arrays (tamano fijo) para datos simples.
 * - Usar ArrayList para listas dinamicas.
 * - Usar HashSet para evitar duplicados.
 * - Usar HashMap para relacionar clave-valor.
 * - Usar TreeMap para mantener datos ordenados.
 * - Combinar todo en un ejercicio integrador tipico de DAW.
 *
 * Objetivo de clase:
 * Construir un mini sistema academico de consola, de menos a mas.
 ******************************************************************************************
 */

<<<<<<< HEAD
 import java.util.Arrays;
 import java.util.Scanner;
 
 public class UT8_ArraysYStrings {
 
     public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
 
         // 🔷 MENÚ PRINCIPAL PARA EJECUTAR EJERCICIOS
         int opcion = -1;
         do {
             System.out.println("\n🧭 MENÚ UNIDAD 8: ARRAYS + STRINGS");
             System.out.println("1️⃣ - Array de palabras");
             System.out.println("2️⃣ - Buscar palabra");
             System.out.println("3️⃣ - Frase a Array con split()");
             System.out.println("4️⃣ - Ordenar palabras");
             System.out.println("0️⃣ - Salir");
             System.out.print("👉 Elige una opción: ");
             opcion = sc.nextInt();
             sc.nextLine(); // Limpiar buffer
 
             switch (opcion) {
                 case 1:
                     arrayDePalabras(sc);
                     break;
                 case 2:
                     buscarPalabra(sc);
                     break;
                 case 3:
                     fraseASplit(sc);
                     break;
                 case 4:
                     ordenarPalabras(sc);
                     break;
                 case 0:
                     System.out.println("🚪 Saliendo del programa...");
                     break;
                 default:
                     System.out.println("❌ Opción inválida.");
             }
         } while (opcion != 0);
 
         sc.close();
     }
 
     // 🔹 EJERCICIO 1: ARRAY DE PALABRAS
     public static void arrayDePalabras(Scanner sc) {
         /*
          * 📖 TEORÍA:
          * - Un array puede almacenar Strings como cualquier otro tipo de dato.
          * - Se accede igual que un array de enteros.
          */
         System.out.println("\n📌 EJERCICIO 1: Array de palabras");
 
        String[] clases = {"Sergio", "Alvaro", "Oleg"};
         // ? Pedimos al usuario ingresar palabras
         for (int i = 0; i < clases.length; i++) {
             System.out.println("👋 " + clases[i] + " ha salido de clase.");
         }

 
         // ✅ TAREA ALUMNO: modifica el array para que tenga 5 palabras y muestra solo aquellas que tengan más de 5 letras.
     }
 
     // 🔹 EJERCICIO 2: BUSCAR UNA PALABRA
     public static void buscarPalabra(Scanner sc) {
         /*
          * 📖 TEORÍA:
          * - Podemos buscar palabras en un array usando un bucle y el método equalsIgnoreCase().
          * - Usamos una variable booleana para indicar si se encuentra o no.
          */
         System.out.println("\n📌 EJERCICIO 2: Buscar palabra en un array");
 
         String[] animales = {"perro", "gato", "loro", "pez"};
         System.out.print("🔎 ¿Qué animal deseas buscar? ");
         String buscar = "gato";
 
         boolean encontrado = false;
         for (String animal : animales) {
             System.out.println(animal);
         }
 
         if (encontrado) {
             System.out.println("✅ El animal está en la lista.");
         } else {
             System.out.println("❌ El animal NO está en la lista.");
         }
 
         // ✅ TAREA ALUMNO: Haz que el usuario introduzca los animales en lugar de estar predefinidos.
     }
 
     // 🔹 EJERCICIO 3: FRASE A ARRAY USANDO SPLIT
     public static void fraseASplit(Scanner sc) {
         /*
          * 📖 TEORÍA:
          * - El método `split(" ")` convierte una cadena en un array separando por espacios.
          * - Esto es útil para analizar palabras individuales dentro de un texto.
          */
         System.out.println("\n📌 EJERCICIO 3: Convertir frase en array con split");
 
         System.out.print("✍️ Escribe una frase: ");
         String frase = sc.nextLine();
 
         String[] palabras = frase.split(" "); // ? Separar por espacios
 
         System.out.println("🧾 Palabras encontradas: " + Arrays.toString(palabras));
         System.out.println("🔢 Total de palabras: " + palabras.length);
 
         // ✅ TAREA ALUMNO: Modifica el código para ignorar mayúsculas y contar cuántas veces aparece la palabra "java"
     }
 
     // 🔹 EJERCICIO 4: ORDENAR ALFABÉTICAMENTE
     public static void ordenarPalabras(Scanner sc) {
         /*
          * 📖 TEORÍA:
          * - La clase `Arrays` ofrece métodos útiles como `sort()` para ordenar elementos.
          * - Funciona tanto con números como con texto.
          */
         System.out.println("\n📌 EJERCICIO 4: Ordenar palabras alfabéticamente");
 
         System.out.print("🔢 ¿Cuántas palabras vas a ingresar? ");
         int cantidad = sc.nextInt();
         sc.nextLine(); // Limpiar buffer
 
         String[] palabras = new String[cantidad];
         for (int i = 0; i < cantidad; i++) {
             System.out.print("Palabra " + (i + 1) + ": ");
             palabras[i] = sc.nextLine();
         }
 
         Arrays.sort(palabras); // ? Ordenamos
         System.out.println("📚 Palabras ordenadas: " + Arrays.toString(palabras));
 
         // ✅ TAREA ALUMNO: Agrega opción para mostrar la palabra más corta y la más larga.
     }
 }
 
=======
/*
 * TEORIA GLOBAL: ARRAYS VS COLECCIONES
 * ------------------------------------
 *
 * 1) ARRAY:
 *    - Tamano fijo.
 *    - Acceso por indice.
 *    - Muy util cuando conocemos la cantidad exacta de elementos.
 *
 * 2) ARRAYLIST:
 *    - Tamano dinamico.
 *    - Permite add/remove facilmente.
 *
 * 3) HASHSET:
 *    - No admite duplicados.
 *    - Ideal para catalogos unicos (modulos, tags, codigos).
 *
 * 4) HASHMAP:
 *    - Almacena pares clave-valor.
 *    - Ejemplo tipico: Alumno -> Nota.
 *
 * 5) TREEMAP:
 *    - Como un Map, pero con claves ordenadas.
 *    - Util para rankings o listados ordenados.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class UT8_ArraysYStrings1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        // *INFO: Este menu permite practicar por fases, sin mezclar conceptos demasiado pronto.
        // !IMPORTANT: Error tipico que evitamos: saltar a mapas sin dominar arrays/listas primero.
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

    // *INFO: Menu unico y claro para que el alumno vea progresion didactica.
    public static void mostrarMenu() {
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

    // *INFO: MICRO-TEORIA - Array de enteros y calculos basicos.
    // !IMPORTANT: Que hace: calcula media/max/min/aprobadas en una sola pasada.
    // !IMPORTANT: Por que: mejora claridad y eficiencia para primer curso.
    // !IMPORTANT: Error tipico: iniciar max/min en 0 en vez del primer elemento.
    public static void ejercicioArrayNotas(Scanner sc) {
        System.out.println("\n--- EJERCICIO 1: ARRAY DE NOTAS ---");
        int[] notas = new int[5];

        for (int i = 0; i < notas.length; i++) {
            notas[i] = leerEnteroEnRango(sc, "Nota " + (i + 1) + " (0-10): ", 0, 10);
        }

        int suma = 0;
        int max = notas[0];
        int min = notas[0];

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

        // TODO: ampliar para mostrar tambien cuantas notas son sobresalientes (>=9).
    }

    // *INFO: MICRO-TEORIA - Busqueda lineal en array de Strings.
    // ?QUESTION: Por que no usamos binary search aqui?
    // *INFO: Porque el array no esta ordenado y queremos priorizar la tecnica base.
    public static void ejercicioBusquedaArray(Scanner sc) {
        System.out.println("\n--- EJERCICIO 2: BUSQUEDA EN ARRAY ---");
        String[] lenguajes = {"java", "python", "javascript", "sql", "php"};
        System.out.println("Lista base: " + Arrays.toString(lenguajes));

        System.out.print("Que lenguaje quieres buscar? ");
        String objetivo = sc.nextLine().trim().toLowerCase();

        int posicion = -1;

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

        // TODO: pedir 5 lenguajes al usuario en lugar de usar el array fijo.
    }

    // *INFO: MICRO-TEORIA - Migrar de estructura fija (array) a dinamica (ArrayList).
    // !IMPORTANT: Error tipico: usar Arrays.asList() y luego intentar add/remove directamente.
    public static void ejercicioArrayAArrayList(Scanner sc) {
        System.out.println("\n--- EJERCICIO 3: ARRAY -> ARRAYLIST ---");
        String[] ciudadesArray = {"Madrid", "Sevilla", "Valencia", "Bilbao"};
        ArrayList<String> ciudadesList = new ArrayList<>(Arrays.asList(ciudadesArray));

        System.out.println("Antes: " + ciudadesList);

        System.out.print("Nueva ciudad para anadir: ");
        String nuevaCiudad = sc.nextLine().trim();
       
            ciudadesList.add(nuevaCiudad);
                ciudadesList.add("Sevilla");

        System.out.print("Ciudad a eliminar (exacta): ");
        String eliminar = "Sevilla";
     ciudadesList.remove(eliminar);

        System.out.println("Despues: " + ciudadesList);
       
    }

    // *INFO: MICRO-TEORIA - Mini CRUD basico con ArrayList.
    // !IMPORTANT: Que hace: alta/baja/listado de alumnos en memoria.
    // !IMPORTANT: Caso limite: eliminar nombre inexistente sin lanzar excepcion.
    public static void ejercicioListaAlumnos(Scanner sc) {
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

        // TODO: ordenar alfabeticamente antes de listar.
    }

    // *INFO: MICRO-TEORIA - HashSet para valores unicos.
    // !IMPORTANT: Error tipico: asumir que HashSet mantiene orden de insercion.
    public static void ejercicioSetModulos(Scanner sc) {
        System.out.println("\n--- EJERCICIO 5: HASHSET DE MODULOS ---");
        HashSet<String> modulos = new HashSet<>();

        for (int i = 1; i <= 6; i++) {
            System.out.print("Modulo " + i + ": ");
            String modulo = sc.nextLine().trim().toUpperCase();
            if (!modulo.isEmpty()) {
                modulos.add(modulo);
            }
        }

        System.out.println("Modulos unicos: " + modulos);
        System.out.println("Cantidad unica: " + modulos.size());

        // ?QUESTION: Cuando interesaria cambiar HashSet por TreeSet?
    }

    // *INFO: MICRO-TEORIA - HashMap para clave-valor.
    // !IMPORTANT: Que hace: registra nota por alumno y permite consulta puntual.
    // !IMPORTANT: Error tipico: no saber que put() con misma clave reemplaza el valor anterior.
    public static void ejercicioMapNotas(Scanner sc) {
        System.out.println("\n--- EJERCICIO 6: HASHMAP ALUMNO -> NOTA ---");
        HashMap<String, Integer> notasPorAlumno = new HashMap<>();

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

    // *INFO: MICRO-TEORIA - TreeMap para mantener orden por clave.
    // ?QUESTION: Como resolver empates de nota si la clave es la propia nota?
    // *INFO: Usamos una clave compuesta para evitar colisiones.
    public static void ejercicioTreeMapRanking(Scanner sc) {
        System.out.println("\n--- EJERCICIO 7: TREEMAP RANKING ORDENADO ---");
        TreeMap<Integer, String> ranking = new TreeMap<>();

        for (int i = 1; i <= 4; i++) {
            System.out.print("Alumno " + i + ": ");
            String nombre = sc.nextLine().trim();
            int nota = leerEnteroEnRango(sc, "Nota (0-10): ", 0, 10);

            int clave = nota * 100 + i;
            ranking.put(clave, nombre + " (nota " + nota + ")");
        }

        System.out.println("Ranking ascendente:");
        for (Map.Entry<Integer, String> entry : ranking.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    // *INFO: MICRO-TEORIA - Ejercicio final integrador.
    // !IMPORTANT: Une array + list + set + map en un mismo flujo realista.
    // !IMPORTANT: Error tipico que evitamos: mezclar toda la logica sin separar fases.
    public static void ejercicioIntegrador(Scanner sc) {
        System.out.println("\n--- EJERCICIO 8: INTEGRADOR ARRAYS + COLECCIONES ---");
        int total = leerEnteroEnRango(sc, "Cuantos alumnos vas a registrar (3-10)? ", 3, 10);

        String[] alumnosArray = new String[total];
        ArrayList<String> alumnosList = new ArrayList<>();
        HashSet<String> modulosUnicos = new HashSet<>();
        HashMap<String, Integer> notaMedia = new HashMap<>();

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

        // TODO: anadir porcentaje de aprobados y media global del grupo.
    }

    // *INFO: Utilidad de apoyo para contar aprobadas en array.
    public static int contarAprobadas(int[] notas) {
        int aprobadas = 0;
        for (int nota : notas) {
            if (nota >= 5) {
                aprobadas++;
            }
        }
        return aprobadas;
    }

    // *INFO: Entrada robusta para enteros en rango.
    // !IMPORTANT: Que hace: evita NumberFormatException en flujo principal.
    // !IMPORTANT: Caso limite: usuario escribe texto o numero fuera de rango.
    public static int leerEnteroEnRango(Scanner sc, String mensaje, int minimo, int maximo) {
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

    // !IMPORTANT: TAREAS PARA EL ALUMNO (SUBIR NIVEL)
    // TODO: 1) Crear opcion para modificar nota de un alumno existente en HashMap.
    // TODO: 2) Exportar el resumen del integrador a fichero de texto.
    // TODO: 3) Implementar busqueda parcial por nombre (contains) en la lista.
}
>>>>>>> 51e456988a9da41534015d33ac66da50756c31ad
