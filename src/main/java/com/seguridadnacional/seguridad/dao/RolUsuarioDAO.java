package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.Permisos;
import com.seguridadnacional.seguridad.models.RolUsuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolUsuarioDAO {

    public boolean insertar(RolUsuario rol) throws SQLException {
        String sql = "INSERT INTO rol_usuario (nombre_rol_usuario, permisos_id_permisos) VALUES (?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, rol.getNombreRolUsuario());
            ps.setInt(2, rol.getPermisosId());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) rol.setIdRolUsuario(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public RolUsuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT r.*, p.nombre_permiso " +
                     "FROM rol_usuario r " +
                     "JOIN permisos p ON r.permisos_id_permisos = p.id_permisos " +
                     "WHERE r.id_rol_usuario = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<RolUsuario> listarTodos() throws SQLException {
        String sql = "SELECT r.*, p.nombre_permiso " +
                     "FROM rol_usuario r " +
                     "JOIN permisos p ON r.permisos_id_permisos = p.id_permisos";
        List<RolUsuario> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(RolUsuario rol) throws SQLException {
        String sql = "UPDATE rol_usuario SET nombre_rol_usuario=?, permisos_id_permisos=? WHERE id_rol_usuario=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol.getNombreRolUsuario());
            ps.setInt(2, rol.getPermisosId());
            ps.setInt(3, rol.getIdRolUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM rol_usuario WHERE id_rol_usuario = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private RolUsuario mapear(ResultSet rs) throws SQLException {
        Permisos permisos = new Permisos(
            rs.getInt("permisos_id_permisos"),
            rs.getString("nombre_permiso")
        );
        return new RolUsuario(
            rs.getInt("id_rol_usuario"),
            rs.getString("nombre_rol_usuario"),
            permisos
        );
    }
}