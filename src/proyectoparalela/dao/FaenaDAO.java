package proyectoparalela.dao;

import proyectoparalela.db.DatabaseConnection;
import proyectoparalela.model.Faena;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FaenaDAO {
    public long crear(Faena f) throws SQLException {
        String sql = "INSERT INTO faenas(habitante_id, tipo_faena, descripcion, fecha_participacion, horas_trabajadas) VALUES(?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, f.getHabitanteId());
            ps.setString(2, f.getTipoFaena());
            ps.setString(3, f.getDescripcion());
            ps.setString(4, f.getFechaParticipacion() == null ? null : f.getFechaParticipacion().toString());
            if (f.getHorasTrabajadas() == null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, f.getHorasTrabajadas());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getLong(1); }
        }
        return -1;
    }

    public Faena leer(long id) throws SQLException {
        String sql = "SELECT * FROM faenas WHERE id = ?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    public boolean actualizar(Faena f) throws SQLException {
        String sql = "UPDATE faenas SET habitante_id=?, tipo_faena=?, descripcion=?, fecha_participacion=?, horas_trabajadas=? WHERE id=?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, f.getHabitanteId());
            ps.setString(2, f.getTipoFaena());
            ps.setString(3, f.getDescripcion());
            ps.setString(4, f.getFechaParticipacion() == null ? null : f.getFechaParticipacion().toString());
            if (f.getHorasTrabajadas() == null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, f.getHorasTrabajadas());
            ps.setLong(6, f.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(long id) throws SQLException {
        String sql = "DELETE FROM faenas WHERE id = ?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) { ps.setLong(1, id); return ps.executeUpdate() > 0; }
    }

    public List<Faena> listarTodos() throws SQLException {
        String sql = "SELECT * FROM faenas ORDER BY id DESC";
        List<Faena> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        return list;
    }

    public List<Faena> buscarPorHabitante(long habitanteId) throws SQLException {
        String sql = "SELECT * FROM faenas WHERE habitante_id = ?";
        List<Faena> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, habitanteId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        }
        return list;
    }

    private Faena map(ResultSet rs) throws SQLException {
        Faena f = new Faena();
        f.setId(rs.getLong("id"));
        f.setHabitanteId(rs.getLong("habitante_id"));
        f.setTipoFaena(rs.getString("tipo_faena"));
        f.setDescripcion(rs.getString("descripcion"));
        String fecha = rs.getString("fecha_participacion");
        f.setFechaParticipacion(fecha == null ? null : LocalDate.parse(fecha));
        int horas = rs.getInt("horas_trabajadas");
        if (!rs.wasNull()) f.setHorasTrabajadas(horas);
        return f;
    }
}


