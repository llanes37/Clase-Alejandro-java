/*
 * ==================================================================================
 * // ! APPLICATION — Gestor de Cursos (Proyecto Final)
 * ==================================================================================
 *
 * // * ARQUITECTURA MVC APLICADA:
 *   - Model:      Alumno, Curso, Matricula, EstadoMatricula (paquete model/)
 *   - Repository: AlumnoRepository, CursoRepository, MatriculaRepository (CSV en data/)
 *   - Controller: AlumnoController, CursoController, MatriculaController (negocio)
 *   - View:       ConsoleView + Application.java (entrada/salida consola)
 *
 * // * ROL DE ESTA CLASE:
 *   Solo navegación de menús y lectura de entradas del usuario.
 *   Las reglas de negocio viven en los Controllers.
 *   Los datos se almacenan en CSV gestionados por los Repositories.
 *
 * // ? POR QUÉ while(true) en los menús:
 *   Simplifica el bucle hasta que el usuario elige "0) Volver".
 *   El return dentro del case "0" sale del submenú limpiamente.
 *
 * // ! FLUJO PRINCIPAL:
 *   main() → run() → menuAlumnos() / menuCursos() / menuMatriculas()
 *
 * // TODO (Alumno):
 *   - [ ] Añadir confirmación (S/N) antes de borrar alumnos, cursos o matrículas.
 *   - [ ] Añadir opción para actualizar el nombre de un Alumno.
 *   - [ ] Añadir listados filtrados (ej.: cursos ONLINE, matrículas por alumno).
 * ==================================================================================
 */
package com.curso.proyectofinal;

import com.curso.proyectofinal.controller.AlumnoController;
import com.curso.proyectofinal.controller.CursoController;
import com.curso.proyectofinal.controller.MatriculaController;
import com.curso.proyectofinal.model.Alumno;
import com.curso.proyectofinal.model.Curso;
import com.curso.proyectofinal.model.Matricula;
import com.curso.proyectofinal.model.EstadoMatricula;
import com.curso.proyectofinal.repository.AlumnoRepository;
import com.curso.proyectofinal.repository.CursoRepository;
import com.curso.proyectofinal.repository.MatriculaRepository;
import com.curso.proyectofinal.view.ConsoleView;

import java.util.List;

/**
 * Aplicación principal: crea repos/controladores y muestra menú de consola.
 */
public class Application {
    private final ConsoleView view = new ConsoleView();
    private final AlumnoRepository alumnoRepo = new AlumnoRepository();
    private final CursoRepository cursoRepo = new CursoRepository();
    private final MatriculaRepository matriculaRepo = new MatriculaRepository();

    private final AlumnoController alumnoCtl = new AlumnoController(alumnoRepo);
    private final CursoController cursoCtl = new CursoController(cursoRepo);
    private final MatriculaController matriculaCtl = new MatriculaController(matriculaRepo, alumnoRepo, cursoRepo);

    public static void main(String[] args) {
        new Application().run();
    }

    // * Contrato: Ejecuta el bucle de navegación del menú principal.
    // ? Por qué while(true): simplifica bucle hasta elegir "0) Salir".
    public void run() {
        while (true) {
            view.title("Gestor de Cursos — Menu Principal");
            view.line("1) Alumnos");
            view.line("2) Cursos");
            view.line("3) Matrículas");
            view.line("0) Salir");
            // ? Leemos la opción del usuario como texto. No usamos int para evitar
            // NumberFormatException.
            // * Ejemplo: "1" para entrar en Alumnos.
            String op = view.prompt("Elige opción");
            try {
                switch (op) {
                    case "1":
                        menuAlumnos();
                        break;
                    case "2":
                        menuCursos();
                        break;
                    case "3":
                        menuMatriculas();
                        break;
                    case "0":
                        return;
                    default:
                        view.line("Opción inválida");
                }
            } catch (Exception e) {
                view.line("[ERROR] " + e.getMessage());
            }
        }
    }

    // * Submenú: Alumnos — listar/crear/borrar
    private void menuAlumnos() {
        while (true) {
            view.title("Alumnos");
            view.line("1) Listar");
            view.line("2) Crear");
            view.line("3) Borrar");
            view.line("0) Volver");
            String op = view.prompt("Opción");
            if (op.equals("0"))
                return;
            try {
                switch (op) {
                    case "1":
                        listarAlumnos();
                        break;
                    case "2":
                        crearAlumno();
                        break;
                    case "3":
                        borrarAlumno();
                        break;
                    default:
                        view.line("Opción inválida");
                }
            } catch (Exception e) {
                view.line("[ERROR] " + e.getMessage());
            }
            view.pause();
        }
    }

    private void listarAlumnos() {
        List<Alumno> list = alumnoCtl.listar();
        view.line("-- Alumnos --");
        // * Recorremos la lista y usamos toString() de Alumno para mostrar información.
        // ! Si quieres más control en la salida, modifica Alumno.toString() o usa
        // ConsoleView.table().
        for (Alumno a : list)
            view.line(a.toString());
    }

    // * Entradas: nombre, email, fecha opcional (yyyy-MM-dd)
    // ! Validación importante: email único (en AlumnoController)
    private void crearAlumno() {
        String nombre = view.prompt("Nombre");
        String email = view.prompt("Email");
        String fnac = view.prompt("Fecha nacimiento (yyyy-MM-dd, opcional)");
        // * Delegamos la validación y persistencia al controlador.
        // ? AlumnoController.crear() lanzará ValidationException si algo falla.
        Alumno a = alumnoCtl.crear(nombre, email, fnac);
        view.line("Creado: " + a.getId());
    }

    private void borrarAlumno() {
        String id = view.prompt("Id alumno a borrar");
        boolean ok = alumnoCtl.borrar(id);
        view.line(ok ? "Borrado" : "No existe");
    }

    // * Submenú: Cursos — listar/crear/borrar
    private void menuCursos() {
        while (true) {
            view.title("Cursos");
            view.line("1) Listar");
            view.line("2) Crear");
            view.line("3) Borrar");
            view.line("0) Volver");
            String op = view.prompt("Opción");
            if (op.equals("0"))
                return;
            try {
                switch (op) {
                    case "1":
                        listarCursos();
                        break;
                    case "2":
                        crearCurso();
                        break;
                    case "3":
                        borrarCurso();
                        break;
                    default:
                        view.line("Opción inválida");
                }
            } catch (Exception e) {
                view.line("[ERROR] " + e.getMessage());
            }
            view.pause();
        }
    }

    private void listarCursos() {
        List<Curso> list = cursoCtl.listar();
        view.line("-- Cursos --");
        // * Mostrar cada curso. Para tablas, usar ConsoleView.table() si la
        // implementas.
        for (Curso c : list)
            view.line(c.toString());
    }

    // * Entradas: nombre, tipo (ONLINE/PRESENCIAL), fechas, precio
    // ? El parseo de precio utiliza Double.parseDouble (usa coma o punto gestionado
    // en ConsoleView para doubles con default)
    private void crearCurso() {
        String nombre = view.prompt("Nombre");
        String tipo = view.prompt("Tipo (ONLINE/PRESENCIAL)");
        String fIni = view.prompt("Fecha inicio (yyyy-MM-dd)");
        String fFin = view.prompt("Fecha fin (yyyy-MM-dd)");
        // ? Atención: parseDouble puede lanzar NumberFormatException si la entrada no
        // es válida.
        // * Podrías usar view.promptDouble() para manejar decimales con coma/punto y
        // valores por defecto.
        double precio = Double.parseDouble(view.prompt("Precio"));
        Curso c = cursoCtl.crear(nombre, tipo, fIni, fFin, precio);
        view.line("Creado: " + c.getId());
    }

    private void borrarCurso() {
        String id = view.prompt("Id curso a borrar");
        boolean ok = cursoCtl.borrar(id);
        view.line(ok ? "Borrado" : "No existe");
    }

    // * Submenú: Matrículas — listar/crear/anular
    private void menuMatriculas() {
        while (true) {
            view.title("Matrículas");
            view.line("1) Listar");
            view.line("2) Matricular alumno en curso");
            view.line("3) Anular matrícula");
            view.line("0) Volver");
            String op = view.prompt("Opción");
            if (op.equals("0"))
                return;
            try {
                switch (op) {
                    case "1":
                        listarMatriculas();
                        break;
                    case "2":
                        crearMatricula();
                        break;
                    case "3":
                        anularMatricula();
                        break;
                    default:
                        view.line("Opción inválida");
                }
            } catch (Exception e) {
                view.line("[ERROR] " + e.getMessage());
            }
            view.pause();
        }
    }

    /*
     * // * LISTAR MATRÍCULAS con nombres de alumno y curso
     * // ? Resolvemos los nombres desde sus repositorios para que el listado sea
     * legible.
     * Sin esto, solo veríamos UUIDs, lo que no aporta información al usuario.
     * // ! Si el alumno o curso fueron borrados, se muestra "(eliminado)" como
     * aviso.
     */
    private void listarMatriculas() {
        List<Matricula> list = matriculaCtl.listar();
        view.line("-- Matrículas registradas --");
        if (list.isEmpty()) {
            view.line("  (No hay matrículas. Usa la opción 2 para matricular un alumno.)");
            return;
        }
        for (Matricula m : list) {
            // * Resolver nombres desde los repos para mostrar info legible
            // ? Si fueron borrados, mostramos texto descriptivo en lugar del ID
            String nombreAlumno = alumnoRepo.findById(m.getAlumnoId())
                    .map(Alumno::getNombre)
                    .orElse("(alumno eliminado)");
            String nombreCurso = cursoRepo.findById(m.getCursoId())
                    .map(Curso::getNombre)
                    .orElse("(curso eliminado)");

            // * Formato legible: Estado | Fecha | Alumno | Curso
            view.line(String.format(
                    "[%s] %s | Alumno: %-20s | Curso: %-25s | ID: %s",
                    m.getEstado(),
                    m.getFechaMatricula(),
                    nombreAlumno,
                    nombreCurso,
                    m.getId().substring(0, 8) + "..."));
        }
        long activas = list.stream().filter(m -> m.getEstado() == EstadoMatricula.ACTIVA).count();
        view.line("  Total: " + list.size() + " matrícula(s) | Activas: " + activas);
    }

    /*
     * // * CREAR MATRÍCULA
     * // ! El alumno y el curso deben existir previamente.
     * // ? Si la fecha se deja vacía, el controller usará la fecha de hoy.
     */
    private void crearMatricula() {
        view.line("  Necesitas los IDs de Alumno y Curso. Consúltalos en sus respectivos menús.");
        String alumnoId = view.prompt("ID del Alumno");
        String cursoId = view.prompt("ID del Curso");
        String fecha = view.prompt("Fecha de matrícula (yyyy-MM-dd, ENTER = hoy)");
        Matricula m = matriculaCtl.matricular(alumnoId, cursoId, fecha);
        view.line("✔ Matrícula creada con ID: " + m.getId());
    }

    /*
     * // * ANULAR MATRÍCULA
     * // ! La matrícula pasa a estado ANULADA (no se borra del registro).
     */
    private void anularMatricula() {
        view.line("  El ID de matrícula aparece en el listado (opción 1).");
        String id = view.prompt("ID de la matrícula a anular");
        try {
            boolean ok = matriculaCtl.anular(id);
            view.line(ok ? "✔ Matrícula anulada correctamente." : "⚠ Matrícula no encontrada.");
        } catch (Exception e) {
            view.line("[ERROR] " + e.getMessage());
        }
    }
}
