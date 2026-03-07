package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    private static final String SELECT_JOIN =
        "SELECT r.id_reporte, r.fecha_creacion, r.descripcion, " +
        "       r.usuario_id_usuario_generador, r.tipo_reporte_id_tipo_reporte, r.status_reporte_id_status_reporte, " +
        "       u.nombre_usuario, u.contrasena, u.status AS u_status, " +
        "       p.id_persona, p.dni, p.nombre, p.apellido, p.correo, p.telefono, " +
        "       tr.nombre AS nombre_tipo, " +
        "       sr.nombre AS nombre_status " +
        "FROM reporte r " +
        "JOIN usuario u       ON r.usuario_id_usuario_generador   = u.id_usuario " +
        "JOIN persona p       ON u.persona_id_persona              = p.id_persona " +
        "JOIN tipo_reporte tr ON r.tipo_reporte_id_tipo_reporte    = tr.id_tipo_reporte " +
        "JOIN status_reporte sr ON r.status_reporte_id_status_reporte = sr.id_status_reporte ";

    public int insertar(Reporte r) throws SQLException {
        String sql = "INSERT INTO reporte (fecha_creacion, descripcion, usuario_id_usuario_generador, " +
                     "tipo_reporte_id_tipo_reporte, status_reporte_id_status_reporte) VALUES (?,?,?,?,?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, Timestamp.valueOf(r.getFechaCreacion()));
            ps.setString(2, r.getDescripcion());
            ps.setInt(3, r.getUsuarioGeneradorId());
            ps.setInt(4, r.getTipoReporteId());
            ps.setInt(5, r.getStatusReporteId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : 0; }
        }
    }

    public Reporte buscarPorId(int id) throws SQLException {
        String sql = SELECT_JOIN + "WHERE r.id_reporte = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public List<Reporte> listarTodos() throws SQLException {
        List<Reporte> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "ORDER BY r.fecha_creacion DESC";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Reporte> listarPorUsuario(int idUsuario) throws SQLException {
        List<Reporte> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "WHERE r.usuario_id_usuario_generador = ? ORDER BY r.fecha_creacion DESC";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) lista.add(mapear(rs)); }
        }
        return lista;
    }

    public List<Reporte> listarPorTipo(int idTipo) throws SQLException {
        List<Reporte> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "WHERE r.tipo_reporte_id_tipo_reporte = ? ORDER BY r.fecha_creacion DESC";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idTipo);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) lista.add(mapear(rs)); }
        }
        return lista;
    }

    public List<Reporte> listarPorStatus(int idStatus) throws SQLException {
        List<Reporte> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "WHERE r.status_reporte_id_status_reporte = ? ORDER BY r.fecha_creacion DESC";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idStatus);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) lista.add(mapear(rs)); }
        }
        return lista;
    }

    public boolean actualizar(Reporte r) throws SQLException {
        String sql = "UPDATE reporte SET descripcion=?, usuario_id_usuario_generador=?, " +
                     "tipo_reporte_id_tipo_reporte=?, status_reporte_id_status_reporte=? WHERE id_reporte=?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, r.getDescripcion());
            ps.setInt(2, r.getUsuarioGeneradorId());
            ps.setInt(3, r.getTipoReporteId());
            ps.setInt(4, r.getStatusReporteId());
            ps.setInt(5, r.getIdReporte());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizarStatus(int idReporte, int idStatus) throws SQLException {
        String sql = "UPDATE reporte SET status_reporte_id_status_reporte=? WHERE id_reporte=?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idStatus); ps.setInt(2, idReporte);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM reporte WHERE id_reporte = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        }
    }

    public static Reporte mapear(ResultSet rs) throws SQLException {
        Persona persona = new Persona(
            rs.getInt("id_persona"), rs.getString("dni"),
            rs.getString("nombre"),  rs.getString("apellido"),
            rs.getString("correo"),  rs.getString("telefono")
        );
        Usuario usuario = new Usuario(
            rs.getInt("usuario_id_usuario_generador"),
            rs.getString("nombre_usuario"), rs.getString("contrasena"),
            rs.getInt("u_status") == 1, persona
        );
        TipoReporte   tipo   = new TipoReporte(rs.getInt("tipo_reporte_id_tipo_reporte"),   rs.getString("nombre_tipo"));
        StatusReporte status = new StatusReporte(rs.getInt("status_reporte_id_status_reporte"), rs.getString("nombre_status"));
        return new Reporte(
            rs.getInt("id_reporte"),
            rs.getTimestamp("fecha_creacion").toLocalDateTime(),
            rs.getString("descripcion"),
            usuario, tipo, status
        );
    }
}