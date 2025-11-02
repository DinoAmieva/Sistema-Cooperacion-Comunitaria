package proyectoparalela.dao;

import proyectoparalela.db.DatabaseConnection;
import proyectoparalela.model.Habitante;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitanteDAO {

    /**
     * Crea un nuevo habitante en la base de datos con todos los campos.
     */
    public long crear(Habitante h) throws SQLException {
        // SQL actualizado con TODOS los campos de la tabla
        String sql = "INSERT INTO habitantes(nombre, apellido, edad, direccion, telefono, fecha_registro, " +
                     "fecha_nacimiento, lugar_nacimiento, estado_civil, ocupacion, grado_estudios, " +
                     "manzana, originario_almaya, certificado_comunero, tipo_certificado, " +
                     "usuario, password) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Establecer parámetros existentes
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getApellido());
            if (h.getEdad() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, h.getEdad());
            ps.setString(4, h.getDireccion());
            ps.setString(5, h.getTelefono());
            ps.setString(6, h.getFechaRegistro() == null ? null : h.getFechaRegistro().toString());

            // Establecer parámetros NUEVOS
            ps.setString(7, h.getFechaNacimiento() == null ? null : h.getFechaNacimiento().toString());
            ps.setString(8, h.getLugarNacimiento());
            ps.setString(9, h.getEstadoCivil());
            ps.setString(10, h.getOcupacion());
            ps.setString(11, h.getGradoEstudios());
            ps.setString(12, h.getManzana());
            ps.setBoolean(13, h.isOriginarioAlmaya());
            ps.setString(14, h.getCertificadoComunero());
            ps.setString(15, h.getTipoCertificado());
            ps.setString(16, h.getUsuario());
            ps.setString(17, h.getPassword()); // ¡¡Ver nota de seguridad al final!!

            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        return -1;
    }

    /**
     * Lee un habitante específico por su ID
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
     */
    public boolean actualizar(Habitante h) throws SQLException {
        // SQL actualizado con TODOS los campos
        String sql = "UPDATE habitantes SET nombre=?, apellido=?, edad=?, direccion=?, telefono=?, fecha_registro=?, " +
                     "fecha_nacimiento=?, lugar_nacimiento=?, estado_civil=?, ocupacion=?, grado_estudios=?, " +
                     "manzana=?, originario_almaya=?, certificado_comunero=?, tipo_certificado=?, " +
                     "usuario=?, password=? WHERE id=?";
        
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            // Establecer parámetros (17 campos + 1 id)
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getApellido());
            if (h.getEdad() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, h.getEdad());
            ps.setString(4, h.getDireccion());
            ps.setString(5, h.getTelefono());
            ps.setString(6, h.getFechaRegistro() == null ? null : h.getFechaRegistro().toString());
            ps.setString(7, h.getFechaNacimiento() == null ? null : h.getFechaNacimiento().toString());
            ps.setString(8, h.getLugarNacimiento());
            ps.setString(9, h.getEstadoCivil());
            ps.setString(10, h.getOcupacion());
            ps.setString(11, h.getGradoEstudios());
            ps.setString(12, h.getManzana());
            ps.setBoolean(13, h.isOriginarioAlmaya());
            ps.setString(14, h.getCertificadoComunero());
            ps.setString(15, h.getTipoCertificado());
            ps.setString(16, h.getUsuario());
            ps.setString(17, h.getPassword());
            ps.setLong(18, h.getId()); // ID al final para el WHERE

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un habitante de la base de datos
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
     * NUEVO: Método de Login
     * Busca un habitante por usuario y contraseña.
     */
    public Habitante login(String usuario, String password) throws SQLException {
        String sql = "SELECT * FROM habitantes WHERE usuario = ?";
        try (Connection c = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Habitante h = map(rs);

                    // ¡¡¡ADVERTENCIA DE SEGURIDAD!!!
                    // Esto es solo para pruebas. NUNCA almacenes contraseñas en texto plano.
                    // En una aplicación real, deberías hashear la contraseña al crearla
                    // y usar una biblioteca como jBCrypt para comparar el hash:
                    // if (BCrypt.checkpw(password, h.getPassword())) { ... }
                    
                    if (h.getPassword() != null && h.getPassword().equals(password)) {
                        return h;
                    }
                }
            }
        }
        return null; // Devuelve null si el usuario no existe o la contraseña no coincide
    }

    /**
     * Mapea un ResultSet a un objeto Habitante (ACTUALIZADO)
     */
    private Habitante map(ResultSet rs) throws SQLException {
        Habitante h = new Habitante();
        
        // Campos existentes
        h.setId(rs.getLong("id"));
        h.setNombre(rs.getString("nombre"));
        h.setApellido(rs.getString("apellido"));
        int edad = rs.getInt("edad");
        if (!rs.wasNull()) h.setEdad(edad);
        h.setDireccion(rs.getString("direccion"));
        h.setTelefono(rs.getString("telefono"));
        String fr = rs.getString("fecha_registro");
        h.setFechaRegistro(fr == null ? null : LocalDate.parse(fr));

        // Campos NUEVOS
        String fn = rs.getString("fecha_nacimiento");
        h.setFechaNacimiento(fn == null ? null : LocalDate.parse(fn));
        h.setLugarNacimiento(rs.getString("lugar_nacimiento"));
        h.setEstadoCivil(rs.getString("estado_civil"));
        h.setOcupacion(rs.getString("ocupacion"));
        h.setGradoEstudios(rs.getString("grado_estudios"));
        h.setManzana(rs.getString("manzana"));
        h.setOriginarioAlmaya(rs.getBoolean("originario_almaya"));
        h.setCertificadoComunero(rs.getString("certificado_comunero"));
        h.setTipoCertificado(rs.getString("tipo_certificado"));
        h.setUsuario(rs.getString("usuario"));
        h.setPassword(rs.getString("password"));
        
        return h;
    }
}