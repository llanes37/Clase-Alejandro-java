/*
 * ==================================================================================
 * // ! CONTROLLER — AlumnoController (Capa de Negocio)
 * ==================================================================================
 *
 * // * RESPONSABILIDADES:
 *   - Validar entradas (nombre no vacío, email válido y único).
 *   - Normalizar datos (trim nombre, email en minúsculas).
 *   - Parsear la fecha de nacimiento opcional (formato yyyy-MM-dd).
 *   - Delegar la persistencia en AlumnoRepository.
 *
 * // ! REGLAS DE NEGOCIO IMPLEMENTADAS:
 *   1. Nombre obligatorio (no vacío ni solo espacios).
 *   2. Email con formato válido (@ y dominio).
 *   3. Email único: no pueden existir dos alumnos con el mismo email.
 *   4. Fecha de nacimiento opcional: null si no se proporciona.
 *
 * // ? POR QUÉ EL CONTROLLER VALIDA Y NO EL REPOSITORY:
 *   El repositorio solo sabe leer/escribir CSV. Las REGLAS DE NEGOCIO
 *   (email único, nombre obligatorio) pertenecen al controller.
 *   Así podemos cambiar el repository (CSV → BD) sin tocar las reglas.
 *
 * // * CONTRATO:
 *   listar()    → List<Alumno> (nunca null, puede estar vacía)
 *   crear(...)  → Alumno con UUID (lanza ValidationException si datos inválidos)
 *   borrar(id)  → true si existía, false si no
 *
 * // TODO (Alumno):
 *   - [ ] Añadir actualizarNombre(String id, String nuevoNombre).
 *   - [ ] Añadir búsqueda: listarPorNombreContiene(String texto).
 *   - [ ] Añadir validación de edad mínima (>= 16 años desde fecha nacimiento).
 * ==================================================================================
 */
package com.curso.proyectofinal.controller;

import com.curso.proyectofinal.exception.ValidationException;
import com.curso.proyectofinal.model.Alumno;
import com.curso.proyectofinal.repository.AlumnoRepository;
import com.curso.proyectofinal.util.DateUtils;
import com.curso.proyectofinal.util.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AlumnoController {

    // ========================================
    // DEPENDENCIAS
    // ========================================

    // * Repositorio de alumnos: responsable de leer/escribir el fichero CSV
    private final AlumnoRepository repo;

    /*
     * // * CONSTRUCTOR CON INYECCIÓN DE DEPENDENCIAS
     * // ? Recibimos el repo desde fuera (Application.java crea e inyecta la
     * instancia).
     * En Spring Boot esto se haría con @Autowired automáticamente.
     */
    public AlumnoController(AlumnoRepository repo) {
        this.repo = repo;
    }

    // ========================================
    // OPERACIONES CRUD + NEGOCIO
    // ========================================

    /*
     * // ! LISTAR todos los alumnos almacenados
     * // * Retorna lista vacía si no hay ninguno (nunca null).
     */
    public List<Alumno> listar() {
        return repo.findAll(); // * Delega directamente al repo (sin lógica de negocio)
    }

    /*
     * // ! CREAR un nuevo alumno
     *
     * // * FLUJO:
     * 1. Validar nombre (obligatorio).
     * 2. Validar formato de email.
     * 3. Verificar que el email no esté ya en uso.
     * 4. Parsear fecha de nacimiento (si se proporcionó).
     * 5. Normalizar datos (trim, lowercase).
     * 6. Crear el alumno con UUID e ID.
     * 7. Persistir y retornar.
     *
     * // ! IMPORTANTE:
     * - El email se guarda en minúsculas para comparaciones case-insensitive.
     * - La fecha de nacimiento es OPCIONAL: si el usuario deja el campo vacío,
     * se guarda como null (no se usa ninguna fecha por defecto).
     *
     * @param nombre Nombre del alumno (obligatorio, no vacío)
     * 
     * @param email Email del alumno (formato válido, único en el sistema)
     * 
     * @param fechaNacStr Fecha de nacimiento yyyy-MM-dd (opcional, vacío = null)
     * 
     * @return Alumno creado y persistido con UUID como ID
     * 
     * @throws ValidationException si nombre/email inválidos o email duplicado
     */
    public Alumno crear(String nombre, String email, String fechaNacStr) {
        // ========================================
        // 1. VALIDAR NOMBRE Y EMAIL
        // ========================================
        // ! Nombre obligatorio
        Validator.requireNotBlank(nombre, "Nombre");

        // * Validar formato básico de email
        Validator.requireEmail(email);

        // ========================================
        // 2. VERIFICAR UNICIDAD DE EMAIL
        // ========================================
        // ! Regla de negocio clave: email único por alumno
        if (repo.findByEmail(email).isPresent()) {
            throw new ValidationException(
                    "Ya existe un alumno con el email '" + email + "'. "
                            + "Cada alumno debe tener un email único.");
        }

        // ========================================
        // 3. PARSEAR FECHA OPCIONAL
        // ========================================
        // ? Si el usuario no facilita una fecha, la dejamos null (campo opcional).
        // * DateUtils.parse() lanza ValidationException si el formato es incorrecto.
        LocalDate fnac = (fechaNacStr == null || fechaNacStr.isBlank())
                ? null
                : DateUtils.parse(fechaNacStr);

        // ========================================
        // 4. CREAR Y PERSISTIR EL ALUMNO
        // ========================================
        String id = UUID.randomUUID().toString(); // * UUID garantiza unicidad global
        // * Normalizamos: trim para nombre, email en minúsculas
        Alumno a = new Alumno(
                id,
                nombre.trim(),
                email.trim().toLowerCase(),
                fnac);

        Alumno guardado = repo.save(a);

        // * Feedback enriquecido para la consola
        System.out.println("  → Alumno creado: " + guardado.getNombre()
                + " | Email: " + guardado.getEmail()
                + " | ID: " + guardado.getId());

        return guardado;
    }

    /*
     * // ! BORRAR un alumno por ID
     *
     * // * La eliminación es permanente (borrado del CSV).
     * // ! ADVERTENCIA: Si el alumno tiene matrículas activas, esas matrículas
     * quedarán huérfanas (con alumnoId que ya no existe).
     *
     * // TODO: Validar que no tenga matrículas ACTIVAS antes de borrar.
     *
     * @param id ID del alumno a eliminar
     * 
     * @return true si se eliminó, false si el alumno no existía
     */
    public boolean borrar(String id) {
        // ! Validar siempre antes de operar
        Validator.requireNotBlank(id, "Id");
        return repo.delete(id);
    }
}
