package proyectoparalela;

import javax.swing.SwingUtilities;
import proyectoparalela.controller.*;
import proyectoparalela.dao.*;
import proyectoparalela.db.DatabaseInitializer;
import proyectoparalela.db.DatabaseConnection;
import proyectoparalela.utils.Observable;
import proyectoparalela.utils.ThreadManager;
import proyectoparalela.view.MainWindow;
import proyectoparalela.view.LoginWindow;

/**
 * Clase principal del sistema CooperaTec
 * Maneja la inicialización del sistema y el flujo de autenticación
 */
public class Main {

    /**
     * Método principal que inicia la aplicación
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Verificar que el driver SQLite esté disponible
        if (!DatabaseConnection.isDriverAvailable()) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "No se encontró el driver SQLite (org.sqlite.JDBC).\n" +
                    "Asegúrate de tener el JAR sqlite-jdbc en Libraries.",
                    "Error de configuración", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Inicializar la base de datos
        DatabaseInitializer.initialize();

        // Ejecutar la interfaz en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            // Mostrar ventana de login primero
            LoginWindow loginWindow = new LoginWindow(new LoginWindow.LoginCallback() {
                @Override
                public void onLoginSuccess() {
                    // Inicializar sistema después del login exitoso
                    initializeSystem();
                }
                
                @Override
                public void onLoginFailure(String message) {
                    // El usuario puede intentar de nuevo
                }
            });
            loginWindow.showLogin();
        });
    }
    
    /**
     * Inicializa el sistema principal después del login exitoso
     * Crea todos los DAOs, controladores y la ventana principal
     */
    private static void initializeSystem() {
        // Sistema de notificaciones para comunicación entre componentes
        Observable<String> notifier = new Observable<>();

        // Crear objetos de acceso a datos (DAOs)
        HabitanteDAO habitanteDAO = new HabitanteDAO();
        ExpedienteDAO expedienteDAO = new ExpedienteDAO();
        CooperacionDAO cooperacionDAO = new CooperacionDAO();
        FaenaDAO faenaDAO = new FaenaDAO();
        SesionDAO sesionDAO = new SesionDAO();

        // Crear controladores que manejan la lógica de negocio
        HabitanteController habitanteController = new HabitanteController(habitanteDAO, notifier);
        ExpedienteController expedienteController = new ExpedienteController(expedienteDAO, notifier);
        CooperacionController cooperacionController = new CooperacionController(cooperacionDAO, notifier);
        FaenaController faenaController = new FaenaController(faenaDAO, notifier);

        // Gestor de hilos del sistema
        ThreadManager threadManager = new ThreadManager(notifier);

        // Crear y mostrar la ventana principal
        MainWindow window = new MainWindow(
                notifier,
                habitanteController,
                expedienteController,
                cooperacionController,
                faenaController,
                threadManager
        );
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Iniciar todos los hilos del sistema automáticamente
        threadManager.startAll();
    }
}


