package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.Persona;
import com.seguridadnacional.seguridad.models.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final String SELECT_JOIN =
        "SELECT u.id_usuario, u.nombre_usuario, u.contrasena, u.status, " +
        "       p.id_persona, p.dni, p.nombre, p.apellido, p.correo, p.telefono " +
        "FROM usuario u " +
        "JOIN persona p ON u.persona_id_persona = p.id_persona ";

    // ── INSERT ────────────────────────────────────────────────────────────────
    public int insertar(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (nombre_usuario, contrasena, status, persona_id_persona) VALUES (?,?,?,?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getContrasena());
            ps.setInt(3, u.isStatus() ? 1 : 0);
            ps.setInt(4, u.getPersonaId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    // ── LOGIN ─────────────────────────────────────────────────────────────────
    public Usuario login(String nombreUsuario, String contrasena) throws SQLException {
        String sql = SELECT_JOIN + "WHERE u.nombre_usuario = ? AND u.contrasena = ? AND u.status = 1";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    // ── SELECT BY ID ──────────────────────────────────────────────────────────
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = SELECT_JOIN + "WHERE u.id_usuario = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    // ── SELECT ALL ────────────────────────────────────────────────────────────
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "ORDER BY u.nombre_usuario";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    public boolean actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuario SET nombre_usuario=?, contrasena=?, status=?, persona_id_persona=? WHERE id_usuario=?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getContrasena());
            ps.setInt(3, u.isStatus() ? 1 : 0);
            ps.setInt(4, u.getPersonaId());
            ps.setInt(5, u.getIdUsuario());
            return ps.executeUpdate() > 0;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ── MAPPER ────────────────────────────────────────────────────────────────
    public static Usuario mapear(ResultSet rs) throws SQLException {
        Persona persona = new Persona(
            rs.getInt("id_persona"),
            rs.getString("dni"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("correo"),
            rs.getString("telefono")
        );
        return new Usuario(
            rs.getInt("id_usuario"),
            rs.getString("nombre_usuario"),
            rs.getString("contrasena"),
            rs.getInt("status") == 1,
            persona
        );
    }
}