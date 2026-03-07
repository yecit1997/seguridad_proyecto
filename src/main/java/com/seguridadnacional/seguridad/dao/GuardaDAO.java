package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuardaDAO {

    private static final String SELECT_JOIN =
        "SELECT g.usuario_rol_id_usuario_rol, g.areaAsignada, g.supervisor_id_supervisor, " +
        "       ur.usuario_id_usuario, ur.rol_id_rol, " +
        "       u.nombre_usuario, u.contrasena, u.status, " +
        "       p.id_persona, p.dni, p.nombre, p.apellido, p.correo, p.telefono, " +
        "       r.nombre AS nombre_rol " +
        "FROM guarda g " +
        "JOIN usuario_rol ur ON g.usuario_rol_id_usuario_rol = ur.id_usuario_rol " +
        "JOIN usuario u      ON ur.usuario_id_usuario = u.id_usuario " +
        "JOIN persona p      ON u.persona_id_persona  = p.id_persona " +
        "JOIN rol r          ON ur.rol_id_rol          = r.id_rol ";

    public boolean insertar(Guarda g) throws SQLException {
        String sql = "INSERT INTO guarda (usuario_rol_id_usuario_rol, areaAsignada, supervisor_id_supervisor) VALUES (?,?,?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, g.getUsuarioRolId());
            ps.setString(2, g.getAreaAsignada());
            if (g.getSupervisor() != null) ps.setInt(3, g.getSupervisorId());
            else ps.setNull(3, Types.INTEGER);
            return ps.executeUpdate() > 0;
        }
    }

    public Guarda buscarPorId(int usuarioRolId) throws SQLException {
        String sql = SELECT_JOIN + "WHERE g.usuario_rol_id_usuario_rol = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioRolId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Guarda> listarTodos() throws SQLException {
        List<Guarda> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "ORDER BY p.apellido, p.nombre";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Guarda> listarPorSupervisor(int supervisorId) throws SQLException {
        List<Guarda> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "WHERE g.supervisor_id_supervisor = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, supervisorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean actualizar(Guarda g) throws SQLException {
        String sql = "UPDATE guarda SET areaAsignada=?, supervisor_id_supervisor=? WHERE usuario_rol_id_usuario_rol=?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, g.getAreaAsignada());
            if (g.getSupervisor() != null) ps.setInt(2, g.getSupervisorId());
            else ps.setNull(2, Types.INTEGER);
            ps.setInt(3, g.getUsuarioRolId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int usuarioRolId) throws SQLException {
        String sql = "DELETE FROM guarda WHERE usuario_rol_id_usuario_rol = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioRolId);
            return ps.executeUpdate() > 0;
        }
    }

    public static Guarda mapear(ResultSet rs) throws SQLException {
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

        // Supervisor se carga como referencia ligera (solo id) para evitar recursión
        int supId = rs.getInt("supervisor_id_supervisor");
        Supervisor supervisor = null;
        if (!rs.wasNull()) {
            UsuarioRol urSup = new UsuarioRol();
            urSup.setIdUsuarioRol(supId);
            supervisor = new Supervisor(urSup, null);
        }

        return new Guarda(ur, rs.getString("areaAsignada"), supervisor);
    }
}