package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    public boolean insertar(Reporte reporte) throws SQLException {
        String sql = "INSERT INTO reporte (descripcion, usuario_id_usuario, " +
                     "tipo_reporte_id_tipo_reporte, status_reporte_id_status_reporte) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, reporte.getDescripcion());
            ps.setInt(2, reporte.getUsuarioId());
            ps.setInt(3, reporte.getTipoReporteId());
            ps.setInt(4, reporte.getStatusReporteId());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) reporte.setIdReporte(rs.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public Reporte buscarPorId(int id) throws SQLException {
        String sql = buildSelectJoin() + " WHERE r.id_reporte = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Reporte> listarTodos() throws SQLException {
        List<Reporte> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(buildSelectJoin());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Reporte> listarPorUsuario(int idUsuario) throws SQLException {
        String sql = buildSelectJoin() + " WHERE r.usuario_id_usuario = ?";
        List<Reporte> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<Reporte> listarPorTipo(int idTipo) throws SQLException {
        String sql = buildSelectJoin() + " WHERE r.tipo_reporte_id_tipo_reporte = ?";
        List<Reporte> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTipo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<Reporte> listarPorStatus(int idStatus) throws SQLException {
        String sql = buildSelectJoin() + " WHERE r.status_reporte_id_status_reporte = ?";
        List<Reporte> lista = new ArrayList<>();
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idStatus);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean actualizarStatus(int idReporte, int idStatus) throws SQLException {
        String sql = "UPDATE reporte SET status_reporte_id_status_reporte=? WHERE id_reporte=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idStatus);
            ps.setInt(2, idReporte);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Reporte reporte) throws SQLException {
        String sql = "UPDATE reporte SET descripcion=?, usuario_id_usuario=?, " +
                     "tipo_reporte_id_tipo_reporte=?, status_reporte_id_status_reporte=? " +
                     "WHERE id_reporte=?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reporte.getDescripcion());
            ps.setInt(2, reporte.getUsuarioId());
            ps.setInt(3, reporte.getTipoReporteId());
            ps.setInt(4, reporte.getStatusReporteId());
            ps.setInt(5, reporte.getIdReporte());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM reporte WHERE id_reporte = ?";
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private String buildSelectJoin() {
        return "SELECT r.id_reporte, r.descripcion, " +
               "u.id_usuario, u.nombre_usuario, u.contrasena, " +
               "su.id_status_usuario, su.status AS status_usuario, " +
               "ru.id_rol_usuario, ru.nombre_rol_usuario, " +
               "p.id_permisos, p.nombre_permiso, " +
               "tr.id_tipo_reporte, tr.tipo, " +
               "sr.id_status_reporte, sr.status AS status_reporte " +
               "FROM reporte r " +
               "JOIN usuario u        ON r.usuario_id_usuario               = u.id_usuario " +
               "JOIN status_usuario su ON u.status_usuario_id_status_usuario = su.id_status_usuario " +
               "JOIN rol_usuario ru    ON u.rol_usuario_id_rol_usuario        = ru.id_rol_usuario " +
               "JOIN permisos p        ON ru.permisos_id_permisos             = p.id_permisos " +
               "JOIN tipo_reporte tr   ON r.tipo_reporte_id_tipo_reporte      = tr.id_tipo_reporte " +
               "JOIN status_reporte sr ON r.status_reporte_id_status_reporte  = sr.id_status_reporte";
    }

    private Reporte mapear(ResultSet rs) throws SQLException {
        Permisos permisos    = new Permisos(rs.getInt("id_permisos"), rs.getString("nombre_permiso"));
        RolUsuario rol       = new RolUsuario(rs.getInt("id_rol_usuario"), rs.getString("nombre_rol_usuario"), permisos);
        StatusUsuario statusU = new StatusUsuario(rs.getInt("id_status_usuario"), rs.getString("status_usuario"));
        Usuario usuario      = new Usuario(rs.getInt("id_usuario"), rs.getString("nombre_usuario"), rs.getString("contrasena"), statusU, rol);
        TipoReporte tipo     = new TipoReporte(rs.getInt("id_tipo_reporte"), rs.getString("tipo"));
        StatusReporte statusR = new StatusReporte(rs.getInt("id_status_reporte"), rs.getString("status_reporte"));
        return new Reporte(rs.getInt("id_reporte"), rs.getString("descripcion"), usuario, tipo, statusR);
    }
}