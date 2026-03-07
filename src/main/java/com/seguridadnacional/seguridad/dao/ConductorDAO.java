package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.Conductor;
import com.seguridadnacional.seguridad.models.Persona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConductorDAO {

    private static final String SELECT_JOIN =
        "SELECT c.id_fk_persona, c.licencia, " +
        "       p.dni, p.nombre, p.apellido, p.correo, p.telefono " +
        "FROM conductor c " +
        "JOIN persona p ON c.id_fk_persona = p.id_persona ";

    public boolean insertar(Conductor c) throws SQLException {
        String sql = "INSERT INTO conductor (id_fk_persona, licencia) VALUES (?,?)";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getIdFkPersona());
            ps.setString(2, c.getLicencia());
            return ps.executeUpdate() > 0;
        }
    }

    public Conductor buscarPorId(int idFkPersona) throws SQLException {
        String sql = SELECT_JOIN + "WHERE c.id_fk_persona = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idFkPersona);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Conductor> listarTodos() throws SQLException {
        List<Conductor> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "ORDER BY p.apellido, p.nombre";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(Conductor c) throws SQLException {
        String sql = "UPDATE conductor SET licencia=? WHERE id_fk_persona=?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getLicencia());
            ps.setInt(2, c.getIdFkPersona());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idFkPersona) throws SQLException {
        String sql = "DELETE FROM conductor WHERE id_fk_persona = ?";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idFkPersona);
            return ps.executeUpdate() > 0;
        }
    }

    public static Conductor mapear(ResultSet rs) throws SQLException {
        Persona p = new Persona(
            rs.getInt("id_fk_persona"),
            rs.getString("dni"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getString("correo"),
            rs.getString("telefono")
        );
        return new Conductor(p, rs.getString("licencia"));
    }
}