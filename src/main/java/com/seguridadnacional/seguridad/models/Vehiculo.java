package com.seguridadnacional.seguridad.models;

/**
 * Modelo para la tabla: vehiculo
 *
 * CREATE TABLE vehiculo (
 *   id_vehiculo               INT NOT NULL,
 *   placa                     VARCHAR(45) NOT NULL,
 *   conductor_id_conductor    INT NOT NULL,
 *   PRIMARY KEY (id_vehiculo),
 *   FOREIGN KEY (conductor_id_conductor) REFERENCES conductor(id_conductor)
 * );
 *
 * Nota: id_vehiculo NO tiene AUTO_INCREMENT en el modelo,
 * el valor se asigna manualmente.
 */
public class Vehiculo {

    private int       idVehiculo;   // NOT NULL, sin AUTO_INCREMENT
    private String    placa;        // NOT NULL, VARCHAR(45)
    private Conductor conductor;    // FK -> conductor.id_conductor  NOT NULL

    public Vehiculo() {}

    public Vehiculo(int idVehiculo, String placa, Conductor conductor) {
        this.idVehiculo = idVehiculo;
        this.placa      = placa;
        this.conductor  = conductor;
    }

    public int    getIdVehiculo()                      { return idVehiculo; }
    public void   setIdVehiculo(int idVehiculo)         { this.idVehiculo = idVehiculo; }

    public String getPlaca()               { return placa; }
    public void   setPlaca(String placa)   { this.placa = placa; }

    public Conductor getConductor()                    { return conductor; }
    public void      setConductor(Conductor conductor) { this.conductor = conductor; }

    /** FK helper */
    public int getConductorId() { return conductor != null ? conductor.getIdConductor() : 0; }

    @Override
    public String toString() {
        return "Vehiculo{idVehiculo=" + idVehiculo +
               ", placa='" + placa +
               "', conductorId=" + getConductorId() + "}";
    }
}