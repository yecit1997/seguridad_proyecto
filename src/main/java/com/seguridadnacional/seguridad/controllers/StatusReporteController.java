package com.seguridadnacional.seguridad.controllers;
import com.seguridadnacional.seguridad.dao.StatusReporteDAO;
import com.seguridadnacional.seguridad.models.StatusReporte;
import java.sql.SQLException;
import java.util.List;
public class StatusReporteController {
    private final StatusReporteDAO dao = new StatusReporteDAO();
    public StatusReporte crear(StatusReporte s) { try { int id = dao.insertar(s); s.setIdStatusReporte(id); return s; } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public StatusReporte buscarPorId(int id) { try { return dao.buscarPorId(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<StatusReporte> listarTodos() { try { return dao.listarTodos(); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void actualizar(StatusReporte s) { try { dao.actualizar(s); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void eliminar(int id) { try { dao.eliminar(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
}