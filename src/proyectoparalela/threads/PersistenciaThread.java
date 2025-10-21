package proyectoparalela.threads;

import proyectoparalela.utils.Observable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Hilo para operaciones de persistencia asíncronas
 * Procesa una cola de operaciones de base de datos
 * Implementa el patrón Producer-Consumer con BlockingQueue
 */
public class PersistenciaThread implements Runnable {
    /**
     * Interfaz para definir operaciones de persistencia
     * Permite encapsular cualquier operación de base de datos
     */
    public interface Operation { 
        void run() throws Exception; 
    }

    // Sistema de notificaciones para comunicar eventos
    private final Observable<String> notifier;
    // Cola thread-safe para procesar operaciones de persistencia
    private final BlockingQueue<Operation> operations = new LinkedBlockingQueue<>();
    // Control de estado del hilo (thread-safe)
    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * Constructor del hilo de persistencia
     * @param notifier Sistema de notificaciones
     */
    public PersistenciaThread(Observable<String> notifier) { 
        this.notifier = notifier; 
    }

    /**
     * Agrega una operación a la cola de procesamiento
     * @param op Operación de persistencia a ejecutar
     */
    public void enqueue(Operation op) { 
        if (op != null) operations.offer(op); 
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
     * Procesa la cola de operaciones de persistencia
     */
    @Override
    public void run() {
        start();
        notifier.publish("Hilo Persistencia iniciado");
        
        // Bucle principal del hilo
        while (running.get()) {
            try {
                // Esperar a que haya una operación en la cola
                // take() bloquea hasta que haya un elemento disponible
                Operation op = operations.take();
                
                try {
                    // Ejecutar la operación de persistencia
                    op.run();
                    notifier.publish("Operación persistencia OK");
                } catch (Exception ex) {
                    // Manejar errores de la operación
                    notifier.publish("Error persistencia: " + ex.getMessage());
                }
            } catch (InterruptedException e) {
                // Manejar interrupción del hilo
                Thread.currentThread().interrupt();
                notifier.publish("Hilo Persistencia interrumpido");
            }
        }
        notifier.publish("Hilo Persistencia detenido");
    }
}




