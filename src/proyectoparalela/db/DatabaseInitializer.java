package proyectoparalela.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate("PRAGMA foreign_keys = ON");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS habitantes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "apellido TEXT NOT NULL, " +
                    "edad INTEGER, " +
                    "direccion TEXT, " +
                    "telefono TEXT, " +
                    "fecha_registro TEXT" +
                    ")");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS expedientes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "habitante_id INTEGER NOT NULL, " +
                    "tipo_expediente TEXT NOT NULL, " +
                    "descripcion TEXT, " +
                    "fecha_creacion TEXT, " +
                    "estado TEXT, " +
                    "FOREIGN KEY(habitante_id) REFERENCES habitantes(id) ON DELETE CASCADE" +
                    ")");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS cooperaciones (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "habitante_id INTEGER NOT NULL, " +
                    "tipo_cooperacion TEXT NOT NULL, " +
                    "descripcion TEXT, " +
                    "fecha_cooperacion TEXT, " +
                    "puntos INTEGER, " +
                    "FOREIGN KEY(habitante_id) REFERENCES habitantes(id) ON DELETE CASCADE" +
                    ")");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS faenas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "habitante_id INTEGER NOT NULL, " +
                    "tipo_faena TEXT NOT NULL, " +
                    "descripcion TEXT, " +
                    "fecha_participacion TEXT, " +
                    "horas_trabajadas INTEGER, " +
                    "FOREIGN KEY(habitante_id) REFERENCES habitantes(id) ON DELETE CASCADE" +
                    ")");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS sesiones (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "usuario TEXT NOT NULL, " +
                    "token TEXT NOT NULL, " +
                    "inicio TEXT, " +
                    "ultimo_acceso TEXT, " +
                    "activa INTEGER NOT NULL" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


