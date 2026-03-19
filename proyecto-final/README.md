# 📚 Proyecto Final — Gestor de Cursos (Java 17, MVC, CSV, Consola)

> **Proyecto didáctico** que gestiona Alumnos, Cursos y Matrículas con patrón MVC,  
> persistencia en ficheros CSV y ejecución por consola (sin frameworks).  
> Incluye comentarios Better Comments, cuaderno de 12 ejercicios con prompts para IA,  
> y scripts de compilación/ejecución para Windows.  
> **Autor:** Joaquín Rodríguez Llanes | Uso educativo exclusivo.

---

## 🚀 Arranque rápido

```bash
# 1. Compilar (genera clases en bin/)
build.bat             # doble clic o desde terminal

# 2. Ejecutar
run.bat               # abre el menú principal en consola

# 3. Empaquetar (opcional)
package.bat           # genera proyecto-final.jar
java -jar proyecto-final.jar
```

> 💡 **Requisito:** JDK 17+ instalado y en PATH (`java -version` / `javac -version`).  
> Alternativa PowerShell: `.\build.ps1`

### Ejecutar desde VS Code
1. Instala la extensión **"Extension Pack for Java"**.
2. Abre la carpeta `proyecto-final` en VS Code.
3. Abre `Application.java` → clic en **"Run"** (CodeLens encima de `main`).

> ⚠️ El directorio de trabajo debe ser `proyecto-final/` para que `resources/data/` se resuelva bien.

---

## 🏗️ Arquitectura por capas (MVC)

```
Menú de consola (usuario)
     │
     ▼
┌─────────────────────────────────────────────────────────┐
│  📁 view/  (Capa de PRESENTACIÓN)                        │
│  ConsoleView.java → title(), line(), prompt(), pause()   │
│  Application.java → menús y flujo de navegación          │
└────────────────────────┬────────────────────────────────┘
                         │ delega lógica
                         ▼
┌─────────────────────────────────────────────────────────┐
│  📁 controller/  (Capa de NEGOCIO)  ← AQUÍ van reglas   │
│  AlumnoController → email único, nombre obligatorio      │
│  CursoController  → fechas coherentes, precio >= 0       │
│  MatriculaController → ventana temporal, anulación       │
└────────────────────────┬────────────────────────────────┘
                         │ acceso a datos
                         ▼
┌─────────────────────────────────────────────────────────┐
│  📁 repository/  (Capa de DATOS)                         │
│  CRUD genérico: save, findById, findAll, update, delete  │
│  Delega lectura/escritura CSV a persistence/             │
└────────────────────────┬────────────────────────────────┘
                         │ ficheros CSV
                         ▼
┌─────────────────────────────────────────────────────────┐
│  📁 model/  (Entidades puras)                            │
│  Alumno, Curso, Matricula, CursoTipo, EstadoMatricula    │
└────────────────────────┬────────────────────────────────┘
                         │ serialización
                         ▼
              resources/data/*.csv
```

**🔑 Regla de oro:** si no sabes dónde va una regla, la respuesta es **`controller`**.

---

## 📏 Reglas de negocio implementadas

| # | Regla | Dónde vive |
|---|---|---|
| 1 | Nombre de alumno obligatorio (no vacío) | `AlumnoController` |
| 2 | Email del alumno con formato válido (regex) | `AlumnoController` + `Validator` |
| 3 | Email del alumno único (case-insensitive) | `AlumnoController` |
| 4 | Tipo de curso: solo ONLINE o PRESENCIAL (enum) | `CursoController` |
| 5 | Precio del curso >= 0 | `CursoController` + `Validator` |
| 6 | `fechaFin >= fechaInicio` en el curso | `CursoController` |
| 7 | Fecha de matrícula dentro del rango del curso | `MatriculaController` |
| 8 | Alumno y curso deben existir para matricular | `MatriculaController` |
| 9 | Anular solo cambia estado (ACTIVA → ANULADA) | `MatriculaController` |

> ⚠️ **TODOs incluidos en el código:** borrado protegido, anti-duplicado ACTIVA, finalización → ver `EJERCICIOS.md`.

---

## 🗃️ Persistencia y formato CSV

Los datos se guardan en `resources/data/` con separador `;`:

| Fichero | Columnas |
|---|---|
| `alumnos.csv` | `id;nombre;email;fechaNacimiento(yyyy-MM-dd \| vacío)` |
| `cursos.csv` | `id;nombre;tipo;fechaInicio;fechaFin;precio` |
| `matriculas.csv` | `id;alumnoId;cursoId;fechaMatricula;estado` |

- **Carga:** al iniciar cada repositorio.
- **Guardado:** al crear, actualizar o borrar.
- Si borras un CSV, se regenera vacío al siguiente arranque.

---

## 📂 Estructura de carpetas

```
proyecto-final/
├── src/com/curso/proyectofinal/
│   ├── Application.java         ← punto de entrada + menús
│   ├── controller/              ← reglas de negocio
│   │   ├── AlumnoController.java
│   │   ├── CursoController.java
│   │   └── MatriculaController.java
│   ├── exception/               ← excepciones de dominio
│   │   └── ValidationException.java
│   ├── model/                   ← entidades + enums
│   │   ├── Alumno.java
│   │   ├── Curso.java
│   │   ├── CursoTipo.java
│   │   ├── EstadoMatricula.java
│   │   └── Matricula.java
│   ├── persistence/             ← utilidades CSV
│   │   ├── CsvUtils.java
│   │   └── FileStorage.java
│   ├── repository/              ← acceso a datos
│   │   ├── Repository.java      (interfaz genérica)
│   │   ├── AlumnoRepository.java
│   │   ├── CursoRepository.java
│   │   └── MatriculaRepository.java
│   ├── util/                    ← utilidades de validación
│   │   ├── DateUtils.java
│   │   └── Validator.java
│   └── view/                    ← interfaz de consola
│       └── ConsoleView.java
├── resources/data/              ← CSV generados al ejecutar
├── bin/                         ← clases compiladas
├── EJERCICIOS.md                ← 12 ejercicios con prompts IA
├── README.md                    ← este archivo
├── build.bat                    ← compilar
├── build.ps1                    ← compilar (PowerShell)
├── run.bat                      ← ejecutar
└── package.bat                  ← generar JAR
```

---

## 🎨 Better Comments (colores en VS Code)

Instala **`aaron-bond.better-comments`** en VS Code:

| Prefijo | Color | Significado |
|---|---|---|
| `// *` | 🟢 Verde | Explicación de flujo, responsabilidad, contrato |
| `// ?` | 🔵 Azul | Justificación técnica, decisión de diseño |
| `// !` | 🔴 Rojo | Regla crítica, advertencia, error frecuente |
| `// TODO` | 🟠 Naranja | Mejora pendiente, ejercicio para el alumno |

---

## 📖 Cómo estudiar este proyecto en clase

**Ruta de lectura recomendada (de menor a mayor complejidad):**

| Orden | Archivo | Qué aprendes |
|---|---|---|
| 1️⃣ | `model/Alumno.java` | Clase POJO simple, equals/hashCode, Optional |
| 2️⃣ | `model/CursoTipo.java` | Enums: por qué son mejores que Strings |
| 3️⃣ | `view/ConsoleView.java` | Separación de presentación, reutilización |
| 4️⃣ | `controller/AlumnoController.java` | Validación, email único, UUID, inyección manual |
| 5️⃣ | `controller/CursoController.java` | Enum parsing, regla temporal fechas |
| 6️⃣ | `controller/MatriculaController.java` | Controller complejo: 3 repos, validación cruzada |
| 7️⃣ | `repository/AlumnoRepository.java` | Persistencia CSV, findByEmail(), CRUD |
| 8️⃣ | `persistence/CsvUtils.java` | Lectura/escritura de ficheros, separador |
| 9️⃣ | `Application.java` | Menús, flujo completo, wiring manual |
| 🔟 | `EJERCICIOS.md` | Practicar todo lo aprendido |

### 🧠 Preguntas didácticas clave
- ¿Qué regla debe vivir en el Controller y cuál en el Repository?
- ¿Qué se rompe si movemos la validación de email único a Application.java?
- ¿Qué ventaja tiene UUID sobre un contador incremental?
- ¿Qué gana el equipo si separa View de Controller?

---

## ❓ Problemas frecuentes

| Problema | Solución |
|---|---|
| `java` o `javac` no encontrados | Instala JDK 17+ y añade `bin/` a la variable PATH |
| Error de formato de fecha | Usa formato `yyyy-MM-dd` (ej: `2025-10-26`) |
| CSV corrupto (la app se cae) | Borra el fichero CSV y reinicia (se regenera vacío) |
| La ventana .bat se cierra al terminar | Ejecuta desde un terminal abierto (no doble clic) |
| Desde VS Code no encuentra los CSV | Asegúrate de que el working dir es `proyecto-final/` |
