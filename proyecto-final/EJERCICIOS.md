# 🏋️ EJERCICIOS PRÁCTICOS — Proyecto Final (Java MVC + CSV + Consola)

> **Autor:** Joaquín Rodríguez Llanes  
> **Nivel:** Java Intermedio (sin frameworks)  
> **Tiempo total estimado:** 8–12 horas  
> **Prerrequisitos:** Haber completado `proyecto-final-basico`. Conocimientos de POO, colecciones, enums, ficheros y LocalDate.

> 💡 **Cómo usar estos ejercicios:**  
> Cada ejercicio incluye un **análisis técnico** que explica QUÉ hay que hacer, POR QUÉ y EN QUÉ ARCHIVOS.  
> Al final de cada uno hay una **📋 Cajita de Prompt** lista para copiar y enviar a una IA  
> (Gemini, ChatGPT, etc.) si quieres que lo implemente automáticamente.  
> **Recomendación:** intenta hacerlo tú primero. Si te atascas, usa el prompt.

---

## 📋 Índice de Ejercicios

| # | Ejercicio | Nivel | Tiempo | Capas que toca |
|---|-----------|-------|--------|----------------|
| 1 | Explorar el proyecto y responder preguntas | ⭐ Básico | 20 min | Todas (lectura) |
| 2 | Comparar con `proyecto-final-basico` | ⭐ Básico | 20 min | Todas (lectura) |
| 3 | Confirmación S/N antes de borrar | ⭐⭐ Intermedio | 30 min | View → Application |
| 4 | Actualizar nombre de un Alumno | ⭐⭐ Intermedio | 30 min | Controller → Repository → Application |
| 5 | Filtrar cursos por tipo (ONLINE/PRESENCIAL) | ⭐⭐ Intermedio | 30 min | Controller → Application |
| 6 | Validar duración máxima del curso (365 días) | ⭐ Básico | 20 min | Controller |
| 7 | Evitar matrículas duplicadas ACTIVA (alumno+curso) | ⭐⭐ Intermedio | 40 min | Controller |
| 8 | Borrado protegido (alumno/curso con matrículas ACTIVAS) | ⭐⭐ Intermedio | 40 min | Controller |
| 9 | Finalizar matrícula (ACTIVA → FINALIZADA) | ⭐⭐ Intermedio | 40 min | Controller → Application |
| 10 | Listar matrículas filtradas (por alumno o por curso) | ⭐⭐ Intermedio | 30 min | Controller → Application |
| 11 | CSV tolerante a fallos (parsing defensivo) | ⭐⭐⭐ Avanzado | 45 min | Repository / CsvUtils |
| 12 | Exportar informe completo a fichero .txt | ⭐⭐⭐ Avanzado | 45 min | Application |

---

## ⭐ EJERCICIO 1 — Explorar el proyecto (básico)

### 🎯 Objetivo
Comprender la relación entre capas: **Application (Vista) → Controller (Negocio) → Repository (CSV) → Model (Entidades)**.

### ✅ Pasos
1. Compila el proyecto con `build.bat` y ejecútalo con `run.bat`.
2. Navega por los menús: **Alumnos**, **Cursos**, **Matrículas**.
3. Crea 2 alumnos, 1 curso y 1 matrícula.
4. Intenta crear un alumno con el mismo email → observa el mensaje de error.
5. Abre `resources/data/` y examina los ficheros CSV generados.

### 🧠 Preguntas para responder
1. ¿En qué archivo se valida que el email del alumno sea único? ¿En el Model, Controller o Application?
2. ¿Qué clase se encarga de leer/escribir los ficheros CSV?
3. ¿Qué pasa si borras un fichero CSV y relanzas la app?
4. ¿Qué es `UUID.randomUUID().toString()` y por qué se usa como ID?
5. ¿Dónde se valida que `fechaFin >= fechaInicio` en los cursos?
6. ¿Qué patrón de diseño sigue este proyecto? (pista: 3 letras)

---

## ⭐ EJERCICIO 2 — Comparar con el proyecto básico (básico)

### 🎯 Objetivo
Entender las **diferencias arquitectónicas** entre `proyecto-final-basico` y este proyecto.

### ✅ Pasos
1. Abre los dos proyectos en paralelo en VS Code.
2. Compara la estructura de carpetas (paquetes).
3. Rellena esta tabla comparativa:

| Aspecto | proyecto-final-basico | proyecto-final |
|---|---|---|
| Entidades | | |
| Nº de clases Java | | |
| Validaciones | | |
| Enums usados | | |
| Persistencia | | |
| Manejo de errores | | |
| Complejidad del menú | | |

### 🧠 Preguntas clave
- ¿Por qué el proyecto avanzado tiene una clase `ConsoleView` separada?
- ¿Qué ventaja tiene usar `CursoTipo` como enum en vez de un String libre?
- ¿Qué añade `ValidationException` sobre un simple `System.out.println("error")`?

---

## ⭐⭐ EJERCICIO 3 — Confirmación S/N antes de borrar (intermedio)

### 🎯 Objetivo
Añadir confirmación al usuario antes de borrar alumnos, cursos o matrículas.

### 📖 Análisis técnico

| Capa | Archivo | Qué hacer |
|------|---------|-----------|
| **View** | `ConsoleView.java` | Crear método `confirmar(String mensaje)` que devuelve `boolean` |
| **Application** | `Application.java` | Llamar a `confirmar()` antes de ejecutar `borrar()` en cada menú |

### 🧠 Decisiones de diseño
- El método `confirmar()` va en `ConsoleView` (es lógica de presentación, no de negocio).
- Se reutiliza el mismo método en los 3 menús (DRY: Don't Repeat Yourself).
- Acepta "S", "s", "SI", "si" como afirmativo; cualquier otra cosa cancela.
- NO modifica ningún Controller → la capa de negocio no cambia.

### 📋 Prompt para la IA
> ```
> En mi proyecto Java MVC de consola "proyecto-final", necesito añadir confirmación
> S/N antes de borrar alumnos, cursos o matrículas (anular).
>
> Arquitectura: Application.java (vista) → *Controller.java (negocio) → *Repository.java (CSV)
>
> Archivos a modificar:
> - src/com/curso/proyectofinal/view/ConsoleView.java → crear método confirmar(String mensaje)
>   que pregunta, lee la respuesta y devuelve true si es "S"/"s"/"SI"/"si"
> - src/com/curso/proyectofinal/Application.java → en borrarAlumno(), borrarCurso() y
>   anularMatricula(), llamar a view.confirmar() antes de ejecutar la operación
>
> Mantén el mismo estilo de comentarios Better Comments del proyecto (// *, // ?, // !, // TODO).
> No modifiques los Controllers (la confirmación es lógica de presentación, no de negocio).
> ```

---

## ⭐⭐ EJERCICIO 4 — Actualizar nombre de Alumno (intermedio)

### 🎯 Objetivo
Implementar una **operación de actualización parcial** en el flujo MVC completo.

### 📖 Análisis técnico

| Capa | Archivo | Qué hacer |
|------|---------|-----------|
| **Controller** | `AlumnoController.java` | Crear método `actualizarNombre(String id, String nuevoNombre)` |
| **Application** | `Application.java` | Añadir opción "4) Actualizar nombre" al submenú de alumnos |

### 🧠 Decisiones de diseño
- El Controller busca el alumno con `repo.findById(id)`, usa `.map()` para modificar y `.orElse(false)` si no existe.
- Se hace `trim()` al nombre nuevo para limpiar espacios.
- Se usa `repo.update(alumno)` que sobreescribe la fila en el CSV.
- Regla: el nombre nuevo NO puede estar vacío → usar `Validator.requireNotBlank()`.

### ⚠️ Errores comunes
- Olvidar validar con `Validator.requireNotBlank()` → permite nombres vacíos.
- No hacer `trim()` → quedan espacios.
- Usar `repo.save()` en vez de `repo.update()` → duplicaría el registro.

### 📋 Prompt para la IA
> ```
> En mi proyecto Java MVC "proyecto-final", necesito añadir la funcionalidad de
> actualizar el nombre de un alumno existente.
>
> Archivos a modificar:
> - src/com/curso/proyectofinal/controller/AlumnoController.java
>   → crear método actualizarNombre(String id, String nuevoNombre) que:
>     1) Valida id y nuevoNombre con Validator.requireNotBlank()
>     2) Busca el alumno con repo.findById(id)
>     3) Si existe: actualiza nombre (con trim), llama repo.update() y retorna true
>     4) Si no existe: retorna false
> - src/com/curso/proyectofinal/Application.java
>   → añadir opción "4) Actualizar nombre" al menú de alumnos
>
> Usa el mismo estilo de comentarios Better Comments del proyecto.
> ```

---

## ⭐⭐ EJERCICIO 5 — Filtrar cursos por tipo (intermedio)

### 🎯 Objetivo
Añadir al menú de cursos la opción de **mostrar solo ONLINE o solo PRESENCIAL**.

### 📖 Análisis técnico

| Capa | Archivo | Qué hacer |
|------|---------|-----------|
| **Controller** | `CursoController.java` | Crear `listarPorTipo(CursoTipo tipo)` con Streams |
| **Application** | `Application.java` | Añadir opciones "4) Ver solo ONLINE" y "5) Ver solo PRESENCIAL" |

### 🧠 Decisiones de diseño
- Se usa `repo.findAll().stream().filter(c -> c.getTipo() == tipo).toList()`.
- La comparación de enums usa `==` (no `.equals()`) porque los enums son singletons.
- El filtro va en el Controller, no en el Repository (para no acoplar la lógica de filtrado al CSV).

### 📋 Prompt para la IA
> ```
> En mi proyecto Java MVC "proyecto-final", necesito filtrar cursos por tipo
> (ONLINE o PRESENCIAL) en el menú de cursos.
>
> Archivos a modificar:
> - src/com/curso/proyectofinal/controller/CursoController.java
>   → crear listarPorTipo(CursoTipo tipo) usando Streams + filter
> - src/com/curso/proyectofinal/Application.java
>   → añadir opciones 4) y 5) al menú de cursos para filtrar por tipo
>   → reutilizar el método listarCursos() o crear mostrarCursos(List<Curso>, String titulo)
>
> El enum CursoTipo ya existe con valores ONLINE y PRESENCIAL.
> Usa el mismo estilo de comentarios Better Comments del proyecto.
> ```

---

## ⭐ EJERCICIO 6 — Duración máxima del curso 365 días (básico)

### 🎯 Objetivo
Impedir la creación de cursos que duren más de 365 días.

### 📖 Análisis técnico

| Capa | Archivo | Qué hacer |
|------|---------|-----------|
| **Controller** | `CursoController.java` | Añadir validación con `ChronoUnit.DAYS.between()` dentro de `crear()` |

### 🧠 Decisiones de diseño
- La validación va DESPUÉS de comprobar que `fin >= inicio` (ambas reglas temporales juntas).
- Se lanza `ValidationException` con un mensaje claro que incluye los días calculados.
- Solo toca 1 archivo (el Controller) → la regla de negocio vive donde debe.

### 📋 Prompt para la IA
> ```
> En mi proyecto Java MVC "proyecto-final", necesito validar que un curso no pueda
> durar más de 365 días en CursoController.crear().
>
> Archivo a modificar:
> - src/com/curso/proyectofinal/controller/CursoController.java
>   → después de la validación fin >= inicio, añadir:
>     long dias = ChronoUnit.DAYS.between(ini, fin);
>     if (dias > 365) throw ValidationException con mensaje claro incluyendo los días
>   → importar java.time.temporal.ChronoUnit
>
> Usa el mismo estilo de comentarios Better Comments del proyecto.
> ```

---

## ⭐⭐ EJERCICIO 7 — Evitar matrículas duplicadas ACTIVA (intermedio)

### 🎯 Objetivo
Impedir que un alumno tenga **dos matrículas ACTIVAS** en el mismo curso.

### 📖 Análisis técnico

| Capa | Archivo | Qué hacer |
|------|---------|-----------|
| **Controller** | `MatriculaController.java` | Añadir comprobación en `matricular()` antes de crear |

### 🧠 Decisiones de diseño
- Se usa `repo.findAll().stream().anyMatch(...)` para buscar coincidencia.
- La condición es: `mismo alumnoId AND mismo cursoId AND estado == ACTIVA`.
- Una matrícula ANULADA NO bloquea → el alumno puede volver a matricularse.
- Se lanza `ValidationException` con mensaje claro (incluye nombres del alumno y curso).
- Solo toca el Controller → es una regla de negocio.

### ⚠️ Errores comunes
- Comparar con `==` en vez de `.equals()` para String (los IDs son UUID String).
- Olvidar filtrar por estado ACTIVA → bloquea incluso matrículas ANULADAS.
- Poner la comprobación DESPUÉS de crear → el duplicado se guarda antes de detectarlo.

### 📋 Prompt para la IA
> ```
> En mi proyecto Java MVC "proyecto-final", necesito impedir matrículas duplicadas
> ACTIVAS para el mismo alumno+curso en MatriculaController.matricular().
>
> Archivo a modificar:
> - src/com/curso/proyectofinal/controller/MatriculaController.java
>   → ANTES de crear la matrícula, comprobar con Streams si ya existe una matrícula
>     con el mismo alumnoId, cursoId y estado == EstadoMatricula.ACTIVA
>   → Si existe: lanzar ValidationException con mensaje claro que incluya el nombre
>     del alumno y del curso
>   → Si NO existe: continuar con la creación normal
>
> Los IDs son String (UUID), comparar con .equals(). Los nombres ya están disponibles
> en las variables locales 'a' (Alumno) y 'c' (Curso).
> Usa el mismo estilo de comentarios Better Comments del proyecto.
> ```

---

## ⭐⭐ EJERCICIO 8 — Borrado protegido de alumno/curso (intermedio)

### 🎯 Objetivo
**Impedir borrar** un alumno o curso que tenga matrículas ACTIVAS asociadas.

### 📖 Análisis técnico

| Capa | Archivo | Qué hacer |
|------|---------|-----------|
| **Controller** | `AlumnoController.java` | Modificar `borrar()` para comprobar matrículas ACTIVAS |
| **Controller** | `CursoController.java` | Modificar `borrar()` para comprobar matrículas ACTIVAS |

### 🧠 Decisiones de diseño
- Los Controllers necesitan acceso al `MatriculaRepository` para comprobar las dependencias.
- Se inyecta `MatriculaRepository` por constructor (igual que los demás repos).
- Se usa `matRepo.findAll().stream().anyMatch(...)` para buscar matrículas ACTIVAS.
- Si hay matrículas ACTIVAS → `ValidationException` con mensaje claro.
- Si NO hay → se borra normalmente.
- Esto ya estaba marcado como `// TODO` en el código.

### 📋 Prompt para la IA
> ```
> En mi proyecto Java MVC "proyecto-final", necesito implementar borrado protegido:
> no se puede borrar un alumno o curso si tiene matrículas ACTIVAS.
>
> Archivos a modificar:
> - src/com/curso/proyectofinal/controller/AlumnoController.java
>   → añadir MatriculaRepository como dependencia inyectada por constructor
>   → en borrar(): comprobar si hay matrículas con alumnoId y estado ACTIVA
>   → si las hay: lanzar ValidationException
> - src/com/curso/proyectofinal/controller/CursoController.java
>   → mismo patrón: inyectar MatriculaRepository y comprobar antes de borrar
> - src/com/curso/proyectofinal/Application.java
>   → actualizar la creación de AlumnoController y CursoController para
>     pasar MatriculaRepository adicional al constructor
>
> Usa Streams + anyMatch para la comprobación. Estilo Better Comments.
> ```

---

## ⭐⭐ EJERCICIO 9 — Finalizar matrícula (intermedio)

### 🎯 Objetivo
Implementar la transición de estado **ACTIVA → FINALIZADA** con sus reglas.

### 📖 Análisis técnico

| Capa | Archivo | Qué hacer |
|------|---------|-----------|
| **Controller** | `MatriculaController.java` | Crear método `finalizar(String matriculaId)` |
| **Application** | `Application.java` | Añadir opción "4) Finalizar matrícula" al submenú |

### 🧠 Decisiones de diseño
- **Solo se puede finalizar** una matrícula ACTIVA (no una ya ANULADA o FINALIZADA).
- **Solo se puede finalizar** si la fecha actual es posterior o igual a `fechaFin` del curso.
- Usa `cursoRepo.findById()` para obtener la `fechaFin` del curso asociado.
- Si ya estaba ANULADA → `ValidationException("Solo se pueden finalizar matrículas ACTIVAS")`.
- Si el curso no ha terminado → `ValidationException("El curso aún no ha terminado (fin: ...)")`.

### 📋 Prompt para la IA
> ```
> En mi proyecto Java MVC "proyecto-final", necesito implementar la finalización
> de matrículas (ACTIVA → FINALIZADA) en MatriculaController.
>
> Archivos a modificar:
> - src/com/curso/proyectofinal/controller/MatriculaController.java
>   → crear finalizar(String matriculaId) que:
>     1) Busca la matrícula (orElseThrow si no existe)
>     2) Verifica que estado sea ACTIVA
>     3) Busca el curso asociado para obtener fechaFin
>     4) Verifica que LocalDate.now() >= curso.getFechaFin()
>     5) Cambia estado a FINALIZADA y persiste
> - src/com/curso/proyectofinal/Application.java
>   → añadir opción "4) Finalizar matrícula" al submenú de matrículas
>
> Lanza ValidationException con mensajes claros en cada caso de error.
> Usa estilo Better Comments.
> ```

---

## ⭐⭐ EJERCICIO 10 — Listar matrículas filtradas (intermedio)

### 🎯 Objetivo
Poder ver **las matrículas de un alumno concreto** o **las de un curso concreto**.

### 📖 Análisis técnico

| Capa | Archivo | Qué hacer |
|------|---------|-----------|
| **Controller** | `MatriculaController.java` | Crear `listarPorAlumno()` y `listarPorCurso()` |
| **Application** | `Application.java` | Añadir opciones al submenú de matrículas |

### 🧠 Decisiones de diseño
- Se usa `repo.findAll().stream().filter(...).toList()` en ambos métodos.
- El filtraje va en el Controller (no en el Repository) para mantener separación de capas.
- En Application, se pide el ID y se muestra la lista filtrada reutilizando el formato existente.

### 📋 Prompt para la IA
> ```
> En mi proyecto Java MVC "proyecto-final", necesito listar matrículas filtradas
> por alumno o por curso en MatriculaController.
>
> Archivos a modificar:
> - src/com/curso/proyectofinal/controller/MatriculaController.java
>   → crear listarPorAlumno(String alumnoId) con Streams + filter por alumnoId
>   → crear listarPorCurso(String cursoId) con Streams + filter por cursoId
> - src/com/curso/proyectofinal/Application.java
>   → añadir opciones 5) y 6) al submenú de matrículas para filtrar
>   → reutilizar el formato de listado existente (con nombres de alumno y curso)
>
> Comparar IDs con .equals() (son String UUID). Estilo Better Comments.
> ```

---

## ⭐⭐⭐ EJERCICIO 11 — CSV tolerante a fallos (avanzado)

### 🎯 Objetivo
Que la app **no se caiga** si un fichero CSV tiene líneas corruptas (columnas de menos, tipos inválidos, etc.).

### 📖 Análisis técnico

| Capa | Archivo | Qué hacer |
|------|---------|-----------|
| **Persistence** | `CsvUtils.java` o Repositorios | Añadir try-catch por línea con log de error |

### 🧠 Decisiones de diseño
- **Estrategia "best effort":** cargar las líneas válidas, saltar las inválidas y reportar cuántas se saltaron.
- Cada repositorio envuelve el parseo de cada línea en un `try-catch`.
- Las líneas problemáticas se reportan con `System.err.println("[WARN] Línea X ignorada: ...")`.
- Al final del proceso se informa: "Cargados 8 alumnos. 2 líneas ignoradas por errores."
- **No se detiene la carga** por una sola línea mala.

### 📋 Prompt para la IA
> ```
> En mi proyecto Java MVC "proyecto-final", necesito que la carga de ficheros CSV
> sea tolerante a fallos: si una línea tiene formato incorrecto, se salta esa línea
> con un aviso pero se siguen cargando las demás.
>
> Archivos a modificar (uno o varios de estos):
> - src/com/curso/proyectofinal/repository/AlumnoRepository.java
> - src/com/curso/proyectofinal/repository/CursoRepository.java
> - src/com/curso/proyectofinal/repository/MatriculaRepository.java
> - src/com/curso/proyectofinal/persistence/CsvUtils.java (si aplica)
>
> Requisitos:
> - Envolver el parseo de cada línea en try-catch
> - Si falla: System.err.println("[WARN] Línea X ignorada: " + detalles del error)
> - Si OK: añadir la entidad a la lista
> - Al final: imprimir resumen "Cargados N registros. M líneas ignoradas."
> - NO detener la carga por una línea mala
>
> Usa estilo Better Comments.
> ```

---

## ⭐⭐⭐ EJERCICIO 12 — Exportar informe completo a fichero .txt (avanzado)

### 🎯 Objetivo
Crear una opción en el menú principal que genere un **fichero de texto** con un resumen completo.

### 📖 Análisis técnico

| Capa | Archivo | Qué hacer |
|------|---------|-----------|
| **Application** | `Application.java` | Crear método `exportarInforme()` y añadir opción al menú principal |

### 🧠 Decisiones de diseño
- Se usa `PrintWriter` con `FileWriter` para escribir el fichero.
- El fichero se llama `informe_cursos.txt` y se crea en la raíz del proyecto.
- Contenido: fecha de generación, lista de alumnos, lista de cursos, estadísticas de matrículas.
- Se usa `try-with-resources` para cerrar automáticamente el fichero (aunque falle).
- Se informa al usuario con mensaje en consola cuando se genera correctamente.

### 📋 Prompt para la IA
> ```
> En mi proyecto Java MVC "proyecto-final", necesito una opción en el menú principal
> que genere un informe completo en un fichero "informe_cursos.txt".
>
> Archivo a modificar:
> - src/com/curso/proyectofinal/Application.java
>   → crear método privado exportarInforme() que:
>     1) Usa PrintWriter + FileWriter con try-with-resources
>     2) Escribe: fecha de generación, lista de alumnos (nombre + email),
>        lista de cursos (nombre + tipo + precio), estadísticas
>        (total matrículas, activas, anuladas, finalizadas)
>     3) Informa al usuario en consola con view.line("✔ Informe exportado")
>   → añadir opción "4) Exportar informe" en el menú principal (run())
>
> Usa estilo Better Comments. Captura IOException para que la app no se caiga.
> ```

---

## 📚 Tabla resumen: Conceptos clave del proyecto

| Concepto | Dónde verlo en el código |
|----------|--------------------------|
| **Patrón MVC manual** | Application (Vista) → Controllers (Negocio) → Repositories (CSV) |
| **Enum (tipo seguro)** | `CursoTipo` (ONLINE/PRESENCIAL), `EstadoMatricula` (ACTIVA/ANULADA/FINALIZADA) |
| **UUID como ID** | `UUID.randomUUID().toString()` en todos los Controllers |
| **Optional** | `findById().map()` / `.orElseThrow()` en Controllers |
| **Streams (filter/anyMatch)** | Filtrado de cursos por tipo, detección de duplicados |
| **Validación centralizada** | Clase `Validator` + `DateUtils` + `ValidationException` |
| **Persistencia CSV** | `CsvUtils.java` + `FileStorage.java` → lee/escribe ficheros planos |
| **Inyección de dependencias manual** | Constructores de Controllers reciben Repositories |
| **LocalDate** | Fechas de nacimiento, inicio/fin de curso, matrícula |
| **Regex para email** | Validación de formato en `Validator.requireEmail()` |

---

## 🎓 Metodología docente (integrada de GUIA_Codex_Ensenanza)

### 🔁 Flujo repetible para cada ejercicio

Usa este ciclo para abordar cada mejora de forma profesional:

1. 🔎 **Diagnóstico:** ¿qué falta y por qué importa?
2. 📜 **Contrato:** comportamiento exacto + mensajes de error.
3. ⚠️ **Casos borde:** mínimo 3 por ejercicio.
4. 🧩 **Diseño por capas:** qué archivos tocar y por qué.
5. 🛠️ **Implementación:** paso a paso, 1 cambio → 1 prueba.
6. ✅ **Validación:** probar manualmente en la consola.
7. 🧑‍🏫 **Cierre:** concepto aprendido + error típico.

### 📌 Checklist del profesor
- [ ] ¿El contrato está definido antes de escribir código?
- [ ] ¿Los casos borde están enumerados?
- [ ] ¿La regla de negocio vive en el Controller (no en Application)?
- [ ] ¿Se puede probar en 1 minuto desde la consola?

### 📄 Prompt para generar un acta de cierre de sesión
> ```
> Genera un acta breve de la sesión de clase:
> 1) Qué problema resolvimos y por qué importaba.
> 2) Decisiones de diseño y trade-offs.
> 3) Cambios por archivos.
> 4) Cómo se probó.
> 5) Tareas para casa (básico + extensión).
> ```

### 🏁 Rúbrica rápida de evaluación
| Criterio | Peso |
|---|---|
| **Correctitud:** cumple el contrato y los casos borde | 40% |
| **Diseño por capas:** responsabilidades claras, sin atajos | 25% |
| **Robustez:** validaciones y errores controlados | 20% |
| **Claridad:** nombres, mensajes, consistencia | 15% |

---

## 🚀 ¿Quieres ir más allá? Ideas avanzadas

| Idea | Dificultad | Qué aprenderías |
|------|-----------|-----------------|
| Búsqueda de alumnos por nombre parcial | ⭐⭐ | Streams, `String.contains()`, case-insensitive |
| Actualizar precio de un curso | ⭐⭐ | CRUD completo (update parcial) |
| Validar edad mínima 16 años | ⭐⭐ | `Period.between()`, `LocalDate` |
| Estadísticas: nº matrículas por curso | ⭐⭐ | `Collectors.groupingBy()`, `Collectors.counting()` |
| Persistir en JSON en vez de CSV | ⭐⭐⭐ | Gson/Jackson, serialización, polimorfismo de persistencia |
| Tests unitarios de Controllers | ⭐⭐⭐ | JUnit 5, mocks manuales o Mockito |
| Migrar a Spring Boot | ⭐⭐⭐⭐ | Comparar MVC manual vs framework → evolución natural |