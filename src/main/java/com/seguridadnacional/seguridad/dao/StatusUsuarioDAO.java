package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.StatusUsuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusUsuarioDAO {

    public boolean insertar(StatusUsuario status) throws SQLException {
        String sql = "INSERT INTO status_usuario (status) VALUES (?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, status.getStatus());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) status.setIdStatusUsuario(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public StatusUsuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM status_usuario WHERE id_status_usuario = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<StatusUsuario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM status_usuario";
        List<StatusUsuario> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(StatusUsuario status) throws SQLException {
        String sql = "UPDATE status_usuario SET status=? WHERE id_status_usuario=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.getStatus());
            ps.setInt(2, status.getIdStatusUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM status_usuario WHERE id_status_usuario = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private StatusUsuario mapear(ResultSet rs) throws SQLException {
        return new StatusUsuario(rs.getInt("id_status_usuario"), rs.getString("status"));
    }
}