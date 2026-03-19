/*
 * ==================================================================================
 * // ! CONTROLLER — CursoController (Capa de Negocio)
 * ==================================================================================
 *
 * // * RESPONSABILIDADES:
 *   - Validar nombre, tipo (ONLINE/PRESENCIAL), fechas y precio.
 *   - Aplicar reglas temporales: fechaFin >= fechaInicio.
 *   - Convertir strings de tipo a enum CursoTipo.
 *   - Delegar la persistencia en CursoRepository.
 *
 * // ! REGLAS DE NEGOCIO IMPLEMENTADAS:
 *   1. Nombre obligatorio (no vacío).
 *   2. Tipo válido: solo "ONLINE" o "PRESENCIAL" (enum CursoTipo).
 *   3. Precio >= 0 (no puede ser negativo).
 *   4. Fecha fin >= Fecha inicio (el curso no puede terminar antes de empezar).
 *
 * // ? POR QUÉ USAMOS ENUM PARA EL TIPO:
 *   Un String libre permitiría "ONLINE", "online", "On-line", "presencial", etc.
 *   El enum CursoTipo restringe los valores posibles a los definidos, evitando
 *   errores de tipografía y facilitando comparaciones.
 *
 * // TODO (Alumno):
 *   - [ ] Añadir listarPorTipo(String tipo) → filtrar cursos ONLINE o PRESENCIAL.
 *   - [ ] Añadir regla de duración máxima: <= 365 días.
 *   - [ ] Añadir actualizarPrecio(String id, double nuevoPrecio) con validación.
 * ==================================================================================
 */
package com.curso.proyectofinal.controller;

import com.curso.proyectofinal.exception.ValidationException;
import com.curso.proyectofinal.model.Curso;
import com.curso.proyectofinal.model.CursoTipo;
import com.curso.proyectofinal.repository.CursoRepository;
import com.curso.proyectofinal.util.DateUtils;
import com.curso.proyectofinal.util.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CursoController {

    // ========================================
    // DEPENDENCIAS
    // ========================================

    // * Repositorio de cursos: responsable de leer/escribir el fichero CSV
    private final CursoRepository repo;

    /*
     * // * CONSTRUCTOR CON INYECCIÓN DE DEPENDENCIAS
     * // ? Patrón idéntico a AlumnoController y MatriculaController.
     */
    public CursoController(CursoRepository repo) {
        this.repo = repo;
    }

    // ========================================
    // OPERACIONES CRUD + NEGOCIO
    // ========================================

    /*
     * // ! LISTAR todos los cursos almacenados
     * // * Retorna lista vacía si no hay ninguno (nunca null).
     */
    public List<Curso> listar() {
        return repo.findAll(); // * Delega directamente al repo
    }

    /*
     * // ! CREAR un nuevo curso
     *
     * // * FLUJO:
     * 1. Validar nombre (obligatorio).
     * 2. Validar precio (>= 0).
     * 3. Convertir el tipo String → CursoTipo enum.
     * 4. Parsear las fechas de inicio y fin.
     * 5. Validar que fin >= inicio (regla temporal).
     * 6. Crear el curso con UUID e ID.
     * 7. Persistir y retornar.
     *
     * // ! GESTIÓN DE ERRORES:
     * - Si tipo no es "ONLINE" ni "PRESENCIAL" → IllegalArgumentException (del
     * enum).
     * - Si formato de fecha incorrecto → ValidationException (de DateUtils).
     * - Si fin < inicio → ValidationException (regla de negocio).
     * - Si precio < 0 → IllegalArgumentException (de Validator).
     *
     * @param nombre Nombre del curso (obligatorio)
     * 
     * @param tipoStr Tipo: "ONLINE" o "PRESENCIAL" (case-insensitive)
     * 
     * @param fIniStr Fecha de inicio (yyyy-MM-dd)
     * 
     * @param fFinStr Fecha de fin (yyyy-MM-dd)
     * 
     * @param precio Precio del curso (>= 0)
     * 
     * @return Curso creado y persistido con UUID como ID
     * 
     * @throws ValidationException si los datos no cumplen las reglas de negocio
     */
    public Curso crear(String nombre, String tipoStr, String fIniStr, String fFinStr, double precio) {
        // ========================================
        // 1. VALIDAR DATOS OBLIGATORIOS
        // ========================================
        // ! Nombre obligatorio
        Validator.requireNotBlank(nombre, "Nombre");

        // ! Precio no puede ser negativo
        Validator.requirePositive(precio, "Precio");

        // ========================================
        // 2. CONVERTIR TIPO STRING → ENUM
        // ========================================
        // * .toUpperCase() asegura que "online" funcione igual que "ONLINE"
        // ! Si tipoStr no es "ONLINE" ni "PRESENCIAL", valueOf() lanza
        // IllegalArgumentException
        CursoTipo tipo;
        try {
            tipo = CursoTipo.valueOf(tipoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException(
                    "Tipo '" + tipoStr + "' no válido. Usa ONLINE o PRESENCIAL.");
        }

        // ========================================
        // 3. PARSEAR Y VALIDAR FECHAS
        // ========================================
        // * DateUtils.parse() valida formato yyyy-MM-dd y lanza excepción si es
        // incorrecto
        LocalDate ini = DateUtils.parse(fIniStr);
        LocalDate fin = DateUtils.parse(fFinStr);

        // ! Regla temporal: el curso no puede terminar antes de empezar
        if (fin.isBefore(ini)) {
            throw new ValidationException(
                    "La fecha fin (" + fFinStr + ") no puede ser anterior "
                            + "a la fecha inicio (" + fIniStr + ").");
        }

        // TODO: Validar duración máxima opcional (ej: <= 365 días)
        // if (ChronoUnit.DAYS.between(ini, fin) > 365) {
        // throw new ValidationException("El curso no puede durar más de 365 días");
        // }

        // ========================================
        // 4. CREAR Y PERSISTIR EL CURSO
        // ========================================
        String id = UUID.randomUUID().toString(); // * UUID garantiza unicidad global
        Curso c = new Curso(id, nombre.trim(), tipo, ini, fin, precio);
        Curso guardado = repo.save(c);

        // * Feedback enriquecido
        System.out.println("  → Curso creado: " + guardado.getNombre()
                + " | Tipo: " + guardado.getTipo()
                + " | " + guardado.getFechaInicio() + " → " + guardado.getFechaFin()
                + " | Precio: " + guardado.getPrecio() + "€");

        return guardado;
    }

    /*
     * // ! BORRAR un curso por ID
     *
     * // * La eliminación es permanente.
     * // ! ADVERTENCIA: Si hay matrículas asociadas, quedarán huérfanas.
     *
     * // TODO: Verificar que no tenga matrículas ACTIVAS antes de borrar.
     *
     * @param id ID del curso a eliminar
     * 
     * @return true si se eliminó, false si el curso no existía
     */
    public boolean borrar(String id) {
        return repo.delete(id);
    }
}
