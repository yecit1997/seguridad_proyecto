package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuardaDAO {

    public boolean insertar(Guarda guarda) throws SQLException {
        String sql = "INSERT INTO guarda (persona_id_persona, areas_id_areas, supervisor_id_supervisor) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, guarda.getPersonaId());
            ps.setInt(2, guarda.getAreasId());
            ps.setInt(3, guarda.getSupervisorId());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) guarda.setIdGuarda(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public Guarda buscarPorId(int id) throws SQLException {
        String sql = buildSelectJoin() + " WHERE g.id_guarda = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Guarda> listarTodos() throws SQLException {
        List<Guarda> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(buildSelectJoin());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Guarda> listarPorSupervisor(int idSupervisor) throws SQLException {
        String sql = buildSelectJoin() + " WHERE g.supervisor_id_supervisor = ?";
        List<Guarda> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSupervisor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean actualizar(Guarda guarda) throws SQLException {
        String sql = "UPDATE guarda SET persona_id_persona=?, areas_id_areas=?, " +
                     "supervisor_id_supervisor=? WHERE id_guarda=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, guarda.getPersonaId());
            ps.setInt(2, guarda.getAreasId());
            ps.setInt(3, guarda.getSupervisorId());
            ps.setInt(4, guarda.getIdGuarda());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM guarda WHERE id_guarda = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private String buildSelectJoin() {
        return "SELECT g.id_guarda, " +
               "p.id_persona, p.nombre, p.apellido, p.correo, p.telefono, " +
               "a.id_areas, a.nombre_area, " +
               "s.id_supervisor, " +
               "sp.id_persona AS sp_id_persona, sp.nombre AS sp_nombre, " +
               "sp.apellido AS sp_apellido, sp.correo AS sp_correo, sp.telefono AS sp_telefono " +
               "FROM guarda g " +
               "JOIN persona p    ON g.persona_id_persona       = p.id_persona " +
               "JOIN areas a      ON g.areas_id_areas           = a.id_areas " +
               "JOIN supervisor s ON g.supervisor_id_supervisor = s.id_supervisor " +
               "JOIN persona sp   ON s.persona_id_persona       = sp.id_persona";
    }

    private Guarda mapear(ResultSet rs) throws SQLException {
        Persona personaGuarda = new Persona(
            rs.getInt("id_persona"), rs.getString("nombre"),
            rs.getString("apellido"), rs.getString("correo"), rs.getString("telefono")
        );
        Areas area = new Areas(rs.getInt("id_areas"), rs.getString("nombre_area"));

        Persona personaSupervisor = new Persona(
            rs.getInt("sp_id_persona"), rs.getString("sp_nombre"),
            rs.getString("sp_apellido"), rs.getString("sp_correo"), rs.getString("sp_telefono")
        );
        Supervisor supervisor = new Supervisor(rs.getInt("id_supervisor"), personaSupervisor);

        return new Guarda(rs.getInt("id_guarda"), personaGuarda, area, supervisor);
    }
}