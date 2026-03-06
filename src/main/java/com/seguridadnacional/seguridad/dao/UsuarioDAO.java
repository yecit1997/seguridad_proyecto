package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombre_usuario, contrasena, " +
                     "status_usuario_id_status_usuario, rol_usuario_id_rol_usuario) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasena());
            ps.setInt(3, usuario.getStatusUsuarioId());
            ps.setInt(4, usuario.getRolUsuarioId());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) usuario.setIdUsuario(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = buildSelectJoin() + " WHERE u.id_usuario = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public Usuario buscarPorNombreUsuario(String nombreUsuario) throws SQLException {
        String sql = buildSelectJoin() + " WHERE u.nombre_usuario = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    /** Login: valida credenciales y retorna el Usuario si son correctas */
    public Usuario login(String nombreUsuario, String contrasena) throws SQLException {
        String sql = buildSelectJoin() + " WHERE u.nombre_usuario = ? AND u.contrasena = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Usuario> listarTodos() throws SQLException {
        String sql = buildSelectJoin();
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nombre_usuario=?, contrasena=?, " +
                     "status_usuario_id_status_usuario=?, rol_usuario_id_rol_usuario=? " +
                     "WHERE id_usuario=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasena());
            ps.setInt(3, usuario.getStatusUsuarioId());
            ps.setInt(4, usuario.getRolUsuarioId());
            ps.setInt(5, usuario.getIdUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private String buildSelectJoin() {
        return "SELECT u.id_usuario, u.nombre_usuario, u.contrasena, " +
               "su.id_status_usuario, su.status, " +
               "ru.id_rol_usuario, ru.nombre_rol_usuario, " +
               "p.id_permisos, p.nombre_permiso " +
               "FROM usuario u " +
               "JOIN status_usuario su ON u.status_usuario_id_status_usuario = su.id_status_usuario " +
               "JOIN rol_usuario ru    ON u.rol_usuario_id_rol_usuario        = ru.id_rol_usuario " +
               "JOIN permisos p        ON ru.permisos_id_permisos              = p.id_permisos";
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Permisos permisos = new Permisos(rs.getInt("id_permisos"), rs.getString("nombre_permiso"));
        RolUsuario rol    = new RolUsuario(rs.getInt("id_rol_usuario"), rs.getString("nombre_rol_usuario"), permisos);
        StatusUsuario status = new StatusUsuario(rs.getInt("id_status_usuario"), rs.getString("status"));
        return new Usuario(
            rs.getInt("id_usuario"),
            rs.getString("nombre_usuario"),
            rs.getString("contrasena"),
            status,
            rol
        );
    }
}