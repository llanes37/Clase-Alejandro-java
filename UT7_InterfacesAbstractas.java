/*
 * ******************************************************************************************
 *                       TEORIA Y CONCEPTOS: INTERFACES Y CLASES ABSTRACTAS EN JAVA
 * ------------------------------------------------------------------------------------------
 * En esta practica aprenderas a:
 *
 * - Comprender que es una clase abstracta y cuando usarla.
 * - Definir metodos abstractos y metodos concretos.
 * - Crear e implementar interfaces.
 * - Combinar herencia + contratos para disenar codigo flexible.
 * - Reforzar encapsulacion, validaciones y buenas practicas.
 *
 * Objetivo de clase:
 * Modelar un sistema de pagos con distintos metodos de cobro.
 ******************************************************************************************
 */

/*
 * TEORIA GLOBAL: ABSTRACT + INTERFACE
 * -----------------------------------
 *
 * 1) Clase abstracta:
 *    Es una clase que no se puede instanciar directamente.
 *    Sirve como base para compartir atributos y logica comun.
 *
 * 2) Metodo abstracto:
 *    Es un metodo sin implementacion en la clase base.
 *    Obliga a las clases hijas a implementar su propia version.
 *
 * 3) Interface:
 *    Define un contrato de metodos que una clase debe cumplir.
 *    Una clase puede implementar varias interfaces.
 *
 * Ejemplo corto:
 * MetodoPago p1 = new PagoTarjeta("Ana", 120.50, "1234");
 * MetodoPago p2 = new PagoBizum("Luis", 80.00, "600112233");
 * p1.procesarPago();
 * p2.procesarPago();
 */

// Clase principal para ejecutar la practica
public class UT7_InterfacesAbstractas {
    public static void main(String[] args) {
        // * ✅ Creamos objetos de distintas implementaciones
        MetodoPago pago1 = new PagoTarjeta("Ana", 120.50, "1234");
        MetodoPago pago2 = new PagoBizum("Luis", 80.00, "600112233");

        // * ✅ Polimorfismo via interface
        MetodoPago[] pagos = { pago1, pago2 };

        for (MetodoPago pago : pagos) {
            pago.procesarPago();
            pago.mostrarResumen();
            System.out.println("-----------------------------------");
        }

        // * 🛠️ Cambio de datos en un objeto especifico
        PagoTarjeta pagoTarjetaReal = (PagoTarjeta) pago1;
        pagoTarjetaReal.setImporte(150.00);
        pagoTarjetaReal.mostrarResumen();

        // ! ✅ TAREA RAPIDA:
        // * 1. Crea una clase PagoTransferencia que herede de PagoBase.
        // * 2. Implementa procesarPago() validando un IBAN simple.
        // * 3. Agrega objetos PagoTransferencia al arreglo y prueba.
    }
}

// Definimos la interface que representa el contrato comun
interface MetodoPago {
    void procesarPago();
    void mostrarResumen();
}

// Definimos la clase abstracta base con datos compartidos
abstract class PagoBase implements MetodoPago {
    private String titular;
    private double importe;

    public PagoBase(String titular, double importe) {
        setTitular(titular);
        setImporte(importe);
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        if (titular != null && !titular.trim().isEmpty()) {
            this.titular = titular;
        } else {
            System.out.println("Error: el titular no puede estar vacio.");
            this.titular = "Sin titular";
        }
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        if (importe > 0) {
            this.importe = importe;
        } else {
            System.out.println("Error: el importe debe ser mayor que 0.");
            this.importe = 1;
        }
    }

    // * ✅ Metodo concreto reutilizable por todas las clases hijas
    @Override
    public void mostrarResumen() {
        System.out.println("Titular: " + titular + " | Importe: " + importe + " EUR");
    }

    // * ⚠️ Metodo abstracto: cada tipo de pago define su propio proceso
    @Override
    public abstract void procesarPago();
}

// Definimos la implementacion concreta: pago con tarjeta
class PagoTarjeta extends PagoBase {
    private String ultimos4;

    public PagoTarjeta(String titular, double importe, String ultimos4) {
        super(titular, importe);
        setUltimos4(ultimos4);
    }

    public String getUltimos4() {
        return ultimos4;
    }

    public void setUltimos4(String ultimos4) {
        if (ultimos4 != null && ultimos4.matches("\\d{4}")) {
            this.ultimos4 = ultimos4;
        } else {
            System.out.println("Error: ultimos4 debe tener 4 digitos.");
            this.ultimos4 = "0000";
        }
    }

    @Override
    public void procesarPago() {
        System.out.println(
            "Procesando pago con tarjeta terminada en " + ultimos4 +
            " por " + getImporte() + " EUR"
        );
    }

    @Override
    public void mostrarResumen() {
        System.out.println(
            "Tipo: Tarjeta | Titular: " + getTitular() +
            " | Importe: " + getImporte() +
            " EUR | Ultimos4: " + ultimos4
        );
    }
}

// Definimos la implementacion concreta: pago con Bizum
class PagoBizum extends PagoBase {
    private String telefono;

    public PagoBizum(String titular, double importe, String telefono) {
        super(titular, importe);
        setTelefono(telefono);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono != null && telefono.matches("\\d{9}")) {
            this.telefono = telefono;
        } else {
            System.out.println("Error: telefono Bizum debe tener 9 digitos.");
            this.telefono = "000000000";
        }
    }

    @Override
    public void procesarPago() {
        System.out.println(
            "Procesando Bizum al telefono " + telefono +
            " por " + getImporte() + " EUR"
        );
    }

    @Override
    public void mostrarResumen() {
        System.out.println(
            "Tipo: Bizum | Titular: " + getTitular() +
            " | Importe: " + getImporte() +
            " EUR | Telefono: " + telefono
        );
    }
}

/*
 * ACTIVIDADES PARA EL ALUMNO
 * --------------------------
 * 1. Crea la clase PagoTransferencia con:
 *    - atributo String iban
 *    - validacion basica de longitud de IBAN
 *    - implementacion de procesarPago()
 *
 * 2. Agrega una nueva interface llamada Reembolsable con metodo reembolsar(double).
 *
 * 3. Haz que PagoTarjeta implemente Reembolsable y programe la logica.
 *
 * 4. Reto:
 *    Crea un arreglo con 5 pagos mezclando Tarjeta, Bizum y Transferencia.
 *    Recorre el arreglo y llama a procesarPago() y mostrarResumen().
 */
