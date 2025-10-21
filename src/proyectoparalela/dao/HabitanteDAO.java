
package proyectoparalela.dao;

import proyectoparalela.db.DatabaseConnection;
import proyectoparalela.model.Habitante;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para manejar operaciones de base de datos
 * relacionadas con la entidad Habitante
 * Implementa el patrón DAO para separar la lógica de acceso a datos
 */
public class HabitanteDAO {

    /**
     * Crea un nuevo habitante en la base de datos
     * @param h Objeto Habitante con los datos a insertar
     * @return ID del habitante creado, -1 si hay error
     * @throws SQLException Si hay error en la operación de base de datos
     */
    public long crear(Habitante h) throws SQLException {
        String sql = "INSERT INTO habitantes(nombre, apellido, edad, direccion, telefono, fecha_registro) VALUES(?,?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Establecer parámetros del PreparedStatement
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getApellido());
            // Manejar valores nulos para edad
            if (h.getEdad() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, h.getEdad());
            ps.setString(4, h.getDireccion());
            ps.setString(5, h.getTelefono());
            // Convertir LocalDate a String para almacenar en SQLite
            ps.setString(6, h.getFechaRegistro() == null ? null : h.getFechaRegistro().toString());
            ps.executeUpdate();
            
            // Obtener el ID generado automáticamente
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        return -1;
    }

    /**
     * Lee un habitante específico por su ID
     * @param id ID del habitante a buscar
     * @return Objeto Habitante encontrado, null si no existe
     * @throws SQLException Si hay error en la operación de base de datos
     */
    public Habitante leer(long id) throws SQLException {
        String sql = "SELECT * FROM habitantes WHERE id = ?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    /**
     * Actualiza los datos de un habitante existente
     * @param h Objeto Habitante con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     * @throws SQLException Si hay error en la operación de base de datos
     */
    public boolean actualizar(Habitante h) throws SQLException {
        String sql = "UPDATE habitantes SET nombre=?, apellido=?, edad=?, direccion=?, telefono=?, fecha_registro=? WHERE id=?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            // Establecer parámetros del UPDATE
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getApellido());
            // Manejar valores nulos para edad
            if (h.getEdad() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, h.getEdad());
            ps.setString(4, h.getDireccion());
            ps.setString(5, h.getTelefono());
            ps.setString(6, h.getFechaRegistro() == null ? null : h.getFechaRegistro().toString());
            ps.setLong(7, h.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un habitante de la base de datos
     * @param id ID del habitante a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     * @throws SQLException Si hay error en la operación de base de datos
     */
    public boolean eliminar(long id) throws SQLException {
        String sql = "DELETE FROM habitantes WHERE id = ?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene todos los habitantes ordenados por ID descendente
     * @return Lista de todos los habitantes
     * @throws SQLException Si hay error en la operación de base de datos
     */
    public List<Habitante> listarTodos() throws SQLException {
        String sql = "SELECT * FROM habitantes ORDER BY id DESC";
        List<Habitante> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    /**
     * Busca habitantes por nombre o apellido (búsqueda parcial)
     * @param nombre Nombre o apellido a buscar
     * @return Lista de habitantes que coinciden con la búsqueda
     * @throws SQLException Si hay error en la operación de base de datos
     */
    public List<Habitante> buscarPorNombre(String nombre) throws SQLException {
        String sql = "SELECT * FROM habitantes WHERE nombre LIKE ? OR apellido LIKE ?";
        List<Habitante> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            String like = "%" + nombre + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    /**
     * Mapea un ResultSet a un objeto Habitante
     * @param rs ResultSet con los datos de la base de datos
     * @return Objeto Habitante mapeado
     * @throws SQLException Si hay error al leer los datos
     */
    private Habitante map(ResultSet rs) throws SQLException {
        Habitante h = new Habitante();
        h.setId(rs.getLong("id"));
        h.setNombre(rs.getString("nombre"));
        h.setApellido(rs.getString("apellido"));
        
        // Manejar edad nula
        int edad = rs.getInt("edad");
        if (!rs.wasNull()) h.setEdad(edad);
        
        h.setDireccion(rs.getString("direccion"));
        h.setTelefono(rs.getString("telefono"));
        
        // Convertir String a LocalDate para fecha_registro
        String fr = rs.getString("fecha_registro");
        h.setFechaRegistro(fr == null ? null : LocalDate.parse(fr));
        return h;
    }
}


