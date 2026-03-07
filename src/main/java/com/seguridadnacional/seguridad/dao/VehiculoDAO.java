package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    private static final String SELECT_JOIN =
        "SELECT v.id_vehiculo, v.placa, v.conductor_id_fk_persona, " +
        "       c.licencia, " +
        "       p.id_persona, p.dni, p.nombre, p.apellido, p.correo, p.telefono " +
        "FROM vehiculo v " +
        "LEFT JOIN conductor c ON v.conductor_id_fk_persona = c.id_fk_persona " +
        "LEFT JOIN persona p   ON c.id_fk_persona = p.id_persona ";

    public int insertar(Vehiculo v) throws SQLException {
        String sql = "INSERT INTO vehiculo (placa, conductor_id_fk_persona) VALUES (?,?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getPlaca());
            if (v.getConductor() != null) ps.setInt(2, v.getConductorIdFkPersona());
            else ps.setNull(2, Types.INTEGER);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : 0; }
        }
    }

    public Vehiculo buscarPorId(int id) throws SQLException {
        String sql = SELECT_JOIN + "WHERE v.id_vehiculo = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public Vehiculo buscarPorPlaca(String placa) throws SQLException {
        String sql = SELECT_JOIN + "WHERE v.placa = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public List<Vehiculo> listarTodos() throws SQLException {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "ORDER BY v.placa";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(Vehiculo v) throws SQLException {
        String sql = "UPDATE vehiculo SET placa=?, conductor_id_fk_persona=? WHERE id_vehiculo=?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, v.getPlaca());
            if (v.getConductor() != null) ps.setInt(2, v.getConductorIdFkPersona());
            else ps.setNull(2, Types.INTEGER);
            ps.setInt(3, v.getIdVehiculo());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        }
    }

    public static Vehiculo mapear(ResultSet rs) throws SQLException {
        Conductor conductor = null;
        int condId = rs.getInt("conductor_id_fk_persona");
        if (!rs.wasNull()) {
            Persona p = new Persona(
                rs.getInt("id_persona"), rs.getString("dni"),
                rs.getString("nombre"),  rs.getString("apellido"),
                rs.getString("correo"),  rs.getString("telefono")
            );
            conductor = new Conductor(p, rs.getString("licencia"));
        }
        return new Vehiculo(rs.getInt("id_vehiculo"), rs.getString("placa"), conductor);
    }
}