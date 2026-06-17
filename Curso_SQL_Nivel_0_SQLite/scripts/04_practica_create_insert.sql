-- ============================================================
-- ! Practica 04: crear una tabla e insertar datos
-- ============================================================
-- * Meta: construir una tabla muy simple desde cero.
-- * Esta practica SI cambia la base de datos (crea tablas nuevas).
-- * Si repites la practica, estas lineas dejan el entorno limpio.

DROP TABLE IF EXISTS talleres;
DROP TABLE IF EXISTS salas_estudio;

-- ? Ejemplo guiado
CREATE TABLE IF NOT EXISTS salas_estudio (
    sala_id INTEGER PRIMARY KEY,
    nombre TEXT NOT NULL,
    plazas INTEGER NOT NULL
);
-- * Como leer esto:
-- *   - sala_id identifica cada sala (no se repite)
-- *   - nombre no puede ser NULL
-- *   - plazas es un numero obligatorio

-- TODO: inserta una fila con sala_id 1, nombre 'Sala Norte' y 12 plazas.

-- TODO: inserta dos filas mas en una sola sentencia:
-- TODO: 'Sala Centro' con 8 plazas
-- TODO: 'Sala Silencio' con 20 plazas

-- TODO: consulta todas las filas de salas_estudio.

-- TODO: crea otra tabla llamada talleres con:
-- TODO: taller_id, titulo, duracion_horas

-- TODO: inserta dos talleres inventados.

-- TODO: consulta la tabla talleres.

-- * Consejo:
-- * Despues de cada CREATE o INSERT, comprueba el resultado con SELECT.
-- ? Si te da "UNIQUE constraint failed":
-- * Has repetido una PK. Solucion: vuelve a ejecutar el script (ya hace DROP) o cambia el id.
-- * Cuando termines, abre la pestana "Solucion" para comprobar.
