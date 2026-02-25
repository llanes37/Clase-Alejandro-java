# UT6 - Enunciado Completo (Herencia y Polimorfismo)

Material para trabajar en clase y rellenar codigo por fases.
Archivo plantilla asociado: `UT6_HerenciaPolimorfismo_Rellenable.java`

## 1. Enunciado General

Debes construir un mini sistema de gestion de vehiculos aplicando Programacion Orientada a Objetos.
El proyecto debe demostrar:

- herencia por niveles (padre -> hija intermedia -> hijas concretas)
- polimorfismo con arreglos del tipo padre
- metodos abstractos y sobrescritos
- sobrecarga de metodos
- encapsulacion con getters/setters y validaciones
- uso de interfaces para comportamiento extra
- uso de `instanceof` y casting seguro

## 2. Objetivos de Aprendizaje

Al terminar, el alumno debe ser capaz de:

- diseñar una jerarquia de clases coherente
- explicar la diferencia entre sobrecarga y sobrescritura
- aplicar `abstract`, `final`, `static`, `extends`, `implements`
- recorrer una flota polimorfica y ejecutar comportamiento dinamico

## 3. Requisitos Tecnicos

- Lenguaje: Java
- Una unica clase publica por archivo
- Compilar sin errores
- Mensajes por consola claros y consistentes

## 4. Clases e Interfaces Requeridas

Debes completar estas piezas (ya estan en la plantilla):

- `VehiculoBase` (abstracta)
- `VehiculoTerrestre` (abstracta intermedia)
- `CocheTurismo`
- `MotoUrbana`
- `PatineteElectrico`
- `CamionReparto`
- `Inspeccionable` (interface)
- `Electrico` (interface)

## 5. Metodos Obligatorios

En la plantilla aparecen con `TODO`.

## En `VehiculoBase`

- `arrancar()`
- `detener()`
- `acelerar()`
- `acelerar(int incremento)` (sobrecarga)
- `necesitaRevision()`
- `generarEtiqueta()`
- `mostrarInformacion()`
- `tocarBocina()` (abstract)
- `calcularCostoMantenimiento()` (abstract)
- `obtenerMasNuevo(...)` (static)
- validaciones en setters

## En cada clase hija

- sobrescribir `tocarBocina()`
- sobrescribir `calcularCostoMantenimiento()`
- sobrescribir `mostrarInformacion()`

## Adicional por tipo

- `PatineteElectrico` debe implementar `Electrico`
- las clases indicadas deben implementar `Inspeccionable`
- `CamionReparto` debe tener `cargarMercancia` y `descargarMercancia`

## 6. Fases de Trabajo Recomendadas

1. Completa primero la base (`VehiculoBase`) y comprueba compilacion.
2. Completa `VehiculoTerrestre`.
3. Completa `CocheTurismo` y prueba en `main`.
4. Completa `MotoUrbana`.
5. Completa `PatineteElectrico` con bateria.
6. Completa `CamionReparto` con carga.
7. Activa demo polimorfica en `main`.
8. Ejecuta pruebas manuales y corrige.

## 7. Pruebas Minimas que Debes Ver

Al ejecutar, deberias poder ver:

- arranque y parada de cada vehiculo
- bocina distinta por tipo
- informacion distinta por tipo
- coste de mantenimiento distinto por tipo
- contador total de vehiculos creados
- uso de `instanceof` con interfaces

## 8. Criterios de Evaluacion

- 25% Diseno OO (herencia + encapsulacion)
- 25% Polimorfismo real y sobrescritura correcta
- 20% Validaciones y robustez
- 15% Claridad del codigo y comentarios
- 15% Pruebas en `main` y salida correcta

## 9. Errores Tipicos a Evitar

- confundir sobrecarga y sobrescritura
- olvidar `@Override`
- cast sin `instanceof`
- setters sin validacion
- duplicar codigo en vez de reutilizar herencia

## 10. Extension (Opcional)

Crear `AutobusEscolar`:

- hereda de `VehiculoTerrestre`
- implementa `Inspeccionable`
- atributo `numeroAlumnos`
- sobrescribe metodos clave

## 11. Entrega

Debes entregar:

- archivo `.java` compilando
- comentarios explicando decisiones
- captura de salida por consola o log de ejecucion

## 12. Checklist del Alumno

- [ ] Compile sin errores
- [ ] Entiendo cada `TODO` resuelto
- [ ] Use `extends` e `implements` correctamente
- [ ] Aplique polimorfismo en arreglo de padre
- [ ] Aplique `instanceof` y casting seguro

