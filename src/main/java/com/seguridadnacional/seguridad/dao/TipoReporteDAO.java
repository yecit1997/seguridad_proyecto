package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.TipoReporte;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoReporteDAO {

    public int insertar(TipoReporte t) throws SQLException {
        String sql = "INSERT INTO tipo_reporte (nombre) VALUES (?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getNombre());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : 0; }
        }
    }

    public TipoReporte buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tipo_reporte WHERE id_tipo_reporte = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public List<TipoReporte> listarTodos() throws SQLException {
        List<TipoReporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipo_reporte ORDER BY nombre";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(TipoReporte t) throws SQLException {
        String sql = "UPDATE tipo_reporte SET nombre=? WHERE id_tipo_reporte=?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, t.getNombre()); ps.setInt(2, t.getIdTipoReporte());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM tipo_reporte WHERE id_tipo_reporte = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        }
    }

    public static TipoReporte mapear(ResultSet rs) throws SQLException {
        return new TipoReporte(rs.getInt("id_tipo_reporte"), rs.getString("nombre"));
    }
}