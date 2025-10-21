package proyectoparalela.dao;

import proyectoparalela.db.DatabaseConnection;
import proyectoparalela.model.Expediente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpedienteDAO {
    public long crear(Expediente e) throws SQLException {
        String sql = "INSERT INTO expedientes(habitante_id, tipo_expediente, descripcion, fecha_creacion, estado) VALUES(?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, e.getHabitanteId());
            ps.setString(2, e.getTipoExpediente());
            ps.setString(3, e.getDescripcion());
            ps.setString(4, e.getFechaCreacion() == null ? null : e.getFechaCreacion().toString());
            ps.setString(5, e.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getLong(1); }
        }
        return -1;
    }

    public Expediente leer(long id) throws SQLException {
        String sql = "SELECT * FROM expedientes WHERE id = ?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    public boolean actualizar(Expediente e) throws SQLException {
        String sql = "UPDATE expedientes SET habitante_id=?, tipo_expediente=?, descripcion=?, fecha_creacion=?, estado=? WHERE id=?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, e.getHabitanteId());
            ps.setString(2, e.getTipoExpediente());
            ps.setString(3, e.getDescripcion());
            ps.setString(4, e.getFechaCreacion() == null ? null : e.getFechaCreacion().toString());
            ps.setString(5, e.getEstado());
            ps.setLong(6, e.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(long id) throws SQLException {
        String sql = "DELETE FROM expedientes WHERE id = ?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) { ps.setLong(1, id); return ps.executeUpdate() > 0; }
    }

    public List<Expediente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM expedientes ORDER BY id DESC";
        List<Expediente> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        return list;
    }

    public List<Expediente> buscarPorHabitante(long habitanteId) throws SQLException {
        String sql = "SELECT * FROM expedientes WHERE habitante_id = ?";
        List<Expediente> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, habitanteId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        }
        return list;
    }

    private Expediente map(ResultSet rs) throws SQLException {
        Expediente e = new Expediente();
        e.setId(rs.getLong("id"));
        e.setHabitanteId(rs.getLong("habitante_id"));
        e.setTipoExpediente(rs.getString("tipo_expediente"));
        e.setDescripcion(rs.getString("descripcion"));
        String fc = rs.getString("fecha_creacion");
        e.setFechaCreacion(fc == null ? null : java.time.LocalDateTime.parse(fc));
        e.setEstado(rs.getString("estado"));
        return e;
    }
}


