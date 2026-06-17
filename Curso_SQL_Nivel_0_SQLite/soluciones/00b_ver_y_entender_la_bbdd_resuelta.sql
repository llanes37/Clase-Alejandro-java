-- ============================================================
-- ! Solucion - Ver y entender la base de datos (00b)
-- ============================================================
-- * Recuerda ejecutar antes 00_reset_biblioteca_nivel0.sql

-- ------------------------------------------------------------
-- * TODO 1) Columnas de la tabla 'libros'
-- ------------------------------------------------------------
PRAGMA table_info(libros);
-- * Devuelve: libro_id (PK), titulo, genero, paginas, disponible.

-- ------------------------------------------------------------
-- * TODO 2) JOIN: socio + titulo de los prestamos NO devueltos
-- ------------------------------------------------------------
SELECT s.nombre AS socio, l.titulo AS libro, p.fecha_prestamo
FROM prestamos p
JOIN socios s ON p.socio_id = s.socio_id
JOIN libros l ON p.libro_id = l.libro_id
WHERE p.devuelto = 'NO';
-- * Resultado esperado (con los datos de ejemplo):
-- *   Ana Ruiz   - SQL para principiantes - 2026-02-10
-- *   Marta Gil  - SQL para principiantes - 2026-02-14

-- ------------------------------------------------------------
-- * TODO 3) Provocar un error de clave foranea a proposito
-- ------------------------------------------------------------
-- * Ejecuta las DOS lineas juntas (la PRAGMA debe ir en la misma
-- * ejecucion, porque en SQLite se reinicia en cada conexion).
PRAGMA foreign_keys = ON;
INSERT INTO prestamos (prestamo_id, socio_id, libro_id, fecha_prestamo, devuelto)
VALUES (100, 999, 1, '2026-03-02', 'NO');
-- * Esperado: "FOREIGN KEY constraint failed".
-- *
-- * Por que lo impide:
-- *   El prestamo apunta a socio_id = 999, pero NO existe ningun
-- *   socio con ese id. La clave foranea (FOREIGN KEY socio_id
-- *   REFERENCES socios) obliga a que todo prestamo pertenezca a un
-- *   socio real. Asi la base de datos evita datos "huerfanos" que
-- *   no se corresponden con nada. Eso es la INTEGRIDAD REFERENCIAL.
