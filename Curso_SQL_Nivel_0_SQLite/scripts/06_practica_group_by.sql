-- ============================================================
-- ! Practica 06: contar y agrupar
-- ============================================================
-- * Meta: resumir datos en vez de mirar fila por fila.
-- * Requisito: antes ejecuta scripts/00_reset_biblioteca_nivel0.sql
-- * Idea:
-- *   COUNT(*) cuenta filas
-- *   GROUP BY agrupa por una columna

-- ? Ejemplo guiado
SELECT ciudad, COUNT(*) AS total_socios
FROM socios
GROUP BY ciudad;
-- * EXPECT: varias filas, una por ciudad.

-- TODO: cuenta cuantos libros hay en total.

-- TODO: cuenta cuantos prestamos hay en total.

-- TODO: muestra cuantos libros hay por genero.

-- TODO: muestra cuantos prestamos hay segun si estan devueltos o no.

-- TODO: muestra cuantas veces aparece cada socio en la tabla prestamos.
-- TODO: si quieres dejarlo mas bonito, usa JOIN para ver el nombre del socio.

-- TODO: ordena el resultado anterior de mayor a menor.
-- * Pista: usa ORDER BY total DESC.

-- * Recuerda:
-- * COUNT(*) cuenta filas.
-- * GROUP BY junta filas parecidas.
-- * Cuando termines, abre la pestana "Solucion" para comprobar.
