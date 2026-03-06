package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.Conductor;
import com.seguridadnacional.seguridad.models.Persona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConductorDAO {

    public boolean insertar(Conductor conductor) throws SQLException {
        String sql = "INSERT INTO conductor (licencia, persona_id_persona) VALUES (?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, conductor.getLicencia());
            ps.setInt(2, conductor.getPersonaId());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) conductor.setIdConductor(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public Conductor buscarPorId(int id) throws SQLException {
        String sql = buildSelectJoin() + " WHERE c.id_conductor = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Conductor> listarTodos() throws SQLException {
        List<Conductor> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(buildSelectJoin());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(Conductor conductor) throws SQLException {
        String sql = "UPDATE conductor SET licencia=?, persona_id_persona=? WHERE id_conductor=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, conductor.getLicencia());
            ps.setInt(2, conductor.getPersonaId());
            ps.setInt(3, conductor.getIdConductor());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM conductor WHERE id_conductor = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private String buildSelectJoin() {
        return "SELECT c.id_conductor, c.licencia, " +
               "p.id_persona, p.nombre, p.apellido, p.correo, p.telefono " +
               "FROM conductor c " +
               "JOIN persona p ON c.persona_id_persona = p.id_persona";
    }

    private Conductor mapear(ResultSet rs) throws SQLException {
        Persona persona = new Persona(
            rs.getInt("id_persona"), rs.getString("nombre"),
            rs.getString("apellido"), rs.getString("correo"), rs.getString("telefono")
        );
        return new Conductor(rs.getInt("id_conductor"), rs.getString("licencia"), persona);
    }
}