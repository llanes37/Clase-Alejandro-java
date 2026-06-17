-- ============================================================
-- ! Practica 02: buscar y filtrar con WHERE
-- ============================================================
-- * Meta: pedir solo las filas que te interesan.
-- * Requisito: antes ejecuta scripts/00_reset_biblioteca_nivel0.sql
-- * Pistas clave:
-- *   - texto siempre con comillas: 'Madrid'
-- *   - NULL no se compara con =, se usa IS NULL
-- *   - LIKE sirve para buscar por patron (A%)

-- ? Ejemplo guiado
SELECT nombre, ciudad
FROM socios
WHERE ciudad = 'Madrid';
-- * EXPECT: deberian salir 2 filas (Ana Ruiz y Marta Gil).

-- TODO: muestra los libros del genero 'Fantasia'.

-- TODO: muestra los libros con mas de 200 paginas.

-- TODO: muestra los socios que no tienen telefono.

-- TODO: muestra los prestamos que NO estan devueltos.

-- TODO: muestra los socios cuyo nombre empieza por 'A'.

-- TODO: muestra los libros con menos de 200 paginas.

-- * Pista:
-- * Para datos vacios usa IS NULL.
-- * Para texto parcial usa LIKE.
-- ? Si una consulta no devuelve filas:
-- * No siempre es error. Puede ser que tu filtro no coincida con los datos.
-- * Cuando termines, abre la pestana "Solucion" para comprobar.
