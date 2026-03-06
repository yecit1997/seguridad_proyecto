package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.Persona;
import com.seguridadnacional.seguridad.models.PersonalAdministrativo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalAdministrativoDAO {

    public boolean insertar(PersonalAdministrativo pa) throws SQLException {
        String sql = "INSERT INTO personal_administrativo (cargo, persona_id_persona) VALUES (?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pa.getCargo());
            ps.setInt(2, pa.getPersonaId());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) pa.setIdPersonalAdministrativo(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public PersonalAdministrativo buscarPorId(int id) throws SQLException {
        String sql = buildSelectJoin() + " WHERE pa.id_personal_administrativo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<PersonalAdministrativo> listarTodos() throws SQLException {
        List<PersonalAdministrativo> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(buildSelectJoin());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(PersonalAdministrativo pa) throws SQLException {
        String sql = "UPDATE personal_administrativo SET cargo=?, persona_id_persona=? " +
                     "WHERE id_personal_administrativo=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pa.getCargo());
            ps.setInt(2, pa.getPersonaId());
            ps.setInt(3, pa.getIdPersonalAdministrativo());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM personal_administrativo WHERE id_personal_administrativo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private String buildSelectJoin() {
        return "SELECT pa.id_personal_administrativo, pa.cargo, " +
               "p.id_persona, p.nombre, p.apellido, p.correo, p.telefono " +
               "FROM personal_administrativo pa " +
               "JOIN persona p ON pa.persona_id_persona = p.id_persona";
    }

    private PersonalAdministrativo mapear(ResultSet rs) throws SQLException {
        Persona persona = new Persona(
            rs.getInt("id_persona"), rs.getString("nombre"),
            rs.getString("apellido"), rs.getString("correo"), rs.getString("telefono")
        );
        return new PersonalAdministrativo(
            rs.getInt("id_personal_administrativo"),
            rs.getString("cargo"),
            persona
        );
    }
}