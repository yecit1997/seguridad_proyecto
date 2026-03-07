package com.seguridadnacional.seguridad.controllers;
import com.seguridadnacional.seguridad.dao.RolDAO;
import com.seguridadnacional.seguridad.models.Rol;
import java.sql.SQLException;
import java.util.List;
public class RolController {
    private final RolDAO dao = new RolDAO();
    public Rol crear(Rol r) { try { int id = dao.insertar(r); r.setIdRol(id); return r; } catch (SQLException e) { throw new RuntimeException("Error al crear rol: " + e.getMessage(), e); } }
    public Rol buscarPorId(int id) { try { return dao.buscarPorId(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<Rol> listarTodos() { try { return dao.listarTodos(); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void actualizar(Rol r) { try { dao.actualizar(r); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void eliminar(int id) { try { dao.eliminar(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
}