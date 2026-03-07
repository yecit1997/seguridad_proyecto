package com.seguridadnacional.seguridad.dao;

import com.seguridadnacional.seguridad.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {

    private static final String SELECT_JOIN =
        "SELECT a.id_alerta, a.fecha_hora, a.mensaje, a.leida, " +
        "       a.usuario_id_usuario_destinatario, a.reporte_id_reporte, " +
        "       u.nombre_usuario, u.contrasena, u.status AS u_status, " +
        "       p.id_persona, p.dni, p.nombre, p.apellido, p.correo, p.telefono " +
        "FROM alerta a " +
        "JOIN usuario u ON a.usuario_id_usuario_destinatario = u.id_usuario " +
        "JOIN persona p ON u.persona_id_persona = p.id_persona ";

    public int insertar(Alerta a) throws SQLException {
        String sql = "INSERT INTO alerta (fecha_hora, mensaje, leida, usuario_id_usuario_destinatario, reporte_id_reporte) VALUES (?,?,?,?,?)";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, Timestamp.valueOf(a.getFechaHora()));
            ps.setString(2, a.getMensaje());
            ps.setInt(3, a.isLeida() ? 1 : 0);
            ps.setInt(4, a.getUsuarioDestinatarioId());
            if (a.getReporte() != null) ps.setInt(5, a.getReporteId());
            else ps.setNull(5, Types.INTEGER);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : 0; }
        }
    }

    public Alerta buscarPorId(int id) throws SQLException {
        String sql = SELECT_JOIN + "WHERE a.id_alerta = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public List<Alerta> listarPorUsuario(int idUsuario) throws SQLException {
        List<Alerta> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "WHERE a.usuario_id_usuario_destinatario = ? ORDER BY a.fecha_hora DESC";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) lista.add(mapear(rs)); }
        }
        return lista;
    }

    public List<Alerta> listarNoLeidasPorUsuario(int idUsuario) throws SQLException {
        List<Alerta> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "WHERE a.usuario_id_usuario_destinatario = ? AND a.leida = 0 ORDER BY a.fecha_hora DESC";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) lista.add(mapear(rs)); }
        }
        return lista;
    }

    public boolean marcarLeida(int idAlerta) throws SQLException {
        String sql = "UPDATE alerta SET leida = 1 WHERE id_alerta = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idAlerta); return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM alerta WHERE id_alerta = ?";
        try (Connection c = ConexionDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        }
    }

    public static Alerta mapear(ResultSet rs) throws SQLException {
        Persona persona = new Persona(
            rs.getInt("id_persona"), rs.getString("dni"),
            rs.getString("nombre"),  rs.getString("apellido"),
            rs.getString("correo"),  rs.getString("telefono")
        );
        Usuario usuario = new Usuario(
            rs.getInt("usuario_id_usuario_destinatario"),
            rs.getString("nombre_usuario"), rs.getString("contrasena"),
            rs.getInt("u_status") == 1, persona
        );
        // Reporte como referencia ligera
        int repId = rs.getInt("reporte_id_reporte");
        Reporte reporte = null;
        if (!rs.wasNull()) {
            reporte = new Reporte();
            reporte.setIdReporte(repId);
        }
        return new Alerta(
            rs.getInt("id_alerta"),
            rs.getTimestamp("fecha_hora").toLocalDateTime(),
            rs.getString("mensaje"),
            rs.getInt("leida") == 1,
            usuario, reporte
        );
    }
}