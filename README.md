# Practica Final POO con Clase Intermedia y Arrays

## Enunciado
Desarrolla un programa en Java que modele una empresa usando Programacion Orientada a Objetos.

El ejercicio debe incluir:
- Una clase `Empresa`.
- Una clase intermedia `Departamento`.
- Una clase `Empleado`.
- Arrays de objetos para guardar departamentos y empleados.
- Un menu en consola para probar las operaciones principales.

Operaciones minimas del programa:
1. Mostrar un resumen de la empresa.
2. Listar departamentos y empleados.
3. Anadir un empleado a un departamento.
4. Buscar un empleado por nombre.
5. Calcular el gasto salarial total.
6. Mostrar el departamento con mas empleados.

## Modelado
- `Empresa`
  Guarda el nombre, el CIF y un array de `Departamento`.
- `Departamento`
  Es la clase intermedia. Guarda su nombre y un array de `Empleado`.
- `Empleado`
  Guarda los datos basicos de cada trabajador: id, nombre, edad, puesto y salario.

Relacion entre clases:
- Una `Empresa` tiene varios `Departamento`.
- Un `Departamento` tiene varios `Empleado`.
- Un `Empleado` pertenece a un `Departamento`.

## Explicacion de metodos
- `crearEmpresaDemo()`
  Crea datos de ejemplo para poder probar el programa sin introducir todo a mano.
- `mostrarResumenEmpresa(Empresa empresa)`
  Enseña datos globales como total de empleados y gasto salarial.
- `mostrarDepartamentosYEmpleados(Empresa empresa)`
  Recorre los arrays de objetos y lista el contenido.
- `contratarEmpleado(Empresa empresa)`
  Pide datos por teclado, crea un objeto `Empleado` y lo guarda en el departamento elegido.
- `buscarEmpleado(Empresa empresa)`
  Realiza una busqueda secuencial por nombre.
- `calcularGastoSalarialTotal()`
  Suma los salarios de todos los empleados de todos los departamentos.
- `obtenerDepartamentoConMasEmpleados()`
  Recorre los departamentos y devuelve el que tiene mas empleados.

## Edge cases
- Intentar anadir un empleado a un departamento lleno.
- Introducir texto cuando el programa espera un numero.
- Escribir un nombre vacio.
- Buscar un empleado que no existe.
- Calcular datos cuando un departamento no tiene empleados.

## Como ejecutar
1. Compilar:
```bash
javac UT5_Practica_Final_Empresa.java
```

2. Ejecutar:
```bash
java UT5_Practica_Final_Empresa
```

3. Flujo recomendado de prueba:
- Opcion `1` para ver el resumen inicial.
- Opcion `2` para ver los departamentos cargados.
- Opcion `3` para contratar un empleado nuevo.
- Opcion `4` para buscarlo por nombre.
- Opcion `5` para comprobar que el gasto salarial ha cambiado.

## Entrada/salida esperada
Entrada de ejemplo:
- Opcion: `3`
- Departamento: `1`
- Id: `10`
- Nombre: `Carlos`
- Edad: `22`
- Puesto: `Programador Junior`
- Salario: `1500`

Salida esperada:
- `Empleado contratado correctamente en Desarrollo.`

Si despues eliges la opcion `4` y buscas `Carlos`, deberias ver una ficha completa con sus datos.

## Errores tipicos del alumno y como evitarlos
- Recorrer todo el `length` del array en lugar de solo las posiciones usadas.
  Solucion: guarda un contador como `totalEmpleados`.
- Comparar `String` con `==`.
  Solucion: usa `equals(...)` o `equalsIgnoreCase(...)`.
- Anadir elementos sin comprobar si el array esta lleno.
  Solucion: valida antes de insertar.
- Meter toda la logica en `main`.
  Solucion: reparte el trabajo en metodos y clases.
- No distinguir entre clase principal y clase intermedia.
  Solucion: piensa quien contiene a quien en el problema real.

## 3 ejercicios extra
1. Crea una opcion para eliminar un empleado por id.
2. Anade el calculo del salario medio por departamento.
3. Sustituye los arrays por `ArrayList` y compara ambas soluciones.
