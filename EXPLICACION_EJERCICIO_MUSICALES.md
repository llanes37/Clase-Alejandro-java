# Explicacion completa del ejercicio: Gestion de musicales

## 1) Que pide exactamente el enunciado

El ejercicio plantea una empresa de espectaculos que quiere informatizar la venta de entradas.

Datos a modelar:
- `Espectaculo`: codigo, tipo y si es para todos los publicos.
- `Codigo`: formato obligatorio de 4 numeros + 2 letras mayusculas.
- `Musical` (hereda de `Espectaculo`): nombre (no repetido), precio y butacas.
- `Butaca`: numero secuencial y estado libre/ocupada.

Condiciones principales:
- Guardar **15 musicales** en una estructura **estatica**.
- Menu con opciones:
1. Gestionar venta de entradas.
2. Modificar precio (rebaja del 15%).
3. Calcular ingresos maximos (si se vendiera todo).
4. Salir.
- Crear subprogramas auxiliares para lectura y reutilizacion de logica.

---

## 2) Diseno orientado a objetos aplicado

Tu solucion separa muy bien responsabilidades en clases:

- `Codigo`: encapsula y valida el formato del identificador.
- `Butaca`: representa un asiento individual.
- `Espectaculo`: clase base con atributos comunes.
- `Musical extends Espectaculo`: añade nombre, precio y gestion de butacas.
- `GestorEspectaculos`: clase principal con menu, array estatico y operaciones.

Esto cumple el bloque de herencia del examen: `Musical` reutiliza atributos base de `Espectaculo` y agrega su parte especifica.

---

## 3) Estructura de datos estatica

Se usa:

```java
private static final Musical[] musicales = new Musical[15];
```

Esto satisface literalmente el requisito de "estructura estatica" para almacenar los 15 musicales.

En `inicializarMusicales()` cargas los 15 elementos con datos de ejemplo. Es correcto para arrancar el sistema con informacion disponible.

---

## 4) Explicacion de cada clase

## 4.1 Clase `Codigo`

Objetivo: asegurar que el codigo de espectaculo siempre tenga formato valido.

Regla implementada:
- Longitud 6.
- Posiciones 0-3: digitos.
- Posiciones 4-5: letras mayusculas.

Si el formato falla, lanzas `IllegalArgumentException`. Esto esta bien porque evita crear objetos invalidos desde el origen.

### Por que esta bien
- Centraliza validacion en un solo sitio.
- Evita repetir comprobaciones en todo el programa.
- Mejora robustez de datos.

---

## 4.2 Clase `Butaca`

Representa un asiento con:
- `numero`
- `libre` (boolean)

El constructor inicializa siempre `libre = true`, cumpliendo el enunciado de que todas las butacas empiezan libres.

---

## 4.3 Clase `Espectaculo`

Clase base con atributos comunes:
- `Codigo codigo`
- `String tipo`
- `boolean publico`

Da una base limpia para futuros tipos (obra teatral, concierto, etc.), aunque aqui solo uses `Musical`.

---

## 4.4 Clase `Musical` (herencia)

Hereda de `Espectaculo` y agrega:
- `nombre`
- `precioEntrada`
- `Butaca[] butacas`

Constante:
```java
private static final int NUM_BUTACAS = 100;
```

En el constructor, creas 100 butacas numeradas del 1 al 100 y libres.

### Metodos clave

- `venderEntradas(int cantidad)`:
1. Comprueba que hay suficientes libres.
2. Marca como ocupadas las primeras butacas libres hasta completar la cantidad.

- `contarButacasLibres()`:
Recorre el array y cuenta las butacas con `isLibre() == true`.

- `calcularImporteVenta(int cantidad)`:
`cantidad * precioEntrada`.

- `reducirPrecio(double porcentaje)`:
Aplica reduccion porcentual al precio actual.

- `calcularRecaudacionTotal()`:
Calcula el maximo teorico por musical (`NUM_BUTACAS * precioEntrada`).

Esto cubre exactamente lo que pide el examen sobre venta, descuento e ingresos.

---

## 5) Flujo del programa principal

En `main`:
1. Inicializas `Scanner`.
2. Cargas los 15 musicales.
3. Ejecutas bucle de menu hasta salir.

El `switch` gestiona:
- Opcion 1 -> `gestionarVentaEntradas()`
- Opcion 2 -> `modificarPrecio()`
- Opcion 3 -> `calcularIngresosMaximos()`
- Opcion 4 -> salida

Esta estructura es clara, mantenible y adecuada para examen.

---

## 6) Opcion 1 del menu: Gestion de venta de entradas

Metodo: `gestionarVentaEntradas()`

Secuencia:
1. Pide nombre del musical.
2. Busca con `buscarMusicalPorNombre(...)`.
3. Si no existe, informa error.
4. Si existe, pide cantidad de entradas.
5. Valida cantidad > 0.
6. Comprueba disponibilidad de butacas.
7. Ejecuta venta y muestra importe total.

Puntos fuertes:
- Manejo de errores completo (musical no existe, cantidad invalida, falta de butacas).
- Mensajes claros de exito y fallo.
- Logica separada en metodos reutilizables.

---

## 7) Opcion 2 del menu: Modificar precio

Metodo: `modificarPrecio()`

Secuencia:
1. Pide nombre del musical.
2. Valida existencia.
3. Guarda precio anterior.
4. Aplica descuento del 15% con `reducirPrecio(15)`.
5. Muestra precio nuevo y ahorro.

Esto coincide exactamente con la consigna del examen.

---

## 8) Opcion 3 del menu: Calcular ingresos maximos

Metodo: `calcularIngresosMaximos()`

Que hace:
- Recorre los 15 musicales.
- Suma para cada uno su recaudacion maxima teorica (`100 * precio actual`).
- Muestra total de empresa y desglose por musical.

Interpretacion correcta del enunciado: "si se vendieran todas las entradas de todos los musicales".

---

## 9) Subprogramas auxiliares (pedido explicito del examen)

Tu codigo incluye auxiliares correctos:

- `leerString(String mensaje)`
Lee texto y elimina espacios extremos con `trim()`.

- `leerEntero(String mensaje)`
Valida que el usuario escriba un entero, repitiendo hasta que sea valido.

- `buscarMusicalPorNombre(String nombre)`
Busqueda case-insensitive con `equalsIgnoreCase`.

- `generarLinea(int longitud)`
Construye lineas para el menu sin depender de `repeat()`.

Esto demuestra modularidad y reutilizacion, exactamente lo que se espera en este tipo de practica.

---

## 10) Relacion requisito -> implementacion

- 15 musicales en estructura estatica -> `Musical[] musicales = new Musical[15]`.
- Herencia -> `class Musical extends Espectaculo`.
- Validacion de codigo -> clase `Codigo` con control de formato.
- Butacas libres/ocupadas -> clase `Butaca` + metodos de venta/conteo.
- Venta de entradas -> `gestionarVentaEntradas()` + `venderEntradas()`.
- Rebaja 15% -> `modificarPrecio()` + `reducirPrecio(15)`.
- Calculo de ingresos -> `calcularIngresosMaximos()` + `calcularRecaudacionTotal()`.
- Subprograma de lectura de cadenas -> `leerString(...)`.

Resultado: el ejercicio queda cubierto de forma completa y coherente.

---

## 11) Observaciones tecnicas utiles para defensa oral

Si tienes que explicarlo en clase o examen oral, estas ideas te ayudan:

- Separacion de responsabilidades: cada clase hace una cosa concreta.
- Encapsulacion: atributos privados y acceso por metodos.
- Herencia real: `Musical` reutiliza estructura comun de `Espectaculo`.
- Validacion de entrada: evita errores por datos mal escritos.
- Estructura estatica: se respeta el requisito sin colecciones dinamicas.
- Complejidad: operaciones lineales simples, adecuadas para 15 musicales y 100 butacas.

---

## 12) Posibles mejoras (no obligatorias para aprobar)

No son necesarias para cumplir el enunciado, pero podrias mencionarlas:

- Evitar nombres duplicados de musical en la carga inicial.
- Permitir elegir butacas concretas en vez de asignar primeras libres.
- Persistir datos en fichero para no perder ventas al cerrar.
- Separar cada clase en su propio archivo Java.

---

## 13) Concluson

Tu implementacion cumple los tres bloques clave del examen:
- Modelado OO con herencia.
- Gestion de datos en estructura estatica.
- Menu funcional con las 3 operaciones pedidas (venta, descuento, ingresos).

Ademas, esta bien organizada, legible y con validaciones suficientes para considerarse una solucion completa del ejercicio.
