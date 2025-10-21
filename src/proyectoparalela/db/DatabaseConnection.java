package proyectoparalela.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase singleton para manejar la conexión a la base de datos SQLite
 * Implementa el patrón Singleton para asegurar una única instancia de conexión
 */
public class DatabaseConnection {
    // Instancia única de la conexión (Singleton)
    private static volatile DatabaseConnection instance;
    // URL de conexión a la base de datos
    private final String url;
    // Propiedades de conexión
    private final Properties props;

    /**
     * Constructor privado para implementar Singleton
     * Configura la ruta de la base de datos y las propiedades de conexión
     */
    private DatabaseConnection() {
        // Obtener el directorio base del proyecto
        String baseDir = System.getProperty("user.dir");
        // Crear ruta hacia la carpeta 'data'
        java.nio.file.Path dataDir = java.nio.file.Path.of(baseDir, "data");
        try { 
            // Crear la carpeta 'data' si no existe
            java.nio.file.Files.createDirectories(dataDir); 
        } catch (Exception ignore) {}
        
        // Construir la ruta completa a la base de datos
        String dbPath = dataDir.resolve("cooperacion.db").toAbsolutePath().toString();
        this.url = "jdbc:sqlite:" + dbPath;
        this.props = new Properties();
        
        // Cargar el driver SQLite
        try { 
            Class.forName("org.sqlite.JDBC"); 
        } catch (ClassNotFoundException ignore) {}
    }

    /**
     * Obtiene la instancia única de DatabaseConnection (Singleton)
     * @return Instancia de DatabaseConnection
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) instance = new DatabaseConnection();
            }
        }
        return instance;
    }

    /**
     * Obtiene una nueva conexión a la base de datos
     * @return Conexión SQLite
     * @throws SQLException Si hay error al conectar
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, props);
    }

    /**
     * Verifica si el driver SQLite está disponible
     * @return true si el driver está disponible, false en caso contrario
     */
    public static boolean isDriverAvailable() {
        try { 
            Class.forName("org.sqlite.JDBC"); 
            return true; 
        } catch (ClassNotFoundException e) { 
            return false; 
        }
    }
}


