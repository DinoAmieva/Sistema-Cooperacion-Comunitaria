package proyectoparalela.dao;

import proyectoparalela.db.DatabaseConnection;
import proyectoparalela.model.Sesion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SesionDAO {
    public long crear(Sesion s) throws SQLException {
        String sql = "INSERT INTO sesiones(usuario, token, inicio, ultimo_acceso, activa) VALUES(?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getUsuario());
            ps.setString(2, s.getToken());
            ps.setString(3, s.getInicio() == null ? null : s.getInicio().toString());
            ps.setString(4, s.getUltimoAcceso() == null ? null : s.getUltimoAcceso().toString());
            ps.setInt(5, s.isActiva() ? 1 : 0);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getLong(1); }
        }
        return -1;
    }

    public boolean actualizar(Sesion s) throws SQLException {
        String sql = "UPDATE sesiones SET usuario=?, token=?, inicio=?, ultimo_acceso=?, activa=? WHERE id=?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getUsuario());
            ps.setString(2, s.getToken());
            ps.setString(3, s.getInicio() == null ? null : s.getInicio().toString());
            ps.setString(4, s.getUltimoAcceso() == null ? null : s.getUltimoAcceso().toString());
            ps.setInt(5, s.isActiva() ? 1 : 0);
            ps.setLong(6, s.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public Sesion leer(long id) throws SQLException {
        String sql = "SELECT * FROM sesiones WHERE id=?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return map(rs); }
        }
        return null;
    }

    public List<Sesion> listarActivas() throws SQLException {
        String sql = "SELECT * FROM sesiones WHERE activa = 1";
        List<Sesion> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        return list;
    }

    private Sesion map(ResultSet rs) throws SQLException {
        Sesion s = new Sesion();
        s.setId(rs.getLong("id"));
        s.setUsuario(rs.getString("usuario"));
        s.setToken(rs.getString("token"));
        String ini = rs.getString("inicio");
        s.setInicio(ini == null ? null : LocalDateTime.parse(ini));
        String ult = rs.getString("ultimo_acceso");
        s.setUltimoAcceso(ult == null ? null : LocalDateTime.parse(ult));
        s.setActiva(rs.getInt("activa") == 1);
        return s;
    }
}


