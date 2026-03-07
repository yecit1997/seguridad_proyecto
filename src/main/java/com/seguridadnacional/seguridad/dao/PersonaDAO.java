package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.Persona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    // ── INSERT ────────────────────────────────────────────────────────────────
    public int insertar(Persona p) throws SQLException {
        String sql = "INSERT INTO persona (dni, nombre, apellido, correo, telefono) VALUES (?,?,?,?,?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellido());
            ps.setString(4, p.getCorreo());
            ps.setString(5, p.getTelefono());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    // ── SELECT BY ID ──────────────────────────────────────────────────────────
    public Persona buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM persona WHERE id_persona = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    // ── SELECT BY DNI ─────────────────────────────────────────────────────────
    public Persona buscarPorDni(String dni) throws SQLException {
        String sql = "SELECT * FROM persona WHERE dni = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    // ── SELECT ALL ────────────────────────────────────────────────────────────
    public List<Persona> listarTodos() throws SQLException {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM persona ORDER BY apellido, nombre";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    public boolean actualizar(Persona p) throws SQLException {
        String sql = "UPDATE persona SET dni=?, nombre=?, apellido=?, correo=?, telefono=? WHERE id_persona=?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellido());
            ps.setString(4, p.getCorreo());
            ps.setString(5, p.getTelefono());
            ps.setInt(6, p.getIdPersona());
            return ps.executeUpdate() > 0;
        }
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM persona WHERE id_persona = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ── MAPPER ────────────────────────────────────────────────────────────────
    public static Persona mapear(ResultSet rs) throws SQLException {
        return new Persona(
            rs.getInt("id_persona"),
            rs.getString("dni"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("correo"),
            rs.getString("telefono")
        );
    }
}