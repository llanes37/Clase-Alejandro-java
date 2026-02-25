/*
 * ******************************************************************************************
 *                  TEORIA Y CONCEPTOS: HERENCIA Y POLIMORFISMO EN JAVA (COMPLETO)
 * ------------------------------------------------------------------------------------------
 * En esta practica aprenderas a:
 *
 * - Entender herencia simple, jerarquica y multinivel.
 * - Usar clases abstractas para definir una base comun.
 * - Aplicar polimorfismo con referencias del tipo padre.
 * - Diferenciar sobrecarga (overload) y sobrescritura (override).
 * - Usar metodos de muchos tipos: void, retorno, static, final y abstract.
 * - Aplicar interfaces para agregar comportamientos extra.
 * - Usar encapsulacion, validaciones e instanceof con casting seguro.
 *
 * Objetivo de clase:
 * Construir una flota de transporte donde cada tipo de vehiculo se comporta diferente.
 ******************************************************************************************
 */

/*
 * TEORIA GLOBAL (MUY DIDACTICA)
 * -----------------------------
 *
 * 1) HERENCIA:
 *    Una clase hija reutiliza atributos y metodos de una clase padre.
 *    Ejemplo: CocheTurismo hereda de VehiculoTerrestre, y VehiculoTerrestre hereda de VehiculoBase.
 *
 * 2) POLIMORFISMO:
 *    Una referencia del tipo padre puede apuntar a objetos de distintas hijas.
 *    Ejemplo: VehiculoBase v = new MotoUrbana(...);
 *
 * 3) SOBRESCRITURA (Override):
 *    La clase hija reescribe un metodo del padre para adaptar el comportamiento.
 *
 * 4) SOBRECARGA (Overload):
 *    Mismo nombre de metodo, pero distinta lista de parametros.
 *
 * 5) CLASE ABSTRACTA:
 *    No se puede crear con new. Sirve como plantilla para las clases hijas.
 *
 * 6) INTERFACE:
 *    Define un contrato de metodos. Una clase puede implementar varias interfaces.
 */

// Clase principal para ejecutar toda la practica
public class UT6_HerenciaPolimorfismo_Completo {
    public static void main(String[] args) {
        // * ? Creamos objetos de distintas clases hijas
        VehiculoBase coche = new CocheTurismo("Toyota", 2021, 5, 5);
        VehiculoBase moto = new MotoUrbana("Yamaha", 2019, 125, true);
        VehiculoBase patinete = new PatineteElectrico("Xiaomi", 2024, 45, 80);
        VehiculoBase camion = new CamionReparto("Volvo", 2018, 12000);

        // * ? Polimorfismo real: todos se tratan como VehiculoBase
        VehiculoBase[] flota = { coche, moto, patinete, camion };

        System.out.println("===== DEMO POLIMORFISMO EN ACCION =====");
        for (VehiculoBase vehiculo : flota) {
            vehiculo.arrancar();
            vehiculo.acelerar();        // Sobrecarga: version sin parametros
            vehiculo.acelerar(12);      // Sobrecarga: version con parametro
            vehiculo.tocarBocina();     // Sobrescritura: cada clase hace algo distinto
            vehiculo.mostrarInformacion();
            System.out.println("Costo mantenimiento estimado: " + vehiculo.calcularCostoMantenimiento() + " EUR");
            vehiculo.detener();
            System.out.println("-----------------------------------");
        }

        // * ? Metodo static del padre (utilidad general de clase)
        System.out.println("Vehiculos creados: " + VehiculoBase.getVehiculosCreados());

        // * ? Otro static: comparar anios de dos objetos
        VehiculoBase masNuevo = VehiculoBase.obtenerMasNuevo(coche, camion);
        System.out.println("Mas nuevo entre coche y camion: " + masNuevo.generarEtiqueta());

        // * ? Uso de interfaces con casting seguro
        System.out.println("===== DEMO INTERFACES =====");
        for (VehiculoBase vehiculo : flota) {
            if (vehiculo instanceof Inspeccionable) {
                Inspeccionable revisable = (Inspeccionable) vehiculo;
                revisable.imprimirChecklist();
                System.out.println("Pasa inspeccion: " + revisable.pasarInspeccion());
            }

            if (vehiculo instanceof Electrico) {
                Electrico electrico = (Electrico) vehiculo;
                System.out.println("Nivel bateria antes: " + electrico.getNivelBateria() + "%");
                electrico.cargarBateria(15);
                System.out.println("Nivel bateria despues: " + electrico.getNivelBateria() + "%");
                System.out.println("Bateria baja: " + electrico.bateriaBaja());
            }
            System.out.println("-----------------------------------");
        }

        // * ??? Downcasting controlado para usar metodos propios de una hija
        if (camion instanceof CamionReparto) {
            CamionReparto camionReal = (CamionReparto) camion;
            camionReal.cargarMercancia(3500);
            camionReal.descargarMercancia(1200);
            camionReal.mostrarInformacion();
        }

        // ! ? TAREAS RAPIDAS PARA EL ALUMNO:
        // * 1. Crea una clase hija AutobusEscolar que herede de VehiculoTerrestre.
        // * 2. Sobrescribe tocarBocina() y calcularCostoMantenimiento().
        // * 3. Haz que implemente Inspeccionable.
        // * 4. Agrega objetos AutobusEscolar a la flota y prueba polimorfismo.
    }
}

// Interface 1: contrato para unidades que pueden pasar inspeccion
interface Inspeccionable {
    boolean pasarInspeccion();

    // Metodo default: NO obliga a reescribir en cada clase
    default void imprimirChecklist() {
        System.out.println("Checklist: frenos, luces, ruedas, documentacion.");
    }
}

// Interface 2: contrato para unidades electricas
interface Electrico {
    void cargarBateria(int porcentaje);
    int getNivelBateria();

    default boolean bateriaBaja() {
        return getNivelBateria() < 20;
    }
}

// Clase abstracta base: contiene comportamiento comun para todos
abstract class VehiculoBase {
    private String marca;
    private int anio;
    private int velocidad;
    private final int idVehiculo;

    private static int contadorVehiculos = 0;

    public VehiculoBase(String marca, int anio) {
        setMarca(marca);
        setAnio(anio);
        this.velocidad = 0;
        contadorVehiculos++;
        this.idVehiculo = contadorVehiculos;
    }

    // * ? Metodo void comun
    public void arrancar() {
        System.out.println("El vehiculo " + generarEtiqueta() + " ha arrancado.");
    }

    // * ? Metodo void comun
    public void detener() {
        velocidad = 0;
        System.out.println("Vehiculo detenido.");
    }

    // * ? Sobrecarga 1: acelerar sin parametro
    public void acelerar() {
        acelerar(5);
    }

    // * ? Sobrecarga 2: acelerar con parametro
    public void acelerar(int incremento) {
        if (incremento > 0) {
            velocidad += incremento;
        }
    }

    // * ? Metodo con retorno boolean
    public boolean necesitaRevision() {
        return anio < 2020;
    }

    // * ? Metodo con retorno String
    public String generarEtiqueta() {
        return "#" + idVehiculo + "-" + marca + "-" + anio;
    }

    // * ? Metodo final: no puede ser sobrescrito en hijas
    public final void mostrarIdentidadBase() {
        System.out.println("ID: " + idVehiculo + " | Marca: " + marca + " | Anio: " + anio);
    }

    // * ? Metodo static de clase
    public static int getVehiculosCreados() {
        return contadorVehiculos;
    }

    // * ? Otro static utilitario
    public static VehiculoBase obtenerMasNuevo(VehiculoBase a, VehiculoBase b) {
        if (a.getAnio() >= b.getAnio()) {
            return a;
        }
        return b;
    }

    // Metodo concreto que normalmente se sobrescribe
    public void mostrarInformacion() {
        System.out.println(
            "Tipo base | Marca: " + marca +
            " | Anio: " + anio +
            " | Velocidad: " + velocidad
        );
    }

    // * ?? Metodos abstractos: obligatorios en clases hijas
    public abstract void tocarBocina();
    public abstract double calcularCostoMantenimiento();

    // Getters y setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (marca != null && !marca.trim().isEmpty()) {
            this.marca = marca;
        } else {
            this.marca = "MarcaDesconocida";
            System.out.println("Error: marca no valida. Se asigna MarcaDesconocida.");
        }
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        if (anio >= 1886) {
            this.anio = anio;
        } else {
            this.anio = 1886;
            System.out.println("Error: anio no valido. Se asigna 1886.");
        }
    }

    public int getVelocidad() {
        return velocidad;
    }

    protected void setVelocidad(int velocidad) {
        if (velocidad >= 0) {
            this.velocidad = velocidad;
        }
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }
}

// Herencia multinivel: VehiculoTerrestre hereda de VehiculoBase
abstract class VehiculoTerrestre extends VehiculoBase {
    private int ruedas;

    public VehiculoTerrestre(String marca, int anio, int ruedas) {
        super(marca, anio);
        setRuedas(ruedas);
    }

    public int getRuedas() {
        return ruedas;
    }

    public void setRuedas(int ruedas) {
        if (ruedas > 0) {
            this.ruedas = ruedas;
        } else {
            this.ruedas = 2;
            System.out.println("Error: ruedas no validas. Se asignan 2.");
        }
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Ruedas: " + ruedas + " | Revisar: " + necesitaRevision());
    }
}

// Clase hija 1
class CocheTurismo extends VehiculoTerrestre implements Inspeccionable {
    private int puertas;
    private int plazas;

    public CocheTurismo(String marca, int anio, int puertas, int plazas) {
        super(marca, anio, 4);
        setPuertas(puertas);
        setPlazas(plazas);
    }

    // Constructor sobrecargado
    public CocheTurismo(String marca, int anio, int puertas) {
        this(marca, anio, puertas, 5);
    }

    @Override
    public void tocarBocina() {
        System.out.println("Coche: pii pii.");
    }

    @Override
    public double calcularCostoMantenimiento() {
        return 180 + (getAnio() < 2020 ? 90 : 30);
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(
            "Tipo: CocheTurismo | Marca: " + getMarca() +
            " | Anio: " + getAnio() +
            " | Puertas: " + puertas +
            " | Plazas: " + plazas +
            " | Velocidad: " + getVelocidad()
        );
    }

    @Override
    public boolean pasarInspeccion() {
        return getAnio() >= 2000 && puertas >= 2;
    }

    public void activarModoEco() {
        System.out.println("Modo ECO activado: consumo optimizado.");
    }

    public int getPuertas() {
        return puertas;
    }

    public void setPuertas(int puertas) {
        if (puertas > 0) {
            this.puertas = puertas;
        } else {
            this.puertas = 4;
        }
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        if (plazas > 0) {
            this.plazas = plazas;
        } else {
            this.plazas = 5;
        }
    }
}

// Clase hija 2
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
        System.out.println("Moto: piii.");
    }

    @Override
    public double calcularCostoMantenimiento() {
        return 90 + (cilindrada > 250 ? 70 : 25);
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(
            "Tipo: MotoUrbana | Marca: " + getMarca() +
            " | Anio: " + getAnio() +
            " | Cilindrada: " + cilindrada + "cc" +
            " | Baul: " + (tieneBaul ? "Si" : "No") +
            " | Velocidad: " + getVelocidad()
        );
    }

    @Override
    public boolean pasarInspeccion() {
        return cilindrada > 49;
    }

    public void hacerCaballito() {
        System.out.println("Caballito solo en entorno controlado y seguro.");
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        if (cilindrada > 0) {
            this.cilindrada = cilindrada;
        } else {
            this.cilindrada = 125;
        }
    }

    public boolean isTieneBaul() {
        return tieneBaul;
    }

    public void setTieneBaul(boolean tieneBaul) {
        this.tieneBaul = tieneBaul;
    }
}

// Clase hija 3: tambien implementa interface Electrico
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
        System.out.println("Patinete: bip bip.");
    }

    @Override
    public double calcularCostoMantenimiento() {
        return 40 + ((100 - nivelBateria) * 0.5);
    }

    // Sobrescritura adicional: comportamiento propio al acelerar
    @Override
    public void acelerar(int incremento) {
        if (nivelBateria <= 5) {
            System.out.println("Bateria muy baja. No puede acelerar.");
            return;
        }

        super.acelerar(incremento);
        nivelBateria -= 1;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(
            "Tipo: PatineteElectrico | Marca: " + getMarca() +
            " | Anio: " + getAnio() +
            " | Autonomia: " + autonomiaKm + "km" +
            " | Bateria: " + nivelBateria + "%" +
            " | Velocidad: " + getVelocidad()
        );
    }

    @Override
    public void cargarBateria(int porcentaje) {
        if (porcentaje > 0) {
            nivelBateria += porcentaje;
            if (nivelBateria > 100) {
                nivelBateria = 100;
            }
        }
    }

    @Override
    public int getNivelBateria() {
        return nivelBateria;
    }

    @Override
    public boolean pasarInspeccion() {
        return autonomiaKm >= 15 && nivelBateria >= 10;
    }

    public int getAutonomiaKm() {
        return autonomiaKm;
    }

    public void setAutonomiaKm(int autonomiaKm) {
        if (autonomiaKm > 0) {
            this.autonomiaKm = autonomiaKm;
        } else {
            this.autonomiaKm = 20;
        }
    }

    public void setNivelBateria(int nivelBateria) {
        if (nivelBateria < 0) {
            this.nivelBateria = 0;
        } else if (nivelBateria > 100) {
            this.nivelBateria = 100;
        } else {
            this.nivelBateria = nivelBateria;
        }
    }
}

// Clase hija 4
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
        System.out.println("Camion: POOON POOON.");
    }

    @Override
    public double calcularCostoMantenimiento() {
        return 350 + (cargaActualKg * 0.02);
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(
            "Tipo: CamionReparto | Marca: " + getMarca() +
            " | Anio: " + getAnio() +
            " | Carga: " + cargaActualKg + "/" + cargaMaxKg + "kg" +
            " | Velocidad: " + getVelocidad()
        );
    }

    @Override
    public boolean pasarInspeccion() {
        return getAnio() >= 1995 && cargaMaxKg >= 1000;
    }

    public void cargarMercancia(double kg) {
        if (kg <= 0) {
            return;
        }

        if (cargaActualKg + kg <= cargaMaxKg) {
            cargaActualKg += kg;
        } else {
            System.out.println("No hay capacidad suficiente para cargar " + kg + "kg.");
        }
    }

    public void descargarMercancia(double kg) {
        if (kg <= 0) {
            return;
        }

        cargaActualKg -= kg;
        if (cargaActualKg < 0) {
            cargaActualKg = 0;
        }
    }

    public double getCargaMaxKg() {
        return cargaMaxKg;
    }

    public void setCargaMaxKg(double cargaMaxKg) {
        if (cargaMaxKg > 0) {
            this.cargaMaxKg = cargaMaxKg;
        } else {
            this.cargaMaxKg = 5000;
        }
    }

    public double getCargaActualKg() {
        return cargaActualKg;
    }

    // =====================================================================
    // ENTRENAMIENTO DE METODOS (PLANTILLAS PARA COMPLETAR EN CLASE)
    // =====================================================================

    // Metodo: porcentajeCarga()
    // Entrenar: retorno double y regla de tres.
    public double porcentajeCarga() {
        // TODO: devolver el % de carga actual sobre cargaMaxKg.
        return 0;
    }

    // Metodo: estaLleno()
    // Entrenar: retorno boolean con comparacion.
    public boolean estaLleno() {
        // TODO: devolver true si cargaActualKg == cargaMaxKg.
        return false;
    }
}

/*
 * ! ACTIVIDADES COMPLETAS PARA EL ALUMNO
 * --------------------------------------
 * * 1. Crear clase AutobusEscolar (hija de VehiculoTerrestre) con atributo numeroAlumnos.
 * * 2. Sobrescribir tocarBocina(), mostrarInformacion() y calcularCostoMantenimiento().
 * * 3. Implementar Inspeccionable en AutobusEscolar.
 * * 4. Crear al menos 2 constructores sobrecargados en AutobusEscolar.
 * * 5. Agregar metodo final en VehiculoBase y comprobar que no se puede sobrescribir.
 * * 6. Agregar metodo static en VehiculoBase para calcular media de anio de una flota.
 * * 7. Crear un nuevo interface Rastreable con metodo obtenerGPS().
 * * 8. Hacer que CamionReparto implemente Rastreable.
 * * 9. En main, crear un arreglo mixto de 8 vehiculos y recorrerlo con polimorfismo.
 * * 10. Usar instanceof para ejecutar logica especifica por tipo de vehiculo.
 * * 11. Completar los TODO de porcentajeCarga() y estaLleno().
 * * 12. Explicar con tus palabras diferencia entre sobrecarga y sobrescritura.
 *
 * ? PREGUNTAS DE REPASO
 * * - Por que una clase abstracta no se puede instanciar?
 * * - Que ventaja tiene programar contra el tipo padre?
 * * - Cuando usar interface y cuando usar herencia de clase?
 */
