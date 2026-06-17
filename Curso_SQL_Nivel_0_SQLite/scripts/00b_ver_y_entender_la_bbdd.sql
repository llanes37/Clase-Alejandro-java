-- ============================================================
-- ! Curso SQL Nivel 0 (SQLite) - Ver y entender la base de datos
-- ============================================================
-- * Acompaña a la guia: Guia_Ver_y_Entender_la_BBDD_en_VSCode.md
-- * Objetivo: NO escribir consultas todavia, sino MIRAR la base
-- *           de datos por dentro: tablas, columnas y relaciones.
-- *
-- * Antes de empezar: ejecuta 00_reset_biblioteca_nivel0.sql
-- * (si no, daria "no such table").
-- *
-- * Como ejecutar en VS Code:
-- *   1) Ctrl + Shift + P
-- *   2) SQLite: Run Query
-- *   3) Elige biblioteca_nivel0.db
-- *   Consejo: puedes seleccionar UNA sentencia y usar Run Query.

-- ============================================================
-- ! PARTE 1 - Ver la ESTRUCTURA (que hay dentro)
-- ============================================================

-- ? Que tablas tiene esta base de datos?
SELECT name FROM sqlite_master WHERE type = 'table';

-- ? Que columnas tiene cada tabla? (nombre, tipo, NOT NULL, clave primaria)
PRAGMA table_info(socios);
PRAGMA table_info(libros);
PRAGMA table_info(prestamos);

-- ============================================================
-- ! PARTE 2 - Ver las RELACIONES
-- ============================================================

-- ? A que tablas apunta 'prestamos'? (sus claves foraneas)
-- * Veras que socio_id -> socios y libro_id -> libros.
PRAGMA foreign_key_list(prestamos);

-- * Los ids sueltos no dicen mucho. Un JOIN sigue las relaciones
-- * y convierte numeros en informacion real:
SELECT p.prestamo_id, s.nombre AS socio, l.titulo AS libro, p.devuelto
FROM prestamos p
JOIN socios s ON p.socio_id = s.socio_id
JOIN libros l ON p.libro_id = l.libro_id;

-- ============================================================
-- ! PARTE 3 - Romperlo a proposito (para ENTENDER las reglas)
-- ============================================================
-- * IMPORTANTE: en SQLite hay que activar las claves foraneas en
-- * CADA ejecucion. Ejecuta este bloque COMPLETO de golpe, o el
-- * INSERT malo no dara error.

-- ? Que pasa si presto un libro a un socio que NO existe?
PRAGMA foreign_keys = ON;
INSERT INTO prestamos (prestamo_id, socio_id, libro_id, fecha_prestamo, devuelto)
VALUES (99, 999, 1, '2026-03-01', 'NO');
-- * Esperado: "FOREIGN KEY constraint failed".
-- * La base de datos te PROTEGE: no deja prestamos de socios fantasma.

-- ? Que pasa si pongo un valor que no esta permitido?
INSERT INTO libros (libro_id, titulo, genero, paginas, disponible)
VALUES (99, 'Libro raro', 'Misterio', 100, 'QUIZA');
-- * Esperado: "CHECK constraint failed".
-- * 'disponible' solo admite 'SI' o 'NO'.

-- ============================================================
-- ! PARTE 4 - Ver lo que NO esta relacionado (huecos)
-- ============================================================

-- ? Que socios no han pedido ningun prestamo?
SELECT s.nombre
FROM socios s
LEFT JOIN prestamos p ON s.socio_id = p.socio_id
WHERE p.prestamo_id IS NULL;

-- ? Que libros no se han prestado nunca?
SELECT l.titulo
FROM libros l
LEFT JOIN prestamos p ON l.libro_id = p.libro_id
WHERE p.prestamo_id IS NULL;

-- ============================================================
-- ! TODO del alumno (resuelvelo sin mirar la solucion)
-- ============================================================
-- TODO: 1) Muestra las columnas de la tabla 'libros'.
-- TODO: 2) Escribe un JOIN que muestre nombre del socio y titulo
-- TODO:    del libro SOLO de los prestamos NO devueltos.
-- TODO: 3) Provoca un error de clave foranea a proposito y explica
-- TODO:    con un comentario por que la base de datos lo impide.
