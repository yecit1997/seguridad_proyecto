package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    public boolean insertar(Vehiculo vehiculo) throws SQLException {
        String sql = "INSERT INTO vehiculo (id_vehiculo, placa, conductor_id_conductor) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, vehiculo.getIdVehiculo());
            ps.setString(2, vehiculo.getPlaca());
            ps.setInt(3, vehiculo.getConductorId());
            return ps.executeUpdate() > 0;
        }
    }

    public Vehiculo buscarPorId(int id) throws SQLException {
        String sql = buildSelectJoin() + " WHERE v.id_vehiculo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public Vehiculo buscarPorPlaca(String placa) throws SQLException {
        String sql = buildSelectJoin() + " WHERE v.placa = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Vehiculo> listarTodos() throws SQLException {
        List<Vehiculo> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(buildSelectJoin());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(Vehiculo vehiculo) throws SQLException {
        String sql = "UPDATE vehiculo SET placa=?, conductor_id_conductor=? WHERE id_vehiculo=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vehiculo.getPlaca());
            ps.setInt(2, vehiculo.getConductorId());
            ps.setInt(3, vehiculo.getIdVehiculo());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private String buildSelectJoin() {
        return "SELECT v.id_vehiculo, v.placa, " +
               "c.id_conductor, c.licencia, " +
               "p.id_persona, p.nombre, p.apellido, p.correo, p.telefono " +
               "FROM vehiculo v " +
               "JOIN conductor c ON v.conductor_id_conductor = c.id_conductor " +
               "JOIN persona p   ON c.persona_id_persona     = p.id_persona";
    }

    private Vehiculo mapear(ResultSet rs) throws SQLException {
        Persona persona = new Persona(
            rs.getInt("id_persona"), rs.getString("nombre"),
            rs.getString("apellido"), rs.getString("correo"), rs.getString("telefono")
        );
        Conductor conductor = new Conductor(rs.getInt("id_conductor"), rs.getString("licencia"), persona);
        return new Vehiculo(rs.getInt("id_vehiculo"), rs.getString("placa"), conductor);
    }
}