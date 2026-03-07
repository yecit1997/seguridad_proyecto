package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalAdministrativoDAO {

    private static final String SELECT_JOIN =
        "SELECT pa.usuario_rol_id_usuario_rol, pa.cargo, " +
        "       ur.usuario_id_usuario, ur.rol_id_rol, " +
        "       u.nombre_usuario, u.contrasena, u.status, " +
        "       p.id_persona, p.dni, p.nombre, p.apellido, p.correo, p.telefono, " +
        "       r.nombre AS nombre_rol " +
        "FROM personal_administrativo pa " +
        "JOIN usuario_rol ur ON pa.usuario_rol_id_usuario_rol = ur.id_usuario_rol " +
        "JOIN usuario u      ON ur.usuario_id_usuario = u.id_usuario " +
        "JOIN persona p      ON u.persona_id_persona  = p.id_persona " +
        "JOIN rol r          ON ur.rol_id_rol          = r.id_rol ";

    public boolean insertar(PersonalAdministrativo pa) throws SQLException {
        String sql = "INSERT INTO personal_administrativo (usuario_rol_id_usuario_rol, cargo) VALUES (?,?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pa.getUsuarioRolId());
            ps.setString(2, pa.getCargo());
            return ps.executeUpdate() > 0;
        }
    }

    public PersonalAdministrativo buscarPorId(int usuarioRolId) throws SQLException {
        String sql = SELECT_JOIN + "WHERE pa.usuario_rol_id_usuario_rol = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioRolId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<PersonalAdministrativo> listarTodos() throws SQLException {
        List<PersonalAdministrativo> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "ORDER BY p.apellido, p.nombre";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(PersonalAdministrativo pa) throws SQLException {
        String sql = "UPDATE personal_administrativo SET cargo=? WHERE usuario_rol_id_usuario_rol=?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, pa.getCargo());
            ps.setInt(2, pa.getUsuarioRolId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int usuarioRolId) throws SQLException {
        String sql = "DELETE FROM personal_administrativo WHERE usuario_rol_id_usuario_rol = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioRolId);
            return ps.executeUpdate() > 0;
        }
    }

    public static PersonalAdministrativo mapear(ResultSet rs) throws SQLException {
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
        return new PersonalAdministrativo(ur, rs.getString("cargo"));
    }
}