package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {

    public int insertar(Rol r) throws SQLException {
        String sql = "INSERT INTO rol (nombre) VALUES (?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, r.getNombre());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public Rol buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM rol WHERE id_rol = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Rol> listarTodos() throws SQLException {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT * FROM rol ORDER BY nombre";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(Rol r) throws SQLException {
        String sql = "UPDATE rol SET nombre=? WHERE id_rol=?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setInt(2, r.getIdRol());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM rol WHERE id_rol = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public static Rol mapear(ResultSet rs) throws SQLException {
        return new Rol(rs.getInt("id_rol"), rs.getString("nombre"));
    }
}