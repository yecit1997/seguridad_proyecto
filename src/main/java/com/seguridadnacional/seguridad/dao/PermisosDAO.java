package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.Permisos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermisosDAO {

    public boolean insertar(Permisos permisos) throws SQLException {
        String sql = "INSERT INTO permisos (nombre_permiso) VALUES (?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, permisos.getNombrePermiso());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) permisos.setIdPermisos(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public Permisos buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM permisos WHERE id_permisos = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Permisos> listarTodos() throws SQLException {
        String sql = "SELECT * FROM permisos";
        List<Permisos> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean actualizar(Permisos permisos) throws SQLException {
        String sql = "UPDATE permisos SET nombre_permiso=? WHERE id_permisos=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, permisos.getNombrePermiso());
            ps.setInt(2, permisos.getIdPermisos());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM permisos WHERE id_permisos = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Permisos mapear(ResultSet rs) throws SQLException {
        return new Permisos(rs.getInt("id_permisos"), rs.getString("nombre_permiso"));
    }
}