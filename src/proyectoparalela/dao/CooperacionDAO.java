package proyectoparalela.dao;

import proyectoparalela.db.DatabaseConnection;
import proyectoparalela.model.Cooperacion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CooperacionDAO {
    public long crear(Cooperacion cpr) throws SQLException {
        String sql = "INSERT INTO cooperaciones(habitante_id, tipo_cooperacion, descripcion, fecha_cooperacion, puntos) VALUES(?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, cpr.getHabitanteId());
            ps.setString(2, cpr.getTipoCooperacion());
            ps.setString(3, cpr.getDescripcion());
            ps.setString(4, cpr.getFechaCooperacion() == null ? null : cpr.getFechaCooperacion().toString());
            if (cpr.getPuntos() == null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, cpr.getPuntos());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getLong(1); }
        }
        return -1;
    }

    public Cooperacion leer(long id) throws SQLException {
        String sql = "SELECT * FROM cooperaciones WHERE id = ?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    public boolean actualizar(Cooperacion cpr) throws SQLException {
        String sql = "UPDATE cooperaciones SET habitante_id=?, tipo_cooperacion=?, descripcion=?, fecha_cooperacion=?, puntos=? WHERE id=?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, cpr.getHabitanteId());
            ps.setString(2, cpr.getTipoCooperacion());
            ps.setString(3, cpr.getDescripcion());
            ps.setString(4, cpr.getFechaCooperacion() == null ? null : cpr.getFechaCooperacion().toString());
            if (cpr.getPuntos() == null) ps.setNull(5, Types.INTEGER); else ps.setInt(5, cpr.getPuntos());
            ps.setLong(6, cpr.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(long id) throws SQLException {
        String sql = "DELETE FROM cooperaciones WHERE id = ?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) { ps.setLong(1, id); return ps.executeUpdate() > 0; }
    }

    public List<Cooperacion> listarTodos() throws SQLException {
        String sql = "SELECT * FROM cooperaciones ORDER BY id DESC";
        List<Cooperacion> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        return list;
    }

    public List<Cooperacion> buscarPorHabitante(long habitanteId) throws SQLException {
        String sql = "SELECT * FROM cooperaciones WHERE habitante_id = ?";
        List<Cooperacion> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, habitanteId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        }
        return list;
    }

    public List<Cooperacion> buscarPorMes(int año, int mes) throws SQLException {
        String sql = "SELECT * FROM cooperaciones WHERE strftime('%Y', fecha_cooperacion) = ? AND strftime('%m', fecha_cooperacion) = ? ORDER BY fecha_cooperacion DESC";
        List<Cooperacion> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(año));
            ps.setString(2, String.format("%02d", mes));
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        }
        return list;
    }

    public int contarCooperacionesPorMes(int año, int mes) throws SQLException {
        String sql = "SELECT COUNT(*) FROM cooperaciones WHERE strftime('%Y', fecha_cooperacion) = ? AND strftime('%m', fecha_cooperacion) = ?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(año));
            ps.setString(2, String.format("%02d", mes));
            try (ResultSet rs = ps.executeQuery()) { 
                if (rs.next()) return rs.getInt(1); 
            }
        }
        return 0;
    }

    public int sumarPuntosPorMes(int año, int mes) throws SQLException {
        String sql = "SELECT COALESCE(SUM(puntos), 0) FROM cooperaciones WHERE strftime('%Y', fecha_cooperacion) = ? AND strftime('%m', fecha_cooperacion) = ?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(año));
            ps.setString(2, String.format("%02d", mes));
            try (ResultSet rs = ps.executeQuery()) { 
                if (rs.next()) return rs.getInt(1); 
            }
        }
        return 0;
    }

    private Cooperacion map(ResultSet rs) throws SQLException {
        Cooperacion cpr = new Cooperacion();
        cpr.setId(rs.getLong("id"));
        cpr.setHabitanteId(rs.getLong("habitante_id"));
        cpr.setTipoCooperacion(rs.getString("tipo_cooperacion"));
        cpr.setDescripcion(rs.getString("descripcion"));
        String f = rs.getString("fecha_cooperacion");
        cpr.setFechaCooperacion(f == null ? null : LocalDate.parse(f));
        int puntos = rs.getInt("puntos");
        if (!rs.wasNull()) cpr.setPuntos(puntos);
        return cpr;
    }
}


