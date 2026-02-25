/*
 * ==========================================================================================
 * PLANTILLA RELLENABLE - UT6 HERENCIA Y POLIMORFISMO
 * ------------------------------------------------------------------------------------------
 * Objetivo:
 * Completar todos los TODO para practicar herencia, polimorfismo, interfaces y encapsulacion.
 * ==========================================================================================
 */

public class UT6_HerenciaPolimorfismo_Rellenable {
    public static void main(String[] args) {
        // ! PASO 1: Crea objetos de las clases hijas cuando completes sus constructores.
        // * Ejemplo:
        // * VehiculoBase coche = new CocheTurismo("Toyota", 2022, 5, 5);
        // * VehiculoBase moto = new MotoUrbana("Yamaha", 2019, 125, true);
        // * VehiculoBase patinete = new PatineteElectrico("Xiaomi", 2024, 35, 70);
        // * VehiculoBase camion = new CamionReparto("Volvo", 2017, 12000);

        // ! PASO 2: Crea un array polimorfico.
        // * VehiculoBase[] flota = { coche, moto, patinete, camion };

        // ! PASO 3: Recorre el array y llama metodos comunes.
        // * for (VehiculoBase vehiculo : flota) {
        // *     vehiculo.arrancar();
        // *     vehiculo.acelerar();
        // *     vehiculo.acelerar(10);
        // *     vehiculo.tocarBocina();
        // *     vehiculo.mostrarInformacion();
        // *     System.out.println("Mantenimiento: " + vehiculo.calcularCostoMantenimiento());
        // *     vehiculo.detener();
        // * }

        // ! PASO 4: Prueba metodos static.
        // * System.out.println(VehiculoBase.getVehiculosCreados());

        // ! PASO 5: Prueba instanceof + casting seguro.
        // * if (patinete instanceof Electrico) {
        // *     Electrico e = (Electrico) patinete;
        // *     e.cargarBateria(20);
        // * }
    }
}

// Contrato para unidades que pasan inspeccion
interface Inspeccionable {
    boolean pasarInspeccion();

    default void imprimirChecklist() {
        System.out.println("Checklist: frenos, luces, ruedas, documentacion.");
    }
}

// Contrato para unidades electricas
interface Electrico {
    void cargarBateria(int porcentaje);
    int getNivelBateria();

    default boolean bateriaBaja() {
        return getNivelBateria() < 20;
    }
}

// Clase base abstracta
abstract class VehiculoBase {
    private String marca;
    private int anio;
    private int velocidad;
    private final int idVehiculo;

    private static int contadorVehiculos = 0;

    public VehiculoBase(String marca, int anio) {
        // TODO: usar setters para validar datos de entrada.
        setMarca(marca);
        setAnio(anio);
        this.velocidad = 0;
        contadorVehiculos++;
        this.idVehiculo = contadorVehiculos;
    }

    // TODO: mostrar mensaje de arranque usando generarEtiqueta().
    public void arrancar() {
        System.out.println("TODO arrancar()");
    }

    // TODO: poner velocidad en 0 y mostrar mensaje.
    public void detener() {
        System.out.println("TODO detener()");
    }

    // TODO: sobrecarga 1 -> llamar a acelerar(5).
    public void acelerar() {
        // TODO implementar
    }

    // TODO: sobrecarga 2 -> sumar incremento solo si es positivo.
    public void acelerar(int incremento) {
        // TODO implementar
    }

    // TODO: devolver true si anio es antiguo (define regla).
    public boolean necesitaRevision() {
        return false;
    }

    // TODO: devolver etiqueta tipo "#id-marca-anio".
    public String generarEtiqueta() {
        return "TODO";
    }

    // Metodo final: no debe sobrescribirse en hijas.
    public final void mostrarIdentidadBase() {
        System.out.println("ID: " + idVehiculo + " | Marca: " + marca + " | Anio: " + anio);
    }

    // TODO: metodo static para devolver contador total.
    public static int getVehiculosCreados() {
        return contadorVehiculos;
    }

    // TODO: metodo static para devolver el vehiculo mas nuevo entre a y b.
    public static VehiculoBase obtenerMasNuevo(VehiculoBase a, VehiculoBase b) {
        // TODO implementar comparando anio
        return a;
    }

    // TODO: mostrar datos comunes.
    public void mostrarInformacion() {
        System.out.println("TODO mostrarInformacion() en VehiculoBase");
    }

    // Metodos abstractos obligatorios en clases hijas.
    public abstract void tocarBocina();
    public abstract double calcularCostoMantenimiento();

    public String getMarca() {
        return marca;
    }

    // TODO: validar que no sea null ni vacio.
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAnio() {
        return anio;
    }

    // TODO: validar anio >= 1886.
    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getVelocidad() {
        return velocidad;
    }

    protected void setVelocidad(int velocidad) {
        // TODO: evitar negativos.
        this.velocidad = velocidad;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }
}

// Clase abstracta intermedia (herencia multinivel)
abstract class VehiculoTerrestre extends VehiculoBase {
    private int ruedas;

    public VehiculoTerrestre(String marca, int anio, int ruedas) {
        super(marca, anio);
        setRuedas(ruedas);
    }

    public int getRuedas() {
        return ruedas;
    }

    // TODO: validar que ruedas > 0.
    public void setRuedas(int ruedas) {
        this.ruedas = ruedas;
    }

    @Override
    public void mostrarInformacion() {
        // TODO: puedes llamar a super.mostrarInformacion() y ampliar info.
        System.out.println("TODO mostrarInformacion() en VehiculoTerrestre");
    }
}

class CocheTurismo extends VehiculoTerrestre implements Inspeccionable {
    private int puertas;
    private int plazas;

    public CocheTurismo(String marca, int anio, int puertas, int plazas) {
        super(marca, anio, 4);
        // TODO: asignar con setters.
        setPuertas(puertas);
        setPlazas(plazas);
    }

    // Constructor sobrecargado
    public CocheTurismo(String marca, int anio, int puertas) {
        this(marca, anio, puertas, 5);
    }

    @Override
    public void tocarBocina() {
        // TODO: mensaje propio de coche.
        System.out.println("TODO bocina coche");
    }

    @Override
    public double calcularCostoMantenimiento() {
        // TODO: definir formula.
        return 0;
    }

    @Override
    public void mostrarInformacion() {
        // TODO: mostrar todos los campos relevantes.
        System.out.println("TODO mostrarInformacion() en CocheTurismo");
    }

    @Override
    public boolean pasarInspeccion() {
        // TODO: definir regla de aprobacion.
        return false;
    }

    public int getPuertas() {
        return puertas;
    }

    // TODO: validar > 0.
    public void setPuertas(int puertas) {
        this.puertas = puertas;
    }

    public int getPlazas() {
        return plazas;
    }

    // TODO: validar > 0.
    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }
}

class MotoUrbana extends VehiculoTerrestre implements Inspeccionable {
    private int cilindrada;
    private boolean tieneBaul;

    public MotoUrbana(String marca, int anio, int cilindrada, boolean tieneBaul) {
        super(marca, anio, 2);
        setCilindrada(cilindrada);
        this.tieneBaul = tieneBaul;
    }

    @Override
    public void tocarBocina() {
        // TODO: mensaje propio de moto.
        System.out.println("TODO bocina moto");
    }

    @Override
    public double calcularCostoMantenimiento() {
        // TODO: definir formula segun cilindrada.
        return 0;
    }

    @Override
    public void mostrarInformacion() {
        // TODO: mostrar datos.
        System.out.println("TODO mostrarInformacion() en MotoUrbana");
    }

    @Override
    public boolean pasarInspeccion() {
        // TODO: definir regla.
        return false;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    // TODO: validar > 0.
    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public boolean isTieneBaul() {
        return tieneBaul;
    }

    public void setTieneBaul(boolean tieneBaul) {
        this.tieneBaul = tieneBaul;
    }
}

class PatineteElectrico extends VehiculoTerrestre implements Electrico, Inspeccionable {
    private int autonomiaKm;
    private int nivelBateria;

    public PatineteElectrico(String marca, int anio, int autonomiaKm, int nivelBateria) {
        super(marca, anio, 2);
        setAutonomiaKm(autonomiaKm);
        setNivelBateria(nivelBateria);
    }

    @Override
    public void tocarBocina() {
        // TODO: mensaje propio.
        System.out.println("TODO bocina patinete");
    }

    @Override
    public double calcularCostoMantenimiento() {
        // TODO: definir formula.
        return 0;
    }

    // TODO: sobrescribir para gastar bateria al acelerar.
    @Override
    public void acelerar(int incremento) {
        // TODO implementar
    }

    @Override
    public void mostrarInformacion() {
        // TODO: mostrar datos.
        System.out.println("TODO mostrarInformacion() en PatineteElectrico");
    }

    @Override
    public void cargarBateria(int porcentaje) {
        // TODO: sumar porcentaje sin pasar de 100.
    }

    @Override
    public int getNivelBateria() {
        return nivelBateria;
    }

    @Override
    public boolean pasarInspeccion() {
        // TODO: definir regla.
        return false;
    }

    public int getAutonomiaKm() {
        return autonomiaKm;
    }

    // TODO: validar > 0.
    public void setAutonomiaKm(int autonomiaKm) {
        this.autonomiaKm = autonomiaKm;
    }

    // TODO: limitar entre 0 y 100.
    public void setNivelBateria(int nivelBateria) {
        this.nivelBateria = nivelBateria;
    }
}

class CamionReparto extends VehiculoTerrestre implements Inspeccionable {
    private double cargaMaxKg;
    private double cargaActualKg;

    public CamionReparto(String marca, int anio, double cargaMaxKg) {
        super(marca, anio, 6);
        setCargaMaxKg(cargaMaxKg);
        this.cargaActualKg = 0;
    }

    @Override
    public void tocarBocina() {
        // TODO: mensaje propio de camion.
        System.out.println("TODO bocina camion");
    }

    @Override
    public double calcularCostoMantenimiento() {
        // TODO: definir formula.
        return 0;
    }

    @Override
    public void mostrarInformacion() {
        // TODO: mostrar datos.
        System.out.println("TODO mostrarInformacion() en CamionReparto");
    }

    @Override
    public boolean pasarInspeccion() {
        // TODO: definir regla.
        return false;
    }

    // TODO: solo cargar si kg > 0 y no supera cargaMaxKg.
    public void cargarMercancia(double kg) {
        // TODO implementar
    }

    // TODO: descargar sin bajar de 0.
    public void descargarMercancia(double kg) {
        // TODO implementar
    }

    public double getCargaMaxKg() {
        return cargaMaxKg;
    }

    // TODO: validar > 0.
    public void setCargaMaxKg(double cargaMaxKg) {
        this.cargaMaxKg = cargaMaxKg;
    }

    public double getCargaActualKg() {
        return cargaActualKg;
    }

    // TODO: implementar regla de tres.
    public double porcentajeCarga() {
        return 0;
    }

    // TODO: devolver true si esta lleno.
    public boolean estaLleno() {
        return false;
    }
}

/*
 * ACTIVIDADES EXTRA (PARA SUBIR NOTA)
 * ------------------------------------------------------------------------------------------
 * 1) Crear clase AutobusEscolar y anadirla a la flota.
 * 2) Crear interface Rastreable con obtenerGPS().
 * 3) Implementar Rastreable en CamionReparto.
 * 4) Mostrar comportamiento especifico por tipo usando instanceof.
 */

