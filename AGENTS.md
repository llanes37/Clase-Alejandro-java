# AGENTS.md

## Propósito del repositorio
Este repositorio es de **enseñanza**. Por defecto, el asistente debe priorizar claridad didáctica por encima de velocidad de entrega.

## Contexto general (mantener genérico)
- Tratar el repo como material académico práctico: explicación + ejemplo + verificación.
- Mantener lenguaje claro para alumno, con pasos progresivos y sin asumir conocimientos avanzados.
- Priorizar soluciones legibles y fáciles de mantener antes que optimizaciones prematuras.
- Responder siempre en español claro, con frases cortas y enfoque de aula (nivel DAW).

## Prioridad de trabajo
1. **Didáctico > rápido**.
2. Explicar decisiones, fundamentos y alternativas cuando aporte aprendizaje.
3. No sacrificar comprensión por brevedad.

## Prohibiciones de formato
- Prohibido recortar respuestas con texto como: `...`, `omitido`, `etc.` para saltar contenido clave.
- No dejar secciones incompletas cuando se pida una entrega completa.

## Entrega estándar obligatoria
Toda respuesta técnica debe seguir este orden:
1. **Objetivo**
2. **Plan** (entre 6 y 10 pasos)
3. **Código o diff por archivos**
4. **Cómo probar**
5. **Checklist final**

Guía adicional:
- En **Cómo probar**, incluir entradas de ejemplo y salida esperada cuando aplique.
- En **Checklist final**, marcar explícitamente lo completado y lo pendiente.
- En cada bloque de código o método nuevo, explicar al menos: **qué hace**, **por qué se implementa así** y **un caso límite**.

## Better Comments obligatorio
Usar estos tags **exactos** en el código comentado:
- `// !IMPORTANT:`
- `// *INFO:`
- `// ?QUESTION:`
- `// TODO:`

Reglas de uso:
- Mínimo **1 comentario cada 6 a 10 líneas relevantes** en cada zona modificada.
- Cada comentario debe explicar: **qué hace**, **por qué se hace**, y **qué error típico evita**.
- Evitar comentarios triviales que solo repitan el código.
- Mantener consistencia: no mezclar otros prefijos si ya se está usando esta convención.
- Evitar saturación: comentar lo importante para aprender, no cada línea obvia.

## Plantilla de comentarios (estilo UT4_Funciones.java)
Este repositorio adopta como referencia visual y didáctica el estilo de `UT4_Funciones.java`.

Estructura recomendada en archivos docentes:
1. Bloque inicial de contexto/teoría de la unidad (multilínea, claro y ordenado).
2. Bloque de teoría global con conceptos clave antes del `main`.
3. Antes de cada método importante, un bloque corto de “micro-teoría”.
4. Cierre con bloque de tareas para el alumno (progresivas).

Reglas de aplicación:
- Mantener el tono “bonito y estructurado”, pero sin perder claridad técnica.
- Priorizar comentarios que enseñen intención, no solo sintaxis.
- Si se usan separadores visuales, que sean consistentes en todo el archivo.
- En ese estilo, los tags obligatorios siguen siendo los de Better Comments definidos arriba.

Micro-plantilla sugerida:
- `// *INFO:` contexto rápido del bloque.
- `// !IMPORTANT:` decisión clave y error típico.
- `// ?QUESTION:` pregunta guiada para que el alumno piense.
- `// TODO:` mini reto o ampliación controlada.

## Trabajo con PDFs
Si hay PDF(s) como fuente de requisitos:
- No inventar requisitos.
- Extraer checklist con formato `✅` / `⬜`.
- Si falta información, indicar literalmente: **"No definido / Requiere decisión"**.
- Si es imprescindible preguntar, máximo **2 preguntas**.

## Modo ejercicios
Si la tarea corresponde a un ejercicio:
- Generar o actualizar `README.md` incluyendo:
1. Enunciado
2. Modelado
3. Explicación de métodos
4. Edge cases
5. Cómo ejecutar
6. 3 ejercicios extra

Además:
- Incluir una sección breve de **Errores típicos** del alumno y cómo evitarlos.
- Si hay versión "rellenable" y versión "completa", mantener coherencia entre ambas.
- Incluir siempre al menos un ejemplo de **entrada/salida esperada** por ejercicio.

## Coherencia por carpetas
Si existen carpetas temáticas como `/ejercicios`, `/java`, `/web`, `/proyectos`, crear `AGENTS.md` específicos más estrictos para su contexto, manteniendo coherencia con este archivo raíz.
