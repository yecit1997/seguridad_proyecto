package com.seguridadnacional.seguridad.controllers;
import com.seguridadnacional.seguridad.dao.SupervisorDAO;
import com.seguridadnacional.seguridad.models.Supervisor;
import java.sql.SQLException;
import java.util.List;
public class SupervisorController {
    private final SupervisorDAO dao = new SupervisorDAO();
    public void crear(Supervisor s) { try { dao.insertar(s); } catch (SQLException e) { throw new RuntimeException("Error al crear supervisor: " + e.getMessage(), e); } }
    public Supervisor buscarPorId(int id) { try { return dao.buscarPorId(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<Supervisor> listarTodos() { try { return dao.listarTodos(); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void actualizar(Supervisor s) { try { dao.actualizar(s); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void eliminar(int id) { try { dao.eliminar(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
}