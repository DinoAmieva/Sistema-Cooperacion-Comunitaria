package proyectoparalela.dao;

import proyectoparalela.db.DatabaseConnection;
import proyectoparalela.model.Asamblea;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla 'asambleas'.
 * Sigue el mismo patrón que HabitanteDAO.
 */
public class AsambleaDAO {

    /**
     * Crea un nuevo registro de asamblea
     */
    public long crear(Asamblea a) throws SQLException {
        String sql = "INSERT INTO asambleas(habitante_id, año, trimestre, asistio) VALUES(?,?,?,?)";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setLong(1, a.getHabitanteId());
            ps.setInt(2, a.getAño());
            ps.setInt(3, a.getTrimestre());
            ps.setBoolean(4, a.isAsistio());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        return -1;
    }

    /**
     * Obtiene todos los registros de asamblea para un habitante específico
     */
    public List<Asamblea> listarPorHabitante(long habitanteId) throws SQLException {
        String sql = "SELECT * FROM asambleas WHERE habitante_id = ? ORDER BY año DESC, trimestre DESC";
        List<Asamblea> lista = new ArrayList<>();
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setLong(1, habitanteId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Mapea un ResultSet a un objeto Asamblea
     */
    private Asamblea map(ResultSet rs) throws SQLException {
        Asamblea a = new Asamblea();
        a.setId(rs.getLong("id"));
        a.setHabitanteId(rs.getLong("habitante_id"));
        a.setAño(rs.getInt("año"));
        a.setTrimestre(rs.getInt("trimestre"));
        a.setAsistio(rs.getBoolean("asistio"));
        return a;
    }
    
    // Aquí puedes añadir los métodos leer(id), actualizar(Asamblea a) y eliminar(id)
    // siguiendo el mismo patrón de HabitanteDAO.
}