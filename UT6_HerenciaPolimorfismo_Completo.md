# 📘 UT6 - Herencia y Polimorfismo en Java (Guía Súper Completa)

> Material didáctico completo para 1º DAW  
> Basado en: `UT6_HerenciaPolimorfismo_Completo.java`

---

## 🎯 Objetivo de esta unidad

Al terminar esta guía, debes poder:

- ✅ Explicar qué es **herencia** y para qué sirve.
- ✅ Explicar qué es **polimorfismo** y aplicarlo en código real.
- ✅ Diferenciar **sobrescritura** (`@Override`) y **sobrecarga** (mismo método, distintos parámetros).
- ✅ Entender cuándo usar **clases abstractas** e **interfaces**.
- ✅ Crear jerarquías de clases bien diseñadas y con validaciones.
- ✅ Usar `instanceof` y casting de forma segura.

---

## 🧠 Mapa mental rápido

- **Clase base abstracta**: `VehiculoBase`
- **Clase intermedia abstracta**: `VehiculoTerrestre`
- **Clases hijas concretas**:
  - `CocheTurismo`
  - `MotoUrbana`
  - `PatineteElectrico`
  - `CamionReparto`
- **Interfaces**:
  - `Inspeccionable`
  - `Electrico`

👉 Este diseño combina lo más importante de POO en una sola práctica.

---

## 1) 🏗️ Herencia: reutilizar y especializar

### ¿Qué es?

La **herencia** permite que una clase hija reutilice atributos y métodos de una clase padre.

Ejemplo conceptual:

```java
class VehiculoBase { ... }          // Padre
class VehiculoTerrestre extends VehiculoBase { ... } // Hija intermedia
class CocheTurismo extends VehiculoTerrestre { ... } // Hija final
```

### Ventajas

- 🔁 Reutilización de código.
- 🧹 Menos duplicación.
- 🧩 Organización por niveles (general -> específico).
- 📈 Escalabilidad para proyectos grandes.

---

## 2) 🎭 Polimorfismo: una referencia, muchos comportamientos

### Idea clave

Una referencia del tipo padre puede apuntar a objetos de distintas clases hijas:

```java
VehiculoBase v1 = new CocheTurismo(...);
VehiculoBase v2 = new MotoUrbana(...);
VehiculoBase v3 = new CamionReparto(...);
```

Cuando llamas al mismo método, cada objeto responde con su versión:

```java
v1.tocarBocina(); // bocina de coche
v2.tocarBocina(); // bocina de moto
v3.tocarBocina(); // bocina de camion
```

### ¿Por qué es tan importante?

- Permite código más limpio y flexible.
- Evita `if` enormes por tipo de objeto.
- Facilita añadir nuevas clases sin romper lo anterior.

---

## 3) 📦 Clase abstracta: plantilla obligatoria

`VehiculoBase` es abstracta:

```java
abstract class VehiculoBase { ... }
```

Esto significa:

- ❌ No puedes hacer `new VehiculoBase(...)`.
- ✅ Sí puedes usarla como tipo de referencia.
- ✅ Puede tener métodos normales y abstractos.

Métodos abstractos del ejemplo:

```java
public abstract void tocarBocina();
public abstract double calcularCostoMantenimiento();
```

Toda clase hija concreta está obligada a implementarlos.

---

## 4) 🔧 Tipos de métodos que aparecen en la práctica

### 4.1 Método `void` (no devuelve valor)

```java
public void arrancar() { ... }
```

### 4.2 Método con retorno

```java
public boolean necesitaRevision() { ... }
public String generarEtiqueta() { ... }
public double calcularCostoMantenimiento() { ... }
```

### 4.3 Método `static` (de clase)

```java
public static int getVehiculosCreados() { ... }
```

Se invoca con el nombre de la clase:

```java
VehiculoBase.getVehiculosCreados();
```

### 4.4 Método `final` (bloqueado para override)

```java
public final void mostrarIdentidadBase() { ... }
```

Las hijas no pueden cambiarlo.

### 4.5 Método abstracto (sin implementación)

```java
public abstract void tocarBocina();
```

---

## 5) 🔁 Sobrescritura vs Sobrecarga

## ✅ Sobrescritura (`@Override`)

Mismo método, misma firma, comportamiento distinto en la hija.

```java
@Override
public void tocarBocina() {
    System.out.println("Moto: piii.");
}
```

## ✅ Sobrecarga (overload)

Mismo nombre, **distintos parámetros**.

```java
public void acelerar() { ... }
public void acelerar(int incremento) { ... }
```

---

## 6) 🔒 Encapsulación y validaciones

En la práctica los atributos son `private`, y se accede mediante getters/setters.

Ejemplos de validación:

- Año no puede ser menor que 1886.
- Marca no puede estar vacía.
- Batería se limita entre 0 y 100.
- Carga máxima del camión no puede ser negativa.

💡 Esto evita estados inválidos del objeto.

---

## 7) 🧩 Interfaces: contrato de comportamiento

### `Inspeccionable`

```java
interface Inspeccionable {
    boolean pasarInspeccion();
    default void imprimirChecklist() { ... }
}
```

- Obliga a implementar `pasarInspeccion()`.
- Reutiliza `imprimirChecklist()` con `default`.

### `Electrico`

```java
interface Electrico {
    void cargarBateria(int porcentaje);
    int getNivelBateria();
    default boolean bateriaBaja() { ... }
}
```

Aplicada en `PatineteElectrico`.

---

## 8) 🧪 `instanceof` y casting seguro

Se usa para ejecutar lógica específica según el tipo real:

```java
if (vehiculo instanceof Electrico) {
    Electrico e = (Electrico) vehiculo;
    e.cargarBateria(15);
}
```

Regla importante:

- ✅ Primero `instanceof`
- ✅ Después casting
- ❌ Nunca casteo directo sin comprobar

---

## 9) 🚚 Explicación por clases del proyecto

## `VehiculoBase` (abstracta)

- Datos comunes: marca, año, velocidad, id.
- Contador `static` de instancias.
- Métodos comunes: arrancar, detener, acelerar, etiqueta.
- Métodos abstractos obligatorios: bocina y coste de mantenimiento.

## `VehiculoTerrestre` (abstracta intermedia)

- Añade `ruedas`.
- Hereda de `VehiculoBase`.
- Sobrescribe `mostrarInformacion()` para ampliar datos.

## `CocheTurismo`

- Atributos: puertas y plazas.
- Implementa `Inspeccionable`.
- Tiene constructor sobrecargado.

## `MotoUrbana`

- Atributos: cilindrada y baúl.
- Implementa `Inspeccionable`.

## `PatineteElectrico`

- Atributos: autonomía y batería.
- Implementa `Electrico` + `Inspeccionable`.
- Sobrescribe `acelerar(int)` para gastar batería.

## `CamionReparto`

- Atributos: carga máxima y carga actual.
- Métodos de negocio: cargar y descargar.
- Incluye `TODO` para completar en clase.

---

## 10) ❗ Errores típicos de examen y cómo evitarlos

- ❌ Confundir sobrecarga y sobrescritura.
- ❌ Olvidar `@Override` cuando realmente sobrescribes.
- ❌ Intentar instanciar una clase abstracta.
- ❌ Hacer casting sin comprobar `instanceof`.
- ❌ No validar datos en setters/constructores.
- ❌ Repetir código en hijas en vez de reutilizar el padre.

---

## 11) 📝 Ejercicios guiados (nivel examen)

### Ejercicio 1 - Clase nueva

Crea `AutobusEscolar` que herede de `VehiculoTerrestre`:

- Atributos: `numeroAlumnos`, `tieneMonitor`.
- Sobrescribe `tocarBocina()` y `calcularCostoMantenimiento()`.
- Implementa `Inspeccionable`.

### Ejercicio 2 - Interface nueva

Crea interface `Rastreable`:

```java
String obtenerGPS();
```

Haz que `CamionReparto` la implemente.

### Ejercicio 3 - Polimorfismo real

- Crea un array `VehiculoBase[]` con 8 objetos mezclados.
- Recorre el array y llama a:
  - `arrancar()`
  - `acelerar()`
  - `tocarBocina()`
  - `mostrarInformacion()`

### Ejercicio 4 - Completar TODO

Implementa:

- `porcentajeCarga()`
- `estaLleno()`

---

## 12) 🧾 Preguntas cortas para estudiar

1. ¿Qué ventaja práctica tiene el polimorfismo?
2. ¿Cuándo usar clase abstracta y cuándo interfaz?
3. ¿Por qué `final` puede mejorar el diseño?
4. ¿Qué pasa si un setter no valida correctamente?
5. ¿Por qué es importante `instanceof` antes del casting?

---

## 13) 📌 Chuleta express para memorizar

- `extends` -> herencia de clase.
- `implements` -> contrato de interfaz.
- `@Override` -> sobrescritura.
- Sobrecarga -> mismo nombre + distinta firma.
- `abstract` -> clase o método incompleto.
- `final` -> no modificable / no sobrescribible.
- `static` -> pertenece a la clase, no al objeto.
- Polimorfismo -> referencia padre, objeto hijo.

---

## 14) ✅ Checklist final del alumno

Marca cada punto cuando lo domines:

- [ ] Sé crear una jerarquía de herencia limpia.
- [ ] Entiendo cómo funciona el polimorfismo en arrays/listas.
- [ ] Sé aplicar `@Override` correctamente.
- [ ] Sé crear métodos sobrecargados.
- [ ] Sé usar interfaces con métodos `default`.
- [ ] Sé validar datos en constructores y setters.
- [ ] Sé usar `instanceof` y casting seguro.
- [ ] Puedo diseñar una clase nueva sin romper el sistema.

---

## 🚀 Recomendación de uso en clase

1. Leer esta guía una vez completa.
2. Ejecutar `UT6_HerenciaPolimorfismo_Completo.java`.
3. Ir clase por clase explicando qué hereda y qué sobrescribe.
4. Hacer en directo los ejercicios 1 y 2.
5. Dejar ejercicios 3 y 4 como práctica evaluable.

Si quieres, te preparo también una versión de esta guía en formato **“resumen de 2 páginas para examen”**.
