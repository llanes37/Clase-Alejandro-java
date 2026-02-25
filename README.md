# UT8 - Arrays y Colecciones (de menos a mas)

## Enunciado
Desarrolla un programa en Java con menu interactivo para practicar estructuras de datos tipicas de DAW.
El programa debe avanzar por dificultad:
1. Arrays (tamano fijo)
2. ArrayList (tamano dinamico)
3. HashSet (sin duplicados)
4. HashMap (clave-valor)
5. TreeMap (ordenacion por clave)
6. Integracion de todo en un mini caso real

## Modelado
- `int[] notas`: datos numericos de tamano fijo.
- `String[] lenguajes`: busqueda lineal de texto.
- `ArrayList<String>`: lista editable de alumnos/ciudades.
- `HashSet<String>`: modulos unicos sin repetidos.
- `HashMap<String, Integer>`: nota asociada a cada alumno.
- `TreeMap<Integer, String>`: ranking ordenado automaticamente.

## Explicacion de metodos principales
- `mostrarMenu()`: imprime opciones del 0 al 8.
- `ejercicioArrayNotas(...)`: calcula media, maximo, minimo y aprobadas.
- `ejercicioBusquedaArray(...)`: busqueda secuencial en array de Strings.
- `ejercicioArrayAArrayList(...)`: conversion de estructura fija a dinamica.
- `ejercicioListaAlumnos(...)`: mini CRUD basico con `ArrayList`.
- `ejercicioSetModulos(...)`: elimina duplicados con `HashSet`.
- `ejercicioMapNotas(...)`: alta y consulta de notas con `HashMap`.
- `ejercicioTreeMapRanking(...)`: muestra ranking ordenado con `TreeMap`.
- `ejercicioIntegrador(...)`: combina array + list + set + map en una sola practica.
- `leerEnteroEnRango(...)`: valida entrada y evita errores de formato/rango.

## Edge cases (casos limite)
- Entradas no numericas cuando se pide un entero.
- Numeros fuera de rango (por ejemplo, nota -1 o 11).
- Nombres vacios (se asigna un nombre por defecto en el integrador).
- Duplicados en modulos (HashSet los elimina).
- Claves repetidas en `HashMap` (se sobreescribe el valor).
- Empates de nota en `TreeMap` (se evita colision usando clave compuesta).

## Como ejecutar
1. Compilar:
```bash
javac UT8_ArraysYStrings.java
```
2. Ejecutar:
```bash
java UT8_ArraysYStrings
```
3. Probar el flujo recomendado:
- Opcion 1 -> arrays basicos
- Opcion 4 -> lista dinamica
- Opcion 6 -> mapa de notas
- Opcion 8 -> integrador final

## Entrada/salida esperada (ejemplo rapido)
Entrada:
- Opcion `1`
- Notas: `7, 5, 9, 4, 8`

Salida esperada (resumen):
- `Notas: [7, 5, 9, 4, 8]`
- `Media: 6.6 | Max: 9 | Min: 4`
- `Aprobadas (>=5): 4`

## Errores tipicos del alumno y como evitarlos
- Comparar `String` con `==`: usar `equals(...)` o `equalsIgnoreCase(...)`.
- Recorrer array con `<= length`: debe ser `< length`.
- Olvidar limpiar/validar entrada: usar `leerEnteroEnRango(...)`.
- Creer que `HashMap` guarda orden: si quieres orden, usar `TreeMap`.
- Modificar directamente un array esperando que crezca: usar `ArrayList`.

## 3 ejercicios extra
1. Anade filtro de aprobados/suspensos en `ejercicioMapNotas`.
2. En el integrador, calcula media global y porcentaje de aprobados.
3. Exporta el resumen final a un fichero `resumen_ut8.txt`.
