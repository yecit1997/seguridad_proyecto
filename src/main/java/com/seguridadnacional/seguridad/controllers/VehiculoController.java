package com.seguridadnacional.seguridad.controllers;
import com.seguridadnacional.seguridad.dao.VehiculoDAO;
import com.seguridadnacional.seguridad.models.Vehiculo;
import java.sql.SQLException;
import java.util.List;
public class VehiculoController {
    private final VehiculoDAO dao = new VehiculoDAO();
    public Vehiculo crear(Vehiculo v) { try { int id = dao.insertar(v); v.setIdVehiculo(id); return v; } catch (SQLException e) { throw new RuntimeException("Error al crear vehículo: " + e.getMessage(), e); } }
    public Vehiculo buscarPorId(int id) { try { return dao.buscarPorId(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public Vehiculo buscarPorPlaca(String placa) { try { return dao.buscarPorPlaca(placa); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<Vehiculo> listarTodos() { try { return dao.listarTodos(); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void actualizar(Vehiculo v) { try { dao.actualizar(v); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void eliminar(int id) { try { dao.eliminar(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
}