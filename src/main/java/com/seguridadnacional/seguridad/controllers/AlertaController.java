package com.seguridadnacional.seguridad.controllers;
import com.seguridadnacional.seguridad.dao.AlertaDAO;
import com.seguridadnacional.seguridad.models.Alerta;
import java.sql.SQLException;
import java.util.List;
public class AlertaController {
    private final AlertaDAO dao = new AlertaDAO();
    public Alerta crear(Alerta a) { try { int id = dao.insertar(a); a.setIdAlerta(id); return a; } catch (SQLException e) { throw new RuntimeException("Error al crear alerta: " + e.getMessage(), e); } }
    public Alerta buscarPorId(int id) { try { return dao.buscarPorId(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<Alerta> listarPorUsuario(int id) { try { return dao.listarPorUsuario(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public List<Alerta> listarNoLeidas(int id) { try { return dao.listarNoLeidasPorUsuario(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void marcarLeida(int id) { try { dao.marcarLeida(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
    public void eliminar(int id) { try { dao.eliminar(id); } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); } }
}