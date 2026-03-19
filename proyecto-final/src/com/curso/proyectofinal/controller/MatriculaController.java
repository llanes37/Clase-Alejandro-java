/*
 * ==================================================================================
 * // ! CONTROLLER — MatriculaController (Capa de Negocio)
 * ==================================================================================
 *
 * // * RESPONSABILIDADES:
 *   - Validar que el Alumno y el Curso existan antes de crear una matrícula.
 *   - Validar ventana temporal: la fecha de matrícula debe estar dentro de
 *     [fechaInicio, fechaFin] del curso (si se han definido).
 *   - Gestionar el estado de las matrículas: ACTIVA → ANULADA.
 *
 * // ? POR QUÉ TRES REPOSITORIOS:
 *   MatriculaController necesita:
 *     - MatriculaRepository: CRUD de matrículas.
 *     - AlumnoRepository: verificar que el alumnoId existe.
 *     - CursoRepository: verificar que el cursoId existe (y obtener sus fechas).
 *   Las reglas de negocio usan datos de los tres repositorios.
 *
 * // ! REGLAS DE NEGOCIO IMPLEMENTADAS:
 *   1. El alumno debe existir (lanza ValidationException si no).
 *   2. El curso debe existir (lanza ValidationException si no).
 *   3. La fecha no puede ser anterior a fechaInicio del curso (si existe).
 *   4. La fecha no puede ser posterior a fechaFin del curso (si existe).
 *   5. Si fechaStr es vacía, se usa la fecha de hoy como fecha de matrícula.
 *
 * // TODO (Alumno):
 *   - [ ] Evitar duplicados: no permitir la misma (alumnoId, cursoId) ACTIVA dos veces.
 *   - [ ] Añadir finalizar(String id) → estado FINALIZADA si fecha > fin del curso.
 *   - [ ] Listar matrículas por alumno: listarPorAlumno(String alumnoId).
 *   - [ ] Listar matrículas por curso: listarPorCurso(String cursoId).
 * ==================================================================================
 */
package com.curso.proyectofinal.controller;

import com.curso.proyectofinal.exception.ValidationException;
import com.curso.proyectofinal.model.*;
import com.curso.proyectofinal.repository.AlumnoRepository;
import com.curso.proyectofinal.repository.CursoRepository;
import com.curso.proyectofinal.repository.MatriculaRepository;
import com.curso.proyectofinal.util.DateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MatriculaController {

    // ========================================
    // DEPENDENCIAS (inyectadas por constructor)
    // ========================================

    // * Repositorio de matrículas: operaciones CRUD sobre Matricula
    private final MatriculaRepository repo;

    // * Repositorio de alumnos: solo para verificar existencia y obtener nombre
    private final AlumnoRepository alumnoRepo;

    // * Repositorio de cursos: verificar existencia y obtener fechas del curso
    private final CursoRepository cursoRepo;

    /*
     * // * CONSTRUCTOR CON INYECCIÓN DE DEPENDENCIAS
     * // ? En Spring Boot, esto se haría con @Autowired automáticamente.
     * Aquí lo hacemos manualmente en Application.java para entender el concepto.
     */
    public MatriculaController(MatriculaRepository repo,
            AlumnoRepository alumnoRepo,
            CursoRepository cursoRepo) {
        this.repo = repo;
        this.alumnoRepo = alumnoRepo;
        this.cursoRepo = cursoRepo;
    }

    // ========================================
    // OPERACIONES CRUD + NEGOCIO
    // ========================================

    /*
     * // ! LISTAR todas las matrículas almacenadas
     * // * Retorna lista vacía si no hay ninguna (nunca null).
     */
    public List<Matricula> listar() {
        return repo.findAll(); // * Delega al repo; sin lógica de negocio aquí
    }

    /*
     * // ! MATRICULAR un alumno en un curso (crea matrícula ACTIVA)
     *
     * // * FLUJO:
     * 1. Buscar el Alumno por alumnoId (lanza excepción si no existe).
     * 2. Buscar el Curso por cursoId (lanza excepción si no existe).
     * 3. Determinar la fecha de matrícula (hoy si no se especifica).
     * 4. Validar ventana temporal del curso.
     * 5. Crear la matrícula en estado ACTIVA.
     * 6. Persistir y retornar.
     *
     * // ! CONTRATOS:
     * - El alumno DEBE existir antes de matricularse.
     * - El curso DEBE existir antes de matricularse.
     * - La fecha de matrícula debe caer dentro de [inicio, fin] del curso.
     *
     * // ? POR QUÉ RETORNAMOS Alumno y Curso y no solo sus IDs:
     * Para poder mostrar los NOMBRES en el feedback, no los UUIDs crípticos.
     *
     * @param alumnoId ID del alumno (debe existir)
     * 
     * @param cursoId ID del curso (debe existir)
     * 
     * @param fechaStr Fecha de matrícula (yyyy-MM-dd) o vacío para usar hoy
     * 
     * @return Matrícula creada en estado ACTIVA
     * 
     * @throws ValidationException si alumno/curso no existen o fecha fuera de
     * ventana
     */
    public Matricula matricular(String alumnoId, String cursoId, String fechaStr) {
        // ========================================
        // 1. VERIFICAR ALUMNO
        // ========================================
        // ! Si el alumno no existe, no tiene sentido continuar
        // * orElseThrow lanza la excepción con mensaje claro para el usuario
        Alumno a = alumnoRepo.findById(alumnoId)
                .orElseThrow(() -> new ValidationException(
                        "Alumno no encontrado con ID: " + alumnoId
                                + ". Crea primero el alumno desde el menú de Alumnos."));

        // ========================================
        // 2. VERIFICAR CURSO
        // ========================================
        // ! Si el curso no existe, no tiene sentido continuar
        Curso c = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new ValidationException(
                        "Curso no encontrado con ID: " + cursoId
                                + ". Crea primero el curso desde el menú de Cursos."));

        // ========================================
        // 3. DETERMINAR FECHA DE MATRÍCULA
        // ========================================
        // ? Si el usuario deja la fecha en blanco, usamos la fecha actual (hoy)
        LocalDate fecha = (fechaStr == null || fechaStr.isBlank())
                ? LocalDate.now()
                : DateUtils.parse(fechaStr);

        // ========================================
        // 4. VALIDAR VENTANA TEMPORAL DEL CURSO
        // ========================================
        // ! Regla: la fecha de matrícula no puede ser antes del inicio del curso
        if (c.getFechaInicio() != null && fecha.isBefore(c.getFechaInicio())) {
            throw new ValidationException(
                    "La fecha de matrícula (" + fecha + ") no puede ser anterior "
                            + "al inicio del curso '" + c.getNombre() + "' ("
                            + c.getFechaInicio() + ")");
        }
        // ! Regla: la fecha de matrícula no puede ser después del fin del curso
        if (c.getFechaFin() != null && fecha.isAfter(c.getFechaFin())) {
            throw new ValidationException(
                    "La fecha de matrícula (" + fecha + ") no puede ser posterior "
                            + "al fin del curso '" + c.getNombre() + "' ("
                            + c.getFechaFin() + ")");
        }

        // TODO: Evitar duplicado — no matricular al mismo alumno en el mismo curso
        // ACTIVO dos veces

        // ========================================
        // 5. CREAR Y PERSISTIR LA MATRÍCULA
        // ========================================
        String id = UUID.randomUUID().toString(); // * UUID garantiza unicidad global
        // * Guardamos IDs (no objetos completos) para mantener CSV simple
        Matricula m = new Matricula(id, a.getId(), c.getId(), fecha, EstadoMatricula.ACTIVA);
        Matricula guardada = repo.save(m);

        // * Feedback enriquecido: muestra nombres, no IDs
        System.out.println("  → Matricula creada:");
        System.out.println("     Alumno: " + a.getNombre() + " (ID: " + a.getId() + ")");
        System.out.println("     Curso:  " + c.getNombre() + " (ID: " + c.getId() + ")");
        System.out.println("     Fecha:  " + fecha + " | Estado: ACTIVA");

        return guardada;
    }

    /*
     * // ! ANULAR una matrícula (ACTIVA → ANULADA)
     *
     * // * FLUJO:
     * 1. Buscar la matrícula por ID.
     * 2. Si no existe → lanza ValidationException (no retorna false).
     * 3. Si existe → cambiar estado a ANULADA y persistir.
     *
     * // ? POR QUÉ lanzamos excepción en lugar de retornar false:
     * "Matrícula no encontrada" es un error que el usuario debe saber,
     * no un simple boolean. La excepción lleva un mensaje descriptivo.
     *
     * @param matriculaId ID de la matrícula a anular
     * 
     * @return true si se anuló correctamente (siempre, o lanza excepción)
     * 
     * @throws ValidationException si la matrícula no existe
     */
    public boolean anular(String matriculaId) {
        // * Buscar la matrícula; si no existe, lanzar excepción con mensaje claro
        Matricula m = repo.findById(matriculaId)
                .orElseThrow(() -> new ValidationException(
                        "Matrícula no encontrada con ID: " + matriculaId));

        // * Transición de estado: ACTIVA → ANULADA
        m.setEstado(EstadoMatricula.ANULADA); // ! Solo cambia el estado, no borra el registro
        repo.update(m); // * Persistir el cambio en el CSV

        System.out.println("  → Matrícula ANULADA correctamente.");
        return true;
    }
}
