package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.StatusReporte;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusReporteDAO {

    public boolean insertar(StatusReporte status) throws SQLException {
        String sql = "INSERT INTO status_reporte (status) VALUES (?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, status.getStatus());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) status.setIdStatusReporte(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public StatusReporte buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM status_reporte WHERE id_status_reporte = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<StatusReporte> listarTodos() throws SQLException {
        String sql = "SELECT * FROM status_reporte";
        List<StatusReporte> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(StatusReporte status) throws SQLException {
        String sql = "UPDATE status_reporte SET status=? WHERE id_status_reporte=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.getStatus());
            ps.setInt(2, status.getIdStatusReporte());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM status_reporte WHERE id_status_reporte = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private StatusReporte mapear(ResultSet rs) throws SQLException {
        return new StatusReporte(rs.getInt("id_status_reporte"), rs.getString("status"));
    }
}