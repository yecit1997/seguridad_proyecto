package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.Persona;
import com.seguridadnacional.seguridad.models.Supervisor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupervisorDAO {

    public boolean insertar(Supervisor supervisor) throws SQLException {
        String sql = "INSERT INTO supervisor (persona_id_persona) VALUES (?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, supervisor.getPersonaId());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) supervisor.setIdSupervisor(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public Supervisor buscarPorId(int id) throws SQLException {
        String sql = buildSelectJoin() + " WHERE s.id_supervisor = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Supervisor> listarTodos() throws SQLException {
        List<Supervisor> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(buildSelectJoin());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(Supervisor supervisor) throws SQLException {
        String sql = "UPDATE supervisor SET persona_id_persona=? WHERE id_supervisor=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, supervisor.getPersonaId());
            ps.setInt(2, supervisor.getIdSupervisor());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM supervisor WHERE id_supervisor = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private String buildSelectJoin() {
        return "SELECT s.id_supervisor, " +
               "p.id_persona, p.nombre, p.apellido, p.correo, p.telefono " +
               "FROM supervisor s " +
               "JOIN persona p ON s.persona_id_persona = p.id_persona";
    }

    private Supervisor mapear(ResultSet rs) throws SQLException {
        Persona persona = new Persona(
            rs.getInt("id_persona"), rs.getString("nombre"),
            rs.getString("apellido"), rs.getString("correo"), rs.getString("telefono")
        );
        return new Supervisor(rs.getInt("id_supervisor"), persona);
    }
}