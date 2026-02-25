/*
 * ==========================================================================================
 * PLANTILLA DE EJERCICIO (PARA COMPLETAR POR EL ALUMNO)
 * TEMA: GESTION DE UNA BIBLIOTECA ESCOLAR
 * ==========================================================================================
 *
 * OBJETIVO GENERAL
 * ----------------
 * Practicar HERENCIA, POLIMORFISMO, ENCAPSULACION y VALIDACIONES.
 *
 * En este ejercicio NO hay solucion implementada. Solo tienes el enunciado y la estructura
 * de clases para que tu escribas toda la logica.
 *
 * ==========================================================================================
 * ENUNCIADO
 * ==========================================================================================
 * Una biblioteca escolar quiere gestionar distintos tipos de materiales.
 *
 * 1) Existe una clase base llamada MaterialBiblioteca con informacion comun:
 *    - codigo (String)
 *    - titulo (String)
 *    - anioPublicacion (int)
 *    - prestado (boolean)
 *
 * 2) Existen 3 tipos de materiales (clases hijas):
 *    - Libro: autor (String), numeroPaginas (int)
 *    - Revista: numeroEdicion (int), mesPublicacion (String)
 *    - DVD: duracionMinutos (int), clasificacionEdad (int)
 *
 * 3) Requisitos funcionales:
 *    - Crear un metodo mostrarInformacion() en la clase base y SOBRESCRIBIRLO en cada hija.
 *    - Crear un metodo prestar() y devolver() para cambiar el estado prestado/no prestado.
 *    - Crear un metodo calcularUsoSemanal() en la clase base (puede devolver 0) y
 *      sobrescribirlo en cada hija con una formula propia (la defines tu).
 *
 * 4) Validaciones minimas obligatorias:
 *    - anioPublicacion >= 1450
 *    - numeroPaginas > 0
 *    - numeroEdicion > 0
 *    - duracionMinutos > 0
 *    - clasificacionEdad >= 0
 *
 * 5) Parte principal (main):
 *    - Crear varios objetos (Libro, Revista, DVD).
 *    - Guardarlos en un arreglo de tipo MaterialBiblioteca[] (polimorfismo).
 *    - Recorrer el arreglo y mostrar informacion de cada objeto.
 *    - Simular prestamos y devoluciones.
 *
 * 6) Entrada por teclado (Scanner):
 *    - Pedir al usuario los datos de UN DVD (titulo, anio, duracion, clasificacion).
 *    - Crear ese objeto y agregarlo al sistema.
 *
 * ==========================================================================================
 * LO QUE DEBES HACER TU
 * ==========================================================================================
 * - Completar constructores.
 * - Completar getters/setters.
 * - Implementar validaciones.
 * - Sobrescribir metodos.
 * - Completar logica del main.
 *
 * RECOMENDACION:
 * 1) Empieza por la clase base.
 * 2) Continua con cada clase hija.
 * 3) Termina en main probando todo.
 * ==========================================================================================
 */

public class UT6_Plantilla_Biblioteca {
    public static void main(String[] args) {
        // TODO: Crear Scanner.
        // TODO: Crear objetos iniciales (Libro, Revista, DVD).
        // TODO: Guardarlos en un arreglo MaterialBiblioteca[].
        // TODO: Recorrer arreglo y llamar mostrarInformacion().
        // TODO: Simular prestar() y devolver().
        // TODO: Pedir por teclado datos de un DVD y crearlo.
        // TODO: Cerrar Scanner.
    }
}

/*
 * CLASE BASE
 * ----------
 * Atributos comunes para todos los materiales de biblioteca.
 *
 * Debes implementar:
 * - Constructor
 * - Getters/setters con validacion
 * - mostrarInformacion()
 * - prestar()
 * - devolver()
 * - calcularUsoSemanal()
 */
class MaterialBiblioteca {
    // TODO: atributos

    // TODO: constructor

    // TODO: getters y setters

    // TODO: metodos comunes
}

/*
 * CLASE HIJA: Libro
 * -----------------
 * Hereda de MaterialBiblioteca.
 *
 * Atributos propios:
 * - autor
 * - numeroPaginas
 *
 * Debes implementar:
 * - Constructor
 * - Getters/setters con validacion
 * - @Override mostrarInformacion()
 * - @Override calcularUsoSemanal()
 */
class Libro extends MaterialBiblioteca {
    // TODO: atributos

    // TODO: constructor

    // TODO: getters y setters

    // TODO: sobrescrituras
}

/*
 * CLASE HIJA: Revista
 * -------------------
 * Hereda de MaterialBiblioteca.
 *
 * Atributos propios:
 * - numeroEdicion
 * - mesPublicacion
 *
 * Debes implementar:
 * - Constructor
 * - Getters/setters con validacion
 * - @Override mostrarInformacion()
 * - @Override calcularUsoSemanal()
 */
class Revista extends MaterialBiblioteca {
    // TODO: atributos

    // TODO: constructor

    // TODO: getters y setters

    // TODO: sobrescrituras
}

/*
 * CLASE HIJA: DVD
 * ---------------
 * Hereda de MaterialBiblioteca.
 *
 * Atributos propios:
 * - duracionMinutos
 * - clasificacionEdad
 *
 * Debes implementar:
 * - Constructor
 * - Getters/setters con validacion
 * - @Override mostrarInformacion()
 * - @Override calcularUsoSemanal()
 */
class DVD extends MaterialBiblioteca {
    // TODO: atributos

    // TODO: constructor

    // TODO: getters y setters

    // TODO: sobrescrituras
}
