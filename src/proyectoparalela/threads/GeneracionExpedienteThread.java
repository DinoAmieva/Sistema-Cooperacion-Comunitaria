package proyectoparalela.threads;

import proyectoparalela.dao.ExpedienteDAO;
import proyectoparalela.model.Expediente;
import proyectoparalela.utils.Observable;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Hilo para la generación automática de expedientes
 * Procesa una cola de nuevos habitantes y crea expedientes automáticamente
 * Implementa el patrón Producer-Consumer con BlockingQueue
 */
public class GeneracionExpedienteThread implements Runnable {
    // Sistema de notificaciones para comunicar eventos
    private final Observable<String> notifier;
    // DAO para operaciones de base de datos de expedientes
    private final ExpedienteDAO expedienteDAO;
    // Cola thread-safe para procesar nuevos habitantes
    private final BlockingQueue<Long> nuevosHabitantesQueue = new LinkedBlockingQueue<>();
    // Control de estado del hilo (thread-safe)
    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * Constructor del hilo de generación de expedientes
     * @param notifier Sistema de notificaciones
     * @param expedienteDAO DAO para operaciones de base de datos
     */
    public GeneracionExpedienteThread(Observable<String> notifier, ExpedienteDAO expedienteDAO) {
        this.notifier = notifier;
        this.expedienteDAO = expedienteDAO;
    }

    /**
     * Agrega un nuevo habitante a la cola de procesamiento
     * @param habitanteId ID del habitante para el cual crear expediente
     */
    public void enqueueNuevoHabitante(long habitanteId) { 
        nuevosHabitantesQueue.offer(habitanteId); 
    }
    
    /**
     * Inicia el hilo
     */
    public void start() { 
        running.set(true); 
    }
    
    /**
     * Detiene el hilo
     */
    public void stop() { 
        running.set(false); 
    }

    /**
     * Método principal del hilo
     * Procesa la cola de nuevos habitantes y crea expedientes automáticamente
     */
    @Override
    public void run() {
        start();
        notifier.publish("Hilo GeneracionExpediente iniciado");
        
        // Bucle principal del hilo
        while (running.get()) {
            try {
                // Esperar a que haya un nuevo habitante en la cola
                // take() bloquea hasta que haya un elemento disponible
                Long habitanteId = nuevosHabitantesQueue.take();
                
                // Crear expediente base para COPACI/CAP
                // En un sistema real, aquí se definirían diferentes tipos de expedientes
                Expediente e = new Expediente(null, habitanteId, "COPACI-CAP", "Expediente inicial", LocalDateTime.now(), "ACTIVO");
                
                try {
                    // Crear el expediente en la base de datos
                    long id = expedienteDAO.crear(e);
                    notifier.publish("Expediente creado id=" + id + " para habitante=" + habitanteId);
                } catch (Exception ex) {
                    // Manejar errores de base de datos
                    notifier.publish("Error creando expediente: " + ex.getMessage());
                }
            } catch (InterruptedException e) {
                // Manejar interrupción del hilo
                Thread.currentThread().interrupt();
                notifier.publish("Hilo GeneracionExpediente interrumpido");
            }
        }
        notifier.publish("Hilo GeneracionExpediente detenido");
    }
}




