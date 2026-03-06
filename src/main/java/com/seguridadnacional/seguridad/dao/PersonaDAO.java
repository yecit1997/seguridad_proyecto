package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.Persona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    // ─── INSERT ────────────────────────────────────────────────────────────────
    public boolean insertar(Persona persona) throws SQLException {
        String sql = "INSERT INTO persona (nombre, apellido, correo, telefono) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setString(3, persona.getCorreo());
            ps.setString(4, persona.getTelefono());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) persona.setIdPersona(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    // ─── SELECT BY ID ──────────────────────────────────────────────────────────
    public Persona buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM persona WHERE id_persona = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    // ─── SELECT ALL ────────────────────────────────────────────────────────────
    public List<Persona> listarTodos() throws SQLException {
        String sql = "SELECT * FROM persona";
        List<Persona> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // ─── UPDATE ────────────────────────────────────────────────────────────────
    public boolean actualizar(Persona persona) throws SQLException {
        String sql = "UPDATE persona SET nombre=?, apellido=?, correo=?, telefono=? WHERE id_persona=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setString(3, persona.getCorreo());
            ps.setString(4, persona.getTelefono());
            ps.setInt(5, persona.getIdPersona());

            return ps.executeUpdate() > 0;
        }
    }

    // ─── DELETE ────────────────────────────────────────────────────────────────
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM persona WHERE id_persona = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    private Persona mapear(ResultSet rs) throws SQLException {
        return new Persona(
            rs.getInt("id_persona"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("correo"),
            rs.getString("telefono")
        );
    }
}