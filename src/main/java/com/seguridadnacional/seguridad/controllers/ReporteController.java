package com.seguridadnacional.seguridad.controllers;
import com.seguridadnacional.seguridad.dao.ReporteDAO;
import com.seguridadnacional.seguridad.models.Reporte;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
public class ReporteController {
    private final ReporteDAO dao = new ReporteDAO();
    public Reporte crear(Reporte r) {
        try { r.setFechaCreacion(LocalDateTime.now()); int id = dao.insertar(r); r.setIdReporte(id); return r; }
        catch (SQLException e) { throw new RuntimeException("Error al crear reporte: " + e.getMessage(), e); }
    }
    public Reporte buscarPorId(int id) { try { return dao.buscarPorId(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<Reporte> listarTodos() { try { return dao.listarTodos(); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<Reporte> listarPorUsuario(int id) { try { return dao.listarPorUsuario(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<Reporte> listarPorTipo(int id) { try { return dao.listarPorTipo(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<Reporte> listarPorStatus(int id) { try { return dao.listarPorStatus(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void actualizar(Reporte r) { try { dao.actualizar(r); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void actualizarStatus(int idReporte, int idStatus) { try { dao.actualizarStatus(idReporte, idStatus); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void eliminar(int id) { try { dao.eliminar(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
}