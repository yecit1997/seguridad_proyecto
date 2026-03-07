package com.seguridadnacional.seguridad.models;

public class Vehiculo {

    private int       idVehiculo;
    private String    placa;
    private Conductor conductor;   // nullable según BD

    public Vehiculo() {}

    public Vehiculo(String placa, Conductor conductor) {
        this.placa     = placa;
        this.conductor = conductor;
    }

    public Vehiculo(int idVehiculo, String placa, Conductor conductor) {
        this.idVehiculo = idVehiculo;
        this.placa      = placa;
        this.conductor  = conductor;
    }

    public int       getIdVehiculo()               { return idVehiculo; }
    public void      setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }
    public String    getPlaca()                    { return placa; }
    public void      setPlaca(String placa)        { this.placa = placa; }
    public Conductor getConductor()                { return conductor; }
    public void      setConductor(Conductor c)     { this.conductor = c; }

    // Helper: FK en BD es conductor_id_fk_persona
    public int getConductorIdFkPersona() { return conductor != null ? conductor.getIdFkPersona() : 0; }

    @Override
    public String toString() {
        return "Vehiculo{id=" + idVehiculo + ", placa=" + placa + "}";
    }
}