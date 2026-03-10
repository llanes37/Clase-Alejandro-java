# UT12 - Ficheros en Java

## 1. Objetivo
Entender muy bien el trabajo con ficheros en Java: que es un fichero, que clases se usan, como crear, escribir, anadir, leer, copiar, consultar informacion y que errores tipicos aparecen.

Este documento esta pensado como apunte completo de referencia. La idea no es solo ver codigo, sino entender por que se hace cada paso.

## 2. Plan
1. Explicar que es un fichero y por que se usa.
2. Diferenciar `File` de las clases de lectura y escritura.
3. Ver las rutas y la carpeta de trabajo.
4. Aprender a crear un fichero.
5. Aprender a escribir sobrescribiendo.
6. Aprender a anadir contenido sin borrar lo anterior.
7. Aprender a leer un fichero linea a linea.
8. Consultar informacion del fichero y recorrer carpetas.
9. Analizar errores tipicos y buenas practicas.
10. Cerrar con ejemplos, pruebas y ejercicios de ampliacion.

## 3. Codigo o diff por archivos

### Archivo principal relacionado
- `UT12_Ficheros_Final.java`

### Idea general del programa
El archivo Java acompana este apunte con un menu por opciones. Cada opcion trabaja una tecnica concreta de ficheros:
- crear,
- escribir,
- anadir,
- leer,
- mostrar informacion,
- copiar,
- listar archivos,
- analizar texto.

---

# BLOQUE 1 - FUNDAMENTOS

## 4. Que es un fichero
Un fichero es un recurso del sistema que guarda informacion de forma persistente.

Persistente significa:
- si el programa termina, los datos siguen guardados;
- si vuelves a ejecutar el programa, puedes recuperar esos datos.

### Comparacion rapida

| Elemento | Se pierde al cerrar el programa | Sirve para guardar datos a largo plazo |
|---|---|---|
| Variable normal | Si | No |
| Fichero | No | Si |

### Ejemplos reales
- guardar notas de alumnos;
- registrar pedidos;
- almacenar configuraciones;
- exportar listados;
- guardar logs de errores.

## 5. Tipos de trabajo con ficheros
Las operaciones mas importantes son estas:

1. Crear un fichero.
2. Comprobar si existe.
3. Escribir contenido.
4. Anadir contenido al final.
5. Leer contenido.
6. Copiar contenido.
7. Mostrar propiedades del fichero.
8. Recorrer carpetas.

## 6. Texto frente a binario
En esta unidad trabajamos con ficheros de texto.

Ejemplos de texto:
- `.txt`
- `.csv`
- `.log`
- `.json` simple

Ejemplos binarios:
- `.pdf`
- `.jpg`
- `.png`
- `.mp3`
- `.exe`

### Idea importante
`FileReader` y `FileWriter` son para texto.
No son la herramienta adecuada para leer o escribir binarios.

---

# BLOQUE 2 - CLASES IMPORTANTES

## 7. La clase `File`
`File` representa una ruta del sistema.

Eso significa que puede apuntar a:
- un archivo;
- una carpeta.

### Lo mas importante
`File` no lee ni escribe contenido por si sola.
Solo representa y consulta.

### Ejemplo basico
```java
File archivo = new File("datos_ut12/notas.txt");

System.out.println(archivo.getName());
System.out.println(archivo.getAbsolutePath());
System.out.println(archivo.exists());
```

### Explicacion por partes
```java
File archivo = new File("datos_ut12/notas.txt");
```
Que hace:
crea un objeto que representa esa ruta.

Por que se hace asi:
porque primero necesitamos una referencia al fichero antes de operar con el.

Caso limite:
que exista el objeto `File` no significa que el archivo exista fisicamente.

```java
archivo.exists()
```
Que hace:
comprueba si el fichero ya existe en disco.

Por que es importante:
porque leer o mostrar datos de un archivo que no existe provoca errores o resultados invalidos.

### Metodos mas usados de `File`
- `exists()`
- `createNewFile()`
- `getName()`
- `getAbsolutePath()`
- `length()`
- `isFile()`
- `isDirectory()`
- `canRead()`
- `canWrite()`
- `listFiles()`

## 8. `FileWriter` y `BufferedWriter`
Sirven para escribir texto.

### Diferencia entre ambos
- `FileWriter`: escribe caracteres en el fichero.
- `BufferedWriter`: anade una capa mas comoda y eficiente para escribir texto.

### Ejemplo sencillo
```java
try (BufferedWriter escritor = new BufferedWriter(new FileWriter("datos_ut12/notas.txt"))) {
    escritor.write("Hola");
    escritor.newLine();
    escritor.write("Segunda linea");
}
```

### Explicacion por partes
```java
new FileWriter("datos_ut12/notas.txt")
```
Que hace:
abre el fichero para escribir.

Por que se implementa asi:
porque necesitamos un flujo de salida hacia el fichero.

Caso limite:
si el archivo ya tenia contenido, se sobrescribe.

```java
new BufferedWriter(...)
```
Que hace:
envuelve al `FileWriter` para facilitar la escritura de texto.

Por que conviene:
permite usar `newLine()` y deja el codigo mas claro.

```java
escritor.write("Hola");
```
Que hace:
escribe texto en el fichero.

Error tipico:
creer que `write()` baja automaticamente a la linea siguiente. No lo hace.

```java
escritor.newLine();
```
Que hace:
anade un salto de linea.

Por que es importante:
evita que todas las frases queden pegadas.

## 9. `FileReader` y `BufferedReader`
Sirven para leer texto.

### Ejemplo sencillo
```java
try (BufferedReader lector = new BufferedReader(new FileReader("datos_ut12/notas.txt"))) {
    String linea;

    while ((linea = lector.readLine()) != null) {
        System.out.println(linea);
    }
}
```

### Explicacion por partes
```java
new FileReader("datos_ut12/notas.txt")
```
Que hace:
abre el fichero para lectura.

Caso limite:
si el fichero no existe, lanzara una excepcion.

```java
new BufferedReader(...)
```
Que hace:
permite leer linea a linea con `readLine()`.

Por que es mejor para empezar:
porque es mucho mas claro que leer caracter a caracter.

```java
while ((linea = lector.readLine()) != null)
```
Que hace:
repite la lectura hasta llegar al final del fichero.

Por que se implementa asi:
porque `readLine()` devuelve `null` cuando ya no quedan lineas.

Error tipico:
suponer que el numero de lineas es fijo.

## 10. `Scanner`
`Scanner` se usa para leer datos por teclado.

En esta unidad es importante porque muchas operaciones piden:
- nombre del fichero;
- texto a guardar;
- opcion del menu.

### Error muy tipico
Leer un entero con `nextInt()` y olvidar limpiar el salto de linea con `nextLine()`.

Ejemplo:
```java
int opcion = sc.nextInt();
sc.nextLine();
```

---

# BLOQUE 3 - RUTAS Y CARPETAS

## 11. Ruta relativa y ruta absoluta
### Ruta relativa
Depende del directorio desde el que se ejecuta el programa.

Ejemplo:
```java
File archivo = new File("datos_ut12/notas.txt");
```

### Ruta absoluta
Es la ruta completa del sistema.

Ejemplo:
```java
System.out.println(archivo.getAbsolutePath());
```

### Cual conviene usar en clase
Normalmente una ruta relativa dentro del proyecto, porque:
- es mas portable;
- queda mas ordenado;
- evita depender del ordenador del aula.

## 12. Carpeta base de trabajo
Es buena practica tener una carpeta propia para las pruebas.

Ejemplo:
```java
private static final String CARPETA_BASE = "datos_ut12";
```

### Por que se hace asi
- no se mezclan los archivos de prueba con otros del proyecto;
- el alumno sabe siempre donde buscar;
- todas las operaciones apuntan al mismo sitio.

### Crear la carpeta si no existe
```java
File carpeta = new File(CARPETA_BASE);
if (!carpeta.exists()) {
    carpeta.mkdirs();
}
```

### Explicacion por partes
`mkdirs()`:
- crea la carpeta;
- y tambien subcarpetas si hacen falta.

Error tipico:
intentar escribir en una ruta cuya carpeta todavia no existe.

---

# BLOQUE 4 - OPERACIONES BASICAS BIEN EXPLICADAS

## 13. Crear un fichero

### Codigo
```java
File archivo = new File("datos_ut12/alumnos.txt");

if (archivo.createNewFile()) {
    System.out.println("Fichero creado");
} else {
    System.out.println("El fichero ya existia");
}
```

### Que hace
Intenta crear el fichero.

### Por que se implementa asi
`createNewFile()` devuelve un boolean:
- `true` si lo crea;
- `false` si ya existia.

### Caso limite
No devuelve `false` por error general.
Si hay un problema real de entrada/salida, se produce una excepcion.

## 14. Escribir sobrescribiendo

### Codigo
```java
try (BufferedWriter escritor = new BufferedWriter(new FileWriter("datos_ut12/alumnos.txt"))) {
    escritor.write("Ana");
    escritor.newLine();
    escritor.write("Luis");
}
```

### Que hace
Escribe el contenido desde cero.

### Por que se implementa asi
Es la forma mas simple de reiniciar el contenido del fichero.

### Lo mas importante
Si el archivo ya tenia texto, se borra y se sustituye por el nuevo.

### Caso limite
Muchos alumnos creen que escribir siempre anade. No es verdad.

## 15. Anadir al final del fichero

### Codigo
```java
try (BufferedWriter escritor = new BufferedWriter(new FileWriter("datos_ut12/alumnos.txt", true))) {
    escritor.newLine();
    escritor.write("Marta");
}
```

### Que hace
Agrega texto al final.

### Por que se implementa asi
El segundo parametro `true` activa el modo append.

### Lo mas importante
Esta es una de las diferencias mas importantes de toda la unidad:

```java
new FileWriter(ruta)
```
sobrescribe.

```java
new FileWriter(ruta, true)
```
anade al final.

### Caso limite
Si no controlas los saltos de linea, el texto nuevo puede quedar pegado al anterior.

## 16. Leer linea a linea

### Codigo
```java
try (BufferedReader lector = new BufferedReader(new FileReader("datos_ut12/alumnos.txt"))) {
    String linea;

    while ((linea = lector.readLine()) != null) {
        System.out.println("-> " + linea);
    }
}
```

### Que hace
Recorre todo el fichero hasta el final.

### Por que se implementa asi
Es el patron mas claro para leer texto de varias lineas.

### Caso limite
Si el fichero no existe, primero debes comprobarlo o capturar la excepcion.

## 17. Mostrar informacion del fichero

### Codigo
```java
File archivo = new File("datos_ut12/alumnos.txt");

System.out.println("Nombre: " + archivo.getName());
System.out.println("Ruta: " + archivo.getAbsolutePath());
System.out.println("Tamano: " + archivo.length());
System.out.println("Se puede leer: " + archivo.canRead());
System.out.println("Se puede escribir: " + archivo.canWrite());
```

### Que hace
Muestra propiedades del fichero.

### Por que es importante
Porque un fichero no es solo contenido. Tambien tiene metadatos.

### Caso limite
Si el archivo no existe, la informacion puede ser engañosa o no tener sentido practico.

## 18. Copiar un fichero de texto

### Codigo
```java
try (BufferedReader lector = new BufferedReader(new FileReader("datos_ut12/origen.txt"));
     BufferedWriter escritor = new BufferedWriter(new FileWriter("datos_ut12/copia.txt"))) {

    String linea;

    while ((linea = lector.readLine()) != null) {
        escritor.write(linea);
        escritor.newLine();
    }
}
```

### Que hace
Lee el fichero origen y va escribiendo lo mismo en el destino.

### Por que se implementa asi
Porque une en un solo ejercicio lectura y escritura.

### Caso limite
Este ejemplo es para texto. No es la forma correcta de copiar un PDF o una imagen.

## 19. Listar archivos de una carpeta

### Codigo
```java
File carpeta = new File("datos_ut12");
File[] elementos = carpeta.listFiles();

if (elementos != null) {
    for (File elemento : elementos) {
        if (elemento.isFile()) {
            System.out.println(elemento.getName());
        }
    }
}
```

### Que hace
Recorre el contenido de una carpeta.

### Por que se implementa asi
Porque muchas aplicaciones reales no trabajan con un solo archivo, sino con varios.

### Caso limite
`listFiles()` puede devolver `null`.

## 20. Analizar un fichero

### Codigo
```java
int lineas = 0;
int palabras = 0;
int caracteres = 0;

try (BufferedReader lector = new BufferedReader(new FileReader("datos_ut12/alumnos.txt"))) {
    String linea;

    while ((linea = lector.readLine()) != null) {
        lineas++;
        caracteres += linea.length();

        String limpia = linea.trim();
        if (!limpia.isEmpty()) {
            palabras += limpia.split("\\s+").length;
        }
    }
}
```

### Que hace
Cuenta lineas, palabras y caracteres.

### Por que se implementa asi
Porque transforma la lectura en una tarea de procesamiento de informacion.

### Caso limite
Una linea vacia no debe contar una palabra falsa.

### Punto importante
`split("\\s+")` es mejor que `split(" ")` porque soporta uno o varios espacios.

---

# BLOQUE 5 - TRY WITH RESOURCES Y EXCEPCIONES

## 21. Por que usar `try-with-resources`
Es una de las partes mas importantes de la unidad.

### Codigo
```java
try (BufferedReader lector = new BufferedReader(new FileReader("datos_ut12/notas.txt"))) {
    System.out.println(lector.readLine());
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
```

### Que hace
Abre el recurso, lo usa y lo cierra automaticamente.

### Por que se implementa asi
Porque evita olvidarse del `close()`.

### Error tipico que evita
- dejar el fichero abierto;
- perder datos no volcados;
- complicar el codigo con cierres manuales.

## 22. Excepciones mas frecuentes

### `IOException`
Es la excepcion general mas comun al trabajar con entrada y salida.

Puede aparecer si:
- el fichero no existe;
- la ruta es incorrecta;
- no hay permisos;
- hay problemas de acceso.

### Ejemplo
```java
try (BufferedReader lector = new BufferedReader(new FileReader("no_existe.txt"))) {
    System.out.println(lector.readLine());
} catch (IOException e) {
    System.out.println("No se pudo leer el fichero");
}
```

### Idea clave
No hay que ignorar la excepcion.
Hay que explicar al alumno que el fallo no siempre es "que Java va mal", sino que el recurso externo puede no estar disponible.

---

# BLOQUE 6 - PATRONES IMPORTANTES QUE DEBEN QUEDAR CLAROS

## 23. Patron 1: comprobar si existe antes de leer
```java
File archivo = new File("datos_ut12/notas.txt");

if (archivo.exists()) {
    // leer
} else {
    System.out.println("No existe el fichero");
}
```

Por que importa:
evita errores y da mensajes mas claros.

## 24. Patron 2: separar logica en metodos pequenos
En vez de meter todo en `main`, es mejor:
- `crearFicheroVacio()`
- `escribirSobrescribiendo()`
- `anadirContenido()`
- `leerFicheroLineaALinea()`

Por que importa:
- mejor lectura;
- mejor mantenimiento;
- mejor depuracion;
- mejor aprendizaje.

## 25. Patron 3: validar la entrada del usuario
```java
if (sc.hasNextInt()) {
    opcion = sc.nextInt();
    sc.nextLine();
} else {
    System.out.println("Debes introducir un numero");
    sc.nextLine();
}
```

Por que importa:
evita que el programa falle si el alumno mete texto en el menu.

---

# BLOQUE 7 - ERRORES TIPICOS DEL ALUMNO

## 26. Errores frecuentes
- Confundir `File` con `FileWriter`.
- Pensar que `createNewFile()` escribe contenido.
- Olvidar que `FileWriter` sobrescribe por defecto.
- Intentar leer un archivo que no existe.
- Olvidar `newLine()` y pegar todas las frases.
- Usar `split(" ")` y contar mal las palabras.
- No limpiar el buffer tras `nextInt()`.
- Intentar leer un PDF con `FileReader`.
- No comprobar si `listFiles()` ha devuelto `null`.

## 27. Como evitarlos
- Repetir siempre esta idea:
  `File` representa, `Reader` lee, `Writer` escribe.
- Distinguir muy bien sobrescribir frente a anadir.
- Usar `try-with-resources`.
- Validar entradas.
- Comprobar existencia del fichero antes de leer cuando tenga sentido.

---

# BLOQUE 8 - EJEMPLO COMPLETO MUY COMENTADO

## 28. Ejemplo integrador
```java
File archivo = new File("datos_ut12/ejemplo.txt");

if (!archivo.exists()) {
    archivo.createNewFile();
}

try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
    escritor.write("Primera linea");
    escritor.newLine();
    escritor.write("Segunda linea");
}

try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
    String linea;

    while ((linea = lector.readLine()) != null) {
        System.out.println(linea);
    }
}
```

### Explicacion por tramos
Tramo 1:
```java
File archivo = new File("datos_ut12/ejemplo.txt");
```
Representa la ruta del archivo.

Tramo 2:
```java
if (!archivo.exists()) {
    archivo.createNewFile();
}
```
Si no existe, lo crea.

Tramo 3:
```java
try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
```
Abre el fichero para escribir desde cero.

Tramo 4:
```java
escritor.write("Primera linea");
escritor.newLine();
escritor.write("Segunda linea");
```
Guarda dos lineas separadas correctamente.

Tramo 5:
```java
try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
```
Abre el mismo fichero para lectura.

Tramo 6:
```java
while ((linea = lector.readLine()) != null) {
    System.out.println(linea);
}
```
Recorre todas las lineas hasta el final.

---

# BLOQUE 9 - RELACION CON EL ARCHIVO JAVA DEL PROYECTO

## 29. Que hace `UT12_Ficheros_Final.java`
El programa del proyecto lleva esta teoria a practica con un menu.

Las partes mas importantes del archivo son:
- una carpeta base controlada;
- validacion de entradas;
- metodos pequenos;
- lectura y escritura con `BufferedReader` y `BufferedWriter`;
- demostracion guiada;
- tareas finales para el alumno.

## 30. Que partes son las mas importantes de toda la unidad
Si tuviera que resumir lo esencial, seria esto:

1. La clase `File` no lee ni escribe, solo representa rutas y consulta informacion.
2. `FileWriter` sobrescribe por defecto.
3. `FileWriter(ruta, true)` anade al final.
4. `BufferedReader` con `readLine()` es el patron mas claro para leer texto.
5. `try-with-resources` es la forma correcta y moderna de cerrar recursos.
6. Hay que distinguir texto de binario.
7. Validar entradas y comprobar rutas evita muchos fallos.

---

# BLOQUE 10 - COMO PROBAR

## 31. Compilacion y ejecucion
```bash
javac UT12_Ficheros_Final.java
java UT12_Ficheros_Final
```

## 32. Pruebas recomendadas

### Prueba 1: crear fichero
Entrada:
```text
1
alumnos.txt
```

Salida esperada:
```text
Fichero creado correctamente...
```

### Prueba 2: escribir sobrescribiendo
Entrada:
```text
2
alumnos.txt
Ana
Luis
FIN
```

Salida esperada:
```text
Contenido guardado sobrescribiendo el fichero.
```

### Prueba 3: anadir contenido
Entrada:
```text
3
alumnos.txt
Marta
FIN
```

Salida esperada:
```text
Contenido anadido correctamente.
```

### Prueba 4: leer el fichero
Entrada:
```text
4
alumnos.txt
```

Salida esperada:
```text
-> Ana
-> Luis
-> Marta
```

### Prueba 5: analizar
Entrada:
```text
8
alumnos.txt
```

Salida esperada aproximada:
```text
Lineas: 3
Palabras aproximadas: 3
Caracteres sin contar saltos de linea: ...
```

---

# BLOQUE 11 - AMPLIACION

## 33. Que podria entrar despues
Despues de dominar esta unidad, el alumno estara mejor preparado para:
- lectura de CSV;
- escritura de logs;
- serializacion;
- acceso a bases de datos;
- importacion y exportacion de datos;
- manejo de ficheros binarios con streams.

## 34. Ejercicios extra
1. Crea una opcion que renombre un fichero.
2. Crea una opcion que cuente vocales.
3. Crea una opcion que busque una palabra dentro de un fichero.
4. Muestra el numero de linea junto a cada linea leida.
5. Filtra y muestra solo los archivos `.txt` de mas de 20 bytes.

## 35. Preguntas de entendimiento
1. Que diferencia hay entre `File` y `FileWriter`?
2. Que diferencia hay entre sobrescribir y anadir?
3. Por que `BufferedReader` suele ser mejor que `FileReader` a secas para empezar?
4. Por que conviene usar `try-with-resources`?
5. Por que un PDF no debe leerse con `FileReader` como si fuera un texto normal?

## 36. Idea final que el alumno debe recordar
La frase-resumen correcta de esta unidad es:

```text
File representa la ruta.
Reader lee.
Writer escribe.
Buffered hace el trabajo mas comodo.
try-with-resources cierra bien los recursos.
```

## 37. Como revisar el documento junto al programa
1. Compilar:
```bash
javac UT12_Ficheros_Final.java
```
2. Ejecutar:
```bash
java UT12_Ficheros_Final
```
3. Probar opciones `1`, `2`, `3`, `4`, `5`, `6`, `7` y `8`.
4. Revisar que los archivos se creen dentro de `datos_ut12`.
5. Comprobar que al usar append no se borra el contenido anterior.
6. Comprobar que al sobrescribir si se sustituye el contenido anterior.

## 38. Checklist final
- [x] El `.md` explica los fundamentos de ficheros en Java.
- [x] Se ha mejorado la estructura visual.
- [x] Se han anadido trozos de codigo explicados por partes.
- [x] Se han reforzado las partes mas importantes de la unidad.
- [x] Se ha quitado el enfoque de "duracion de 1 hora".
- [x] Se incluyen errores tipicos, pruebas y ampliaciones.
- [ ] Pendiente solo de ajustar el tono o la profundidad si quieres que quede mas tipo "apunte academico" o mas tipo "guion del profesor".
