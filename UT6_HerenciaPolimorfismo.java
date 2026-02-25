import java.util.Scanner;

/*
 * ******************************************************************************************
 *                         TEORIA Y CONCEPTOS: HERENCIA Y POLIMORFISMO EN JAVA
 * ------------------------------------------------------------------------------------------
 * En esta practica aprenderas a:
 *
 * - Comprender la herencia en programacion orientada a objetos.
 * - Crear una clase base y clases hijas.
 * - Sobrescribir metodos (@Override) para personalizar comportamientos.
 * - Aplicar polimorfismo usando referencias del tipo de la clase padre.
 * - Reforzar buenas practicas con encapsulacion y validaciones.
 *
 * Objetivo de clase:
 * Construir un mini sistema de vehiculos donde cada tipo se comporta diferente.
 ******************************************************************************************
 */

/*
 * TEORIA GLOBAL: HERENCIA Y POLIMORFISMO
 * --------------------------------------
 *
 * 1) Herencia:
 *    Permite crear una clase nueva (hija) a partir de otra existente (padre).
 *    La clase hija reutiliza atributos/metodos del padre y puede agregar o cambiar comportamiento.
 *
 * 2) Polimorfismo:
 *    Permite tratar objetos de distintas clases hijas como si fueran del tipo padre.
 *    Esto simplifica el codigo y lo hace mas flexible.
 *
 * 3) Sobrescritura de metodos:
 *    Una clase hija puede redefinir un metodo del padre con su propia logica.
 *
 * Ejemplo corto:
 * Vehiculo v1 = new Coche("Toyota", 2020, 5);
 * Vehiculo v2 = new Moto("Yamaha", 2022, false);
 * v1.mostrarInformacion(); // Ejecuta version de Coche
 * v2.mostrarInformacion(); // Ejecuta version de Moto
 */

// Clase principal para ejecutar la practica
public class UT6_HerenciaPolimorfismo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Creamos objetos de clases hijas
        Vehiculo coche1 = new Coche("Toyota", 2020, 5);
        Vehiculo moto1 = new Moto("Yamaha", 2022, false);

        // Pedimos datos del camion por consola.
        System.out.print("Introduce la marca del camion: ");
        String marcaCamion = scanner.nextLine();

        System.out.print("Introduce el anio del camion: ");
        int anioCamion = Integer.parseInt(scanner.nextLine());

        System.out.print("Introduce la carga maxima del camion (toneladas): ");
        double cargaMaximaCamion = Double.parseDouble(scanner.nextLine());

        camion camionUsuario = new camion(marcaCamion, anioCamion, cargaMaximaCamion);
        // Polimorfismo: todos se manejan como Vehiculo
        Vehiculo[] vehiculos = { coche1, moto1, camionUsuario };

        // Recorremos y mostramos informacion (cada uno usa su version del metodo)
        for (Vehiculo vehiculo : vehiculos) {
            vehiculo.mostrarInformacion();
            vehiculo.arrancar();
            System.out.println("-----------------------------------");
        }
        // Recorremos solo camiones y usamos sus metodos.
        camion[] camiones = { camionUsuario };
        for (camion c : camiones) {
            c.mostrarInformacion();
            c.tocarBocina();
        }

        // Cambio de estado en un objeto especifico
        Moto motoReal = (Moto) moto1;
        motoReal.setTieneMaletero(true);
        motoReal.mostrarInformacion();

        // TAREA RAPIDA:
        // 1. Crea una clase hija "Camion" con atributo "cargaMaxima".
        // 2. Sobrescribe mostrarInformacion() para incluir la carga.
        // 3. Agrega objetos Camion al arreglo y prueba el polimorfismo.

        scanner.close();
    }
}

// Clase padre
class Vehiculo {
    private String marca;
    private int anio;

    // Constructor
    public Vehiculo(String marca, int anio) {
        this.marca = marca;
        setAnio(anio);
    }

    // Metodo comun para todos los vehiculos
    public void arrancar() {
        System.out.println("El vehiculo de marca " + marca + " ha arrancado.");
    }

    // Metodo que puede ser sobrescrito por las clases hijas
    public void mostrarInformacion() {
        System.out.println("Marca: " + marca + " | Anio: " + anio);
    }

    // Getters y setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        if (anio >= 1886) {
            this.anio = anio;
        } else {
            System.out.println("Error: el anio no es valido para un vehiculo.");
            this.anio = 1886;
        }
    }
}

// Clase hija Coche
class Coche extends Vehiculo {
    private int numeroPuertas;

    public Coche(String marca, int anio, int numeroPuertas) {
        super(marca, anio);
        setNumeroPuertas(numeroPuertas);
    }

    public int getNumeroPuertas() {
        return numeroPuertas;
    }

    public void setNumeroPuertas(int numeroPuertas) {
        if (numeroPuertas > 0) {
            this.numeroPuertas = numeroPuertas;
        } else {
            System.out.println("Error: un coche debe tener al menos 1 puerta.");
            this.numeroPuertas = 1;
        }
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(
            "Tipo: Coche | Marca: " + getMarca() +
            " | Anio: " + getAnio() +
            " | Puertas: " + numeroPuertas
        );
    }
}

// Clase hija Moto
class Moto extends Vehiculo {
    private boolean tieneMaletero;

    public Moto(String marca, int anio, boolean tieneMaletero) {
        super(marca, anio);
        this.tieneMaletero = tieneMaletero;
    }

    public boolean isTieneMaletero() {
        return tieneMaletero;
    }

    public void setTieneMaletero(boolean tieneMaletero) {
        this.tieneMaletero = tieneMaletero;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(
            "Tipo: Moto | Marca: " + getMarca() +
            " | Anio: " + getAnio() +
            " | Maletero: " + (tieneMaletero ? "Si" : "No")
        );
    }
}
class camion extends Vehiculo {
    private double cargaMaxima;

    public camion(String marca, int anio, double cargaMaxima) {
        super(marca, anio);
        // Reutilizamos el setter para centralizar la validacion.
        setCargaMaxima(cargaMaxima);
    }

    public double getCargaMaxima() {
        return cargaMaxima;
    }

    public void setCargaMaxima(double cargaMaxima) {
        // Validacion: no se permite carga negativa.
        if (cargaMaxima >= 0) {
            this.cargaMaxima = cargaMaxima;
        } else {
            System.out.println("Error: la carga maxima no puede ser negativa.");
            this.cargaMaxima = 0.0;
        }
    }

    @Override
    public void mostrarInformacion() {
        // Sobrescritura: mostramos informacion comun + dato propio del camion.
        System.out.println(
            "Tipo: Camion | Marca: " + getMarca() +
            " | Anio: " + getAnio() +
            " | Carga maxima: " + cargaMaxima + " toneladas"
        );
    }

    // Metodo personalizado para el camion.
    public void tocarBocina() {
        System.out.println("Camion " + getMarca() + ": POOOOON POOOOON!");
    }
}

/*
 * ACTIVIDADES PARA EL ALUMNO
 * --------------------------
 * 1. Agrega la clase Camion con:
 *    - atributo double cargaMaxima
 *    - validacion para evitar carga negativa
 *    - sobrescritura de mostrarInformacion()
 *
 * 2. Crea un metodo en Vehiculo llamado tocarBocina() y personalizalo en cada clase hija.
 *
 * 3. Investiga:
 *    - Diferencia entre sobrecarga y sobrescritura de metodos.
 *    - Que ventaja practica tiene el polimorfismo en proyectos grandes.
 *
 * 4. Reto:
 *    Crea un arreglo de 5 vehiculos mezclando Coche, Moto y Camion.
 *    Recorre el arreglo y llama a mostrarInformacion(), arrancar() y tocarBocina().
 */
