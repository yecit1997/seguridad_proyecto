package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupervisorDAO {

    private static final String SELECT_JOIN =
        "SELECT s.usuario_rol_id_usuario_rol, s.fecha_ascenso, " +
        "       ur.usuario_id_usuario, ur.rol_id_rol, " +
        "       u.nombre_usuario, u.contrasena, u.status, " +
        "       p.id_persona, p.dni, p.nombre, p.apellido, p.correo, p.telefono, " +
        "       r.nombre AS nombre_rol " +
        "FROM supervisor s " +
        "JOIN usuario_rol ur ON s.usuario_rol_id_usuario_rol = ur.id_usuario_rol " +
        "JOIN usuario u      ON ur.usuario_id_usuario = u.id_usuario " +
        "JOIN persona p      ON u.persona_id_persona  = p.id_persona " +
        "JOIN rol r          ON ur.rol_id_rol          = r.id_rol ";

    public boolean insertar(Supervisor s) throws SQLException {
        String sql = "INSERT INTO supervisor (usuario_rol_id_usuario_rol, fecha_ascenso) VALUES (?,?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, s.getUsuarioRolId());
            ps.setDate(2, s.getFechaAscenso() != null ? Date.valueOf(s.getFechaAscenso()) : null);
            return ps.executeUpdate() > 0;
        }
    }

    public Supervisor buscarPorId(int usuarioRolId) throws SQLException {
        String sql = SELECT_JOIN + "WHERE s.usuario_rol_id_usuario_rol = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioRolId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Supervisor> listarTodos() throws SQLException {
        List<Supervisor> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "ORDER BY p.apellido, p.nombre";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(Supervisor s) throws SQLException {
        String sql = "UPDATE supervisor SET fecha_ascenso=? WHERE usuario_rol_id_usuario_rol=?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, s.getFechaAscenso() != null ? Date.valueOf(s.getFechaAscenso()) : null);
            ps.setInt(2, s.getUsuarioRolId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int usuarioRolId) throws SQLException {
        String sql = "DELETE FROM supervisor WHERE usuario_rol_id_usuario_rol = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioRolId);
            return ps.executeUpdate() > 0;
        }
    }

    public static Supervisor mapear(ResultSet rs) throws SQLException {
        Persona persona = new Persona(
            rs.getInt("id_persona"), rs.getString("dni"),
            rs.getString("nombre"),  rs.getString("apellido"),
            rs.getString("correo"),  rs.getString("telefono")
        );
        Usuario usuario = new Usuario(
            rs.getInt("usuario_id_usuario"), rs.getString("nombre_usuario"),
            rs.getString("contrasena"), rs.getInt("status") == 1, persona
        );
        Rol rol = new Rol(rs.getInt("rol_id_rol"), rs.getString("nombre_rol"));
        UsuarioRol ur = new UsuarioRol(rs.getInt("usuario_rol_id_usuario_rol"), usuario, rol);

        Date fecha = rs.getDate("fecha_ascenso");
        return new Supervisor(ur, fecha != null ? fecha.toLocalDate() : null);
    }
}