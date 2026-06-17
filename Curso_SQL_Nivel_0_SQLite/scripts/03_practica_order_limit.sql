-- ============================================================
-- ! Practica 03: ordenar y limitar
-- ============================================================
-- * Meta: ver primero lo mas importante.
-- * Requisito: antes ejecuta scripts/00_reset_biblioteca_nivel0.sql
-- * Idea:
-- *   ORDER BY ordena
-- *   LIMIT recorta filas

-- ? Ejemplo guiado
SELECT titulo, paginas
FROM libros
ORDER BY paginas DESC;
-- * EXPECT: "Viaje al centro" debe salir arriba (310 paginas).

-- TODO: muestra los socios ordenados por nombre.

-- TODO: muestra los libros del mas corto al mas largo.

-- TODO: muestra los 3 libros mas largos.

-- TODO: muestra los 2 primeros socios ordenados por ciudad.

-- TODO: muestra los prestamos mas recientes primero.

-- * Regla mental:
-- * 1) SELECT
-- * 2) FROM
-- * 3) ORDER BY
-- * 4) LIMIT
-- * Cuando termines, abre la pestana "Solucion" para comprobar.
