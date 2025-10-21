package proyectoparalela.utils;

import proyectoparalela.dao.*;
import proyectoparalela.threads.*;

/**
 * Gestor de hilos del sistema CooperaTec
 * Maneja la creación, configuración y control de todos los hilos del sistema
 * Implementa el patrón Manager para centralizar el control de hilos
 */
public class ThreadManager {
    // Sistema de notificaciones para comunicación con la vista
    private final Observable<String> notifier;

    // Referencias a los hilos del sistema
    private final Thread t1, t2, t3, t4, t5, t6;

    // Instancias de los hilos para acceso directo
    public final RegistroHabitanteThread registroHabitanteThread;
    public final GeneracionExpedienteThread generacionExpedienteThread;
    public final RegistroMensualThread registroMensualThread;
    public final RegistroFaenasThread registroFaenasThread;
    public final PersistenciaThread persistenciaThread;
    public final SesionThread sesionThread;

    /**
     * Constructor del gestor de hilos
     * @param notifier Sistema de notificaciones
     */
    public ThreadManager(Observable<String> notifier) {
        this.notifier = notifier;

        // Crear DAOs necesarios para los hilos
        HabitanteDAO habitanteDAO = new HabitanteDAO();
        ExpedienteDAO expedienteDAO = new ExpedienteDAO();
        FaenaDAO faenaDAO = new FaenaDAO();
        SesionDAO sesionDAO = new SesionDAO();

        // Crear instancias de los hilos con sus respectivos DAOs
        registroHabitanteThread = new RegistroHabitanteThread(notifier, habitanteDAO);
        generacionExpedienteThread = new GeneracionExpedienteThread(notifier, expedienteDAO);
        registroMensualThread = new RegistroMensualThread(notifier);
        registroFaenasThread = new RegistroFaenasThread(notifier, faenaDAO);
        persistenciaThread = new PersistenciaThread(notifier);
        sesionThread = new SesionThread(notifier, sesionDAO);

        // Crear objetos Thread con nombres descriptivos
        t1 = new Thread(registroHabitanteThread, "RegistroHabitanteThread");
        t2 = new Thread(generacionExpedienteThread, "GeneracionExpedienteThread");
        t3 = new Thread(registroMensualThread, "RegistroMensualThread");
        t4 = new Thread(registroFaenasThread, "RegistroFaenasThread");
        t5 = new Thread(persistenciaThread, "PersistenciaThread");
        t6 = new Thread(sesionThread, "SesionThread");
    }

    /**
     * Inicia todos los hilos del sistema
     * Cada hilo maneja una funcionalidad específica del sistema
     */
    public void startAll() {
        // Iniciar hilo de registro de habitantes (simula registros automáticos)
        t1.start();
        // Iniciar hilo de generación de expedientes (crea expedientes automáticamente)
        t2.start();
        // Iniciar hilo de registro mensual (genera reportes mensuales)
        t3.start();
        // Iniciar hilo de registro de faenas (procesa participaciones)
        t4.start();
        // Iniciar hilo de persistencia (guarda datos en BD)
        t5.start();
        // Iniciar hilo de sesiones (mantiene sesiones activas)
        t6.start();
        
        // Notificar que todos los hilos han sido iniciados
        notifier.publish("Todos los hilos iniciados");
    }
}






