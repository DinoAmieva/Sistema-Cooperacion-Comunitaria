package proyectoparalela.threads;

import proyectoparalela.dao.FaenaDAO;
import proyectoparalela.model.Faena;
import proyectoparalela.utils.Observable;

import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Hilo para el registro asíncrono de faenas comunitarias
 * Procesa una cola de faenas y las registra en la base de datos
 * Implementa el patrón Producer-Consumer con BlockingQueue
 */
public class RegistroFaenasThread implements Runnable {
    // Sistema de notificaciones para comunicar eventos
    private final Observable<String> notifier;
    // DAO para operaciones de base de datos de faenas
    private final FaenaDAO faenaDAO;
    // Cola thread-safe para procesar faenas
    private final BlockingQueue<Faena> queue = new LinkedBlockingQueue<>();
    // Control de estado del hilo (thread-safe)
    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * Constructor del hilo de registro de faenas
     * @param notifier Sistema de notificaciones
     * @param faenaDAO DAO para operaciones de base de datos
     */
    public RegistroFaenasThread(Observable<String> notifier, FaenaDAO faenaDAO) {
        this.notifier = notifier;
        this.faenaDAO = faenaDAO;
    }

    /**
     * Registra una faena de forma asíncrona agregándola a la cola
     * @param f Objeto Faena a registrar
     */
    public void registrarAsync(Faena f) { 
        queue.offer(f); 
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
     * Procesa la cola de faenas y las registra en la base de datos
     */
    @Override
    public void run() {
        start();
        notifier.publish("Hilo RegistroFaenas iniciado");
        
        // Bucle principal del hilo
        while (running.get()) {
            try {
                // Esperar a que haya una faena en la cola
                // take() bloquea hasta que haya un elemento disponible
                Faena f = queue.take();
                
                // Establecer fecha de participación si no está definida
                if (f.getFechaParticipacion() == null) f.setFechaParticipacion(LocalDate.now());
                
                try {
                    // Crear la faena en la base de datos
                    long id = faenaDAO.crear(f);
                    notifier.publish("Faena registrada id=" + id + " habitante=" + f.getHabitanteId());
                } catch (Exception ex) {
                    // Manejar errores de base de datos
                    notifier.publish("Error registrando faena: " + ex.getMessage());
                }
            } catch (InterruptedException e) {
                // Manejar interrupción del hilo
                Thread.currentThread().interrupt();
                notifier.publish("Hilo RegistroFaenas interrumpido");
            }
        }
        notifier.publish("Hilo RegistroFaenas detenido");
    }
}




