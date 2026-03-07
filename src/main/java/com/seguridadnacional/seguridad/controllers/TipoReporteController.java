package com.seguridadnacional.seguridad.controllers;
import com.seguridadnacional.seguridad.dao.TipoReporteDAO;
import com.seguridadnacional.seguridad.models.TipoReporte;
import java.sql.SQLException;
import java.util.List;
public class TipoReporteController {
    private final TipoReporteDAO dao = new TipoReporteDAO();
    public TipoReporte crear(TipoReporte t) { try { int id = dao.insertar(t); t.setIdTipoReporte(id); return t; } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public TipoReporte buscarPorId(int id) { try { return dao.buscarPorId(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<TipoReporte> listarTodos() { try { return dao.listarTodos(); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void actualizar(TipoReporte t) { try { dao.actualizar(t); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void eliminar(int id) { try { dao.eliminar(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
}