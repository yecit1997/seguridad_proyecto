package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.StatusReporte;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusReporteDAO {

    public int insertar(StatusReporte s) throws SQLException {
        String sql = "INSERT INTO status_reporte (nombre) VALUES (?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getNombre());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : 0; }
        }
    }

    public StatusReporte buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM status_reporte WHERE id_status_reporte = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public List<StatusReporte> listarTodos() throws SQLException {
        List<StatusReporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM status_reporte ORDER BY nombre";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(StatusReporte s) throws SQLException {
        String sql = "UPDATE status_reporte SET nombre=? WHERE id_status_reporte=?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getNombre()); ps.setInt(2, s.getIdStatusReporte());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM status_reporte WHERE id_status_reporte = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        }
    }

    public static StatusReporte mapear(ResultSet rs) throws SQLException {
        return new StatusReporte(rs.getInt("id_status_reporte"), rs.getString("nombre"));
    }
}