package com.seguridadnacional.seguridad.controllers;

import com.seguridadnacional.seguridad.dao.VehiculoDAO;
import com.seguridadnacional.seguridad.models.Vehiculo;
import java.sql.SQLException;
import java.util.List;

public class VehiculoController {

    private final VehiculoDAO dao = new VehiculoDAO();

    public Vehiculo registrarVehiculo(Vehiculo vehiculo) {
        try {
            validarVehiculo(vehiculo);
            boolean ok = dao.insertar(vehiculo);
            if (!ok) throw new RuntimeException("No se pudo registrar el vehículo");
            return vehiculo;
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar vehículo: " + e.getMessage(), e);
        }
    }

    public Vehiculo buscarPorId(int id) {
        try {
            Vehiculo v = dao.buscarPorId(id);
            if (v == null) throw new RuntimeException("Vehículo no encontrado con id: " + id);
            return v;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar vehículo: " + e.getMessage(), e);
        }
    }

    public Vehiculo buscarPorPlaca(String placa) {
        try {
            Vehiculo v = dao.buscarPorPlaca(placa);
            if (v == null) throw new RuntimeException("Vehículo no encontrado con placa: " + placa);
            return v;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por placa: " + e.getMessage(), e);
        }
    }

    public List<Vehiculo> listarTodos() {
        try {
            return dao.listarTodos();
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar vehículos: " + e.getMessage(), e);
        }
    }

    public Vehiculo actualizar(Vehiculo vehiculo) {
        try {
            validarVehiculo(vehiculo);
            boolean ok = dao.actualizar(vehiculo);
            if (!ok) throw new RuntimeException("No se pudo actualizar el vehículo");
            return vehiculo;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar vehículo: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        try {
            boolean ok = dao.eliminar(id);
            if (!ok) throw new RuntimeException("No se pudo eliminar el vehículo con id: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar vehículo: " + e.getMessage(), e);
        }
    }

    private void validarVehiculo(Vehiculo v) {
        if (v.getPlaca() == null || v.getPlaca().trim().isEmpty())
            throw new IllegalArgumentException("La placa del vehículo es obligatoria");
        if (v.getConductor() == null)
            throw new IllegalArgumentException("El conductor es obligatorio");
    }
}