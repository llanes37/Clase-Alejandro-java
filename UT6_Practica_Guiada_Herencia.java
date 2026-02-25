import java.util.Scanner;

/*
 * ==========================================================================================
 * EJERCICIO GUIADO (NIVEL INICIAL): HERENCIA + POLIMORFISMO + VALIDACIONES
 * ==========================================================================================
 * ENUNCIADO SENCILLO:
 * Una tienda de transporte quiere gestionar 3 tipos de vehiculos:
 * - Coche
 * - Moto
 * - Camion
 *
 * De TODOS los vehiculos se guarda:
 * - marca (String)
 * - anio (int)
 *
 * Ademas:
 * - Coche guarda numero de puertas (int)
 * - Moto guarda si tiene baul (boolean)
 * - Camion guarda carga maxima (double)
 *
 * REQUISITOS DEL EJERCICIO:
 * 1) Crear la clase padre Vehiculo y las clases hijas Coche, Moto y Camion.
 * 2) Sobrescribir mostrarInformacion() en cada clase hija.
 * 3) Crear metodo tocarBocina() en Vehiculo y personalizarlo en cada hija.
 * 4) Validar:
 *    - anio >= 1886
 *    - puertas > 0
 *    - cargaMaxima >= 0
 * 5) Crear un arreglo de Vehiculo y recorrerlo (polimorfismo).
 * 6) Pedir por teclado SOLO los datos del camion (marca, anio, carga maxima).
 *
 * OBJETIVO:
 * Practicar exactamente el tipo de ejercicio que estas viendo, pero en version mas facil.
 * ==========================================================================================
 */

public class UT6_Practica_Guiada_Herencia {
    public static void main(String[] args) {
        // Scanner para leer datos por consola.
        Scanner sc = new Scanner(System.in);

        // 1) Objetos con datos fijos para que tengas ejemplos listos.
        Vehiculo coche = new Coche("Seat", 2020, 5);
        Vehiculo moto = new Moto("Yamaha", 2022, true);

        // 2) Camion por teclado (parte de practica de entrada de datos).
        System.out.println("=== ALTA DE CAMION ===");
        System.out.print("Marca: ");
        String marcaCamion = sc.nextLine();

        System.out.print("Anio: ");
        int anioCamion = Integer.parseInt(sc.nextLine());

        System.out.print("Carga maxima (toneladas): ");
        double cargaCamion = Double.parseDouble(sc.nextLine());

        Vehiculo camion = new Camion(marcaCamion, anioCamion, cargaCamion);

        // 3) Arreglo polimorfico: distintos tipos guardados como Vehiculo.
        Vehiculo[] flota = { coche, moto, camion };

        // 4) Recorremos y llamamos metodos comunes/sobrescritos.
        //    Java ejecuta automaticamente la version correcta segun el objeto real.
        System.out.println("\n=== INFORMACION DE LA FLOTA ===");
        for (Vehiculo v : flota) {
            v.mostrarInformacion();
            v.arrancar();
            v.tocarBocina();
            System.out.println("---------------------------------");
        }

        sc.close();
    }
}

/*
 * CLASE PADRE
 * - Contiene lo comun para todos los vehiculos.
 */
class Vehiculo {
    private String marca;
    private int anio;

    // Constructor base.
    public Vehiculo(String marca, int anio) {
        this.marca = marca;
        setAnio(anio); // usamos setter para aplicar validacion
    }

    // Metodo comun para todos.
    public void arrancar() {
        System.out.println("El vehiculo " + marca + " ha arrancado.");
    }

    // Metodo pensado para sobrescribir en clases hijas.
    public void mostrarInformacion() {
        System.out.println("Marca: " + marca + " | Anio: " + anio);
    }

    // Metodo comun de bocina (las hijas lo personalizan).
    public void tocarBocina() {
        System.out.println("Bocina generica: pi pi");
    }

    // Getters/setters.
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
        // Validacion minima: primer vehiculo moderno historico.
        if (anio >= 1886) {
            this.anio = anio;
        } else {
            System.out.println("Error: anio invalido, se asigna 1886 por defecto.");
            this.anio = 1886;
        }
    }
}

/*
 * CLASE HIJA Coche
 */
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
            System.out.println("Error: puertas invalidas, se asigna 1.");
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

    @Override
    public void tocarBocina() {
        System.out.println("Coche " + getMarca() + ": pi piiii");
    }
}

/*
 * CLASE HIJA Moto
 */
class Moto extends Vehiculo {
    private boolean tieneBaul;

    public Moto(String marca, int anio, boolean tieneBaul) {
        super(marca, anio);
        this.tieneBaul = tieneBaul;
    }

    public boolean isTieneBaul() {
        return tieneBaul;
    }

    public void setTieneBaul(boolean tieneBaul) {
        this.tieneBaul = tieneBaul;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(
            "Tipo: Moto | Marca: " + getMarca() +
            " | Anio: " + getAnio() +
            " | Baul: " + (tieneBaul ? "Si" : "No")
        );
    }

    @Override
    public void tocarBocina() {
        System.out.println("Moto " + getMarca() + ": piii");
    }
}

/*
 * CLASE HIJA Camion
 */
class Camion extends Vehiculo {
    private double cargaMaxima;

    public Camion(String marca, int anio, double cargaMaxima) {
        super(marca, anio);
        setCargaMaxima(cargaMaxima);
    }

    public double getCargaMaxima() {
        return cargaMaxima;
    }

    public void setCargaMaxima(double cargaMaxima) {
        if (cargaMaxima >= 0) {
            this.cargaMaxima = cargaMaxima;
        } else {
            System.out.println("Error: carga negativa, se asigna 0.0.");
            this.cargaMaxima = 0.0;
        }
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(
            "Tipo: Camion | Marca: " + getMarca() +
            " | Anio: " + getAnio() +
            " | Carga maxima: " + cargaMaxima + " t"
        );
    }

    @Override
    public void tocarBocina() {
        System.out.println("Camion " + getMarca() + ": POOOON POOOON");
    }
}
