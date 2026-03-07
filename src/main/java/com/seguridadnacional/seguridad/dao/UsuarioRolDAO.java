package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRolDAO {

    private static final String SELECT_JOIN =
        "SELECT ur.id_usuario_rol, ur.usuario_id_usuario, ur.rol_id_rol, " +
        "       u.nombre_usuario, u.contrasena, u.status, " +
        "       p.id_persona, p.dni, p.nombre, p.apellido, p.correo, p.telefono, " +
        "       r.nombre AS nombre_rol " +
        "FROM usuario_rol ur " +
        "JOIN usuario u ON ur.usuario_id_usuario = u.id_usuario " +
        "JOIN persona p ON u.persona_id_persona  = p.id_persona " +
        "JOIN rol r     ON ur.rol_id_rol          = r.id_rol ";

    public int insertar(UsuarioRol ur) throws SQLException {
        String sql = "INSERT INTO usuario_rol (usuario_id_usuario, rol_id_rol) VALUES (?,?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ur.getUsuarioId());
            ps.setInt(2, ur.getRolId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public UsuarioRol buscarPorId(int id) throws SQLException {
        String sql = SELECT_JOIN + "WHERE ur.id_usuario_rol = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<UsuarioRol> listarPorUsuario(int idUsuario) throws SQLException {
        List<UsuarioRol> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "WHERE ur.usuario_id_usuario = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<UsuarioRol> listarTodos() throws SQLException {
        List<UsuarioRol> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "ORDER BY p.apellido, p.nombre";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuario_rol WHERE id_usuario_rol = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public static UsuarioRol mapear(ResultSet rs) throws SQLException {
        Persona persona = new Persona(
            rs.getInt("id_persona"), rs.getString("dni"),
            rs.getString("nombre"),  rs.getString("apellido"),
            rs.getString("correo"),  rs.getString("telefono")
        );
        Usuario usuario = new Usuario(
            rs.getInt("usuario_id_usuario"),
            rs.getString("nombre_usuario"),
            rs.getString("contrasena"),
            rs.getInt("status") == 1,
            persona
        );
        Rol rol = new Rol(rs.getInt("rol_id_rol"), rs.getString("nombre_rol"));
        return new UsuarioRol(rs.getInt("id_usuario_rol"), usuario, rol);
    }
}