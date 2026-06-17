# 🧱 Fundamentos: ¿qué es una base de datos? (empieza por aquí)

> Antes de escribir nada de SQL, vamos a **entender** qué es una base de datos y,
> sobre todo, **cómo se conectan las tablas entre sí**. Sin tecnicismos y muy
> visual. Cuando esto te quede claro, todo lo demás del curso es mucho más fácil.

---

## 1. Una base de datos es un conjunto de tablas relacionadas

Imagina una **biblioteca**. Para gestionarla necesitas guardar tres cosas:

- Los **socios** (las personas).
- Los **libros**.
- Los **préstamos** (quién se ha llevado qué y cuándo).

Cada una de esas cosas es una **tabla**. Una base de datos es, simplemente, **un
grupo de tablas que se relacionan entre sí**. Nada más misterioso que eso.

```
            BASE DE DATOS "biblioteca_nivel0"
        ┌───────────┐   ┌───────────┐   ┌───────────┐
        │  socios   │   │  libros   │   │ prestamos │
        └───────────┘   └───────────┘   └───────────┘
            personas        libros       quién llevó qué
```

---

## 2. Una tabla = filas y columnas

Una tabla es como una hoja de Excel: tiene **columnas** (los campos) y **filas**
(los registros). Esta es la tabla `socios` de verdad de tu base de datos:

| socio_id | nombre     | ciudad   | telefono   |
|---------:|------------|----------|------------|
| 1        | Ana Ruiz   | Madrid   | 600111111  |
| 2        | Luis Perez | Sevilla  | *(vacío)*  |
| 3        | Marta Gil  | Madrid   | 600333333  |

- Cada **columna** es un dato que guardamos de cada socio (nombre, ciudad...).
- Cada **fila** es **un** socio concreto.
- ¿Ves el `telefono` vacío de Luis? Eso es un **NULL**: "no hay dato". Importante,
  porque más adelante NULL se trata distinto al resto.

---

## 3. La clave primaria (PK): el carné de cada fila

Fíjate en la columna `socio_id`. Es un número **único** para cada socio: el 1 es
Ana, el 2 es Luis... no se repite jamás. Esa columna es la **clave primaria (PK)**.

> La clave primaria es como el **DNI** de cada fila: identifica a una fila y solo a
> una. Sirve para poder señalar a un socio concreto sin confundirlo con otro.

Todas las tablas tienen una: `libros` usa `libro_id` y `prestamos` usa `prestamo_id`.

---

## 4. El problema que resuelven las relaciones

Imagina que guardáramos **todo en una sola tabla** de préstamos, repitiendo los
datos del socio en cada fila:

| prestamo | nombre   | ciudad | titulo                 |
|---------:|----------|--------|------------------------|
| 1        | Ana Ruiz | Madrid | SQL para principiantes |
| 3        | Ana Ruiz | Madrid | Viaje al centro        |

¿Ves el problema? **"Ana Ruiz, Madrid" está escrito dos veces.** Si Ana cambia de
ciudad, habría que corregir muchas filas. Y si se equivoca una, los datos se
contradicen. **Repetir datos es malo.**

La solución: guardar a Ana **una sola vez** en `socios`, y que la tabla de
préstamos solo **apunte** a ella con su número. Eso es una **relación**.

---

## 5. Cómo "se abre" una relación (lo más importante)

Mira la tabla `prestamos`. No guarda el nombre del socio ni el título del libro:
guarda sus **números** (`socio_id` y `libro_id`).

| prestamo_id | socio_id | libro_id | fecha      | devuelto |
|------------:|---------:|---------:|------------|----------|
| 1           | **1**    | 2        | 2026-02-10 | NO       |

Ese `socio_id = 1` es una flecha que **apunta** a la fila 1 de `socios`. Al
"seguir la flecha", el número se convierte en una persona de verdad:

```
   prestamos                              socios
 ┌────────────┬──────────┐             ┌──────────┬────────────┬─────────┐
 │ prestamo_id│ socio_id │             │ socio_id │ nombre     │ ciudad  │
 ├────────────┼──────────┤             ├──────────┼────────────┼─────────┤
 │     1      │    1  ●──┼──────────── →│    1     │ Ana Ruiz   │ Madrid  │
 └────────────┴──────────┘   "sigue     └──────────┴────────────┴─────────┘
                              la flecha"
```

Eso es una **relación**: el `socio_id` del préstamo se "abre" y te lleva a toda la
información del socio. Lo mismo pasa con `libro_id`, que apunta a la tabla `libros`.

---

## 6. La clave foránea (FK): la columna que apunta a otra tabla

A esa columna que guarda el id de otra tabla la llamamos **clave foránea (FK)**.

- `prestamos.socio_id` es una FK que apunta a `socios.socio_id`.
- `prestamos.libro_id` es una FK que apunta a `libros.libro_id`.

> **Clave primaria (PK)** = el id propio de la fila (su DNI).
> **Clave foránea (FK)** = una columna que guarda el DNI de una fila de **otra** tabla.

La tabla `prestamos` es el **puente** que une socios con libros: cada préstamo
conecta **un socio** con **un libro**.

---

## 7. El mapa completo de nuestra biblioteca

```
        socios                                 libros
   ┌──────────────┐                       ┌──────────────┐
   │ socio_id (PK)│                       │ libro_id (PK)│
   │ nombre       │                       │ titulo       │
   │ ciudad       │                       │ genero       │
   └──────┬───────┘                       └──────┬───────┘
          │ 1                                     │ 1
          │                                       │
          │ muchos                          muchos│
          └──────────────┐         ┌──────────────┘
                     ┌────┴─────────┴────┐
                     │     prestamos     │
                     │ prestamo_id (PK)  │
                     │ socio_id   (FK) ──┼─→ apunta a socios
                     │ libro_id   (FK) ──┼─→ apunta a libros
                     │ fecha, devuelto   │
                     └───────────────────┘
```

Léelo así: **un** socio puede tener **muchos** préstamos, y **un** libro puede
aparecer en **muchos** préstamos. A esa relación se la llama **"uno a muchos" (1:N)**.

> 💡 **Pruébalo ahora:** pulsa el botón **"Ver esquema"** (arriba a la derecha) y
> verás este mismo mapa dibujado automáticamente desde la base de datos real, con
> las marcas **PK** y **FK** en cada columna.

---

## 8. Resumen (lo que ya entiendes)

- Una **base de datos** es un grupo de **tablas** relacionadas.
- Una **tabla** tiene **columnas** (campos) y **filas** (registros).
- La **clave primaria (PK)** identifica cada fila (su DNI).
- Guardar los datos **una sola vez** y **apuntar** a ellos evita repetir y
  equivocarse: eso es una **relación**.
- La **clave foránea (FK)** es la columna que apunta a otra tabla.
- Un `JOIN` (lo verás en la Práctica 05) es "seguir la flecha" para convertir los
  ids en información que una persona puede leer.

---

### Siguiente paso

Ve a **"Paso 00 - Preparar base"**: dejarás la base de datos lista en memoria y
empezarás a mirarla por dentro. Y si quieres verla directamente con las
extensiones de VS Code, tienes la guía **"Ver y entender la base de datos"**.

> No tienes que memorizar nada de esto todavía. Con haberlo *entendido* una vez,
> el resto del curso te va a sonar a algo conocido.
