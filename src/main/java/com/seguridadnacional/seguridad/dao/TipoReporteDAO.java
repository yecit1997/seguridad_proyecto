package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.TipoReporte;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoReporteDAO {

    public boolean insertar(TipoReporte tipo) throws SQLException {
        String sql = "INSERT INTO tipo_reporte (tipo) VALUES (?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tipo.getTipo());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) tipo.setIdTipoReporte(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public TipoReporte buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tipo_reporte WHERE id_tipo_reporte = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<TipoReporte> listarTodos() throws SQLException {
        String sql = "SELECT * FROM tipo_reporte";
        List<TipoReporte> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(TipoReporte tipo) throws SQLException {
        String sql = "UPDATE tipo_reporte SET tipo=? WHERE id_tipo_reporte=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipo.getTipo());
            ps.setInt(2, tipo.getIdTipoReporte());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM tipo_reporte WHERE id_tipo_reporte = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private TipoReporte mapear(ResultSet rs) throws SQLException {
        return new TipoReporte(rs.getInt("id_tipo_reporte"), rs.getString("tipo"));
    }
}