package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.Areas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AreasDAO {

    public boolean insertar(Areas area) throws SQLException {
        String sql = "INSERT INTO areas (nombre_area) VALUES (?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, area.getNombreArea());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) area.setIdAreas(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public Areas buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM areas WHERE id_areas = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Areas> listarTodos() throws SQLException {
        String sql = "SELECT * FROM areas";
        List<Areas> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(Areas area) throws SQLException {
        String sql = "UPDATE areas SET nombre_area=? WHERE id_areas=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, area.getNombreArea());
            ps.setInt(2, area.getIdAreas());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM areas WHERE id_areas = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Areas mapear(ResultSet rs) throws SQLException {
        return new Areas(rs.getInt("id_areas"), rs.getString("nombre_area"));
    }
}