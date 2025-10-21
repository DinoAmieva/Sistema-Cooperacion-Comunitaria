package proyectoparalela.threads;

import proyectoparalela.dao.HabitanteDAO;
import proyectoparalela.model.Habitante;
import proyectoparalela.utils.Observable;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Hilo para simular el registro automático de habitantes
 * Simula el proceso de registro automático de personas que cumplen 18 años
 * y se convierten en habitantes del sistema
 */
public class RegistroHabitanteThread implements Runnable {
    // Sistema de notificaciones para comunicar eventos
    private final Observable<String> notifier;
    // DAO para operaciones de base de datos
    private final HabitanteDAO habitanteDAO;
    // Control de estado del hilo (thread-safe)
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean paused = new AtomicBoolean(false);
    // Intervalo de tiempo entre registros (en milisegundos)
    private long sleepMillis = 5_000;

    /**
     * Constructor del hilo de registro de habitantes
     * @param notifier Sistema de notificaciones
     * @param habitanteDAO DAO para operaciones de base de datos
     */
    public RegistroHabitanteThread(Observable<String> notifier, HabitanteDAO habitanteDAO) {
        this.notifier = notifier;
        this.habitanteDAO = habitanteDAO;
    }

    /**
     * Inicia el hilo
     */
    public void start() { 
        running.set(true); 
    }
    
    /**
     * Pausa el hilo (mantiene el estado pero detiene la ejecución)
     */
    public void pauseThread() { 
        paused.set(true); 
    }
    
    /**
     * Reanuda el hilo pausado
     */
    public void resumeThread() { 
        paused.set(false); 
        synchronized (paused) { 
            paused.notifyAll(); 
        } 
    }
    
    /**
     * Detiene el hilo completamente
     */
    public void stopThread() { 
        running.set(false); 
    }
    
    /**
     * Establece el intervalo de tiempo entre registros
     * @param ms Milisegundos entre registros (mínimo 1000ms)
     */
    public void setSleepMillis(long ms) { 
        this.sleepMillis = Math.max(1000, ms); 
    }

    /**
     * Método principal del hilo
     * Ejecuta un bucle continuo que simula el registro automático de habitantes
     */
    @Override
    public void run() {
        start();
        notifier.publish("Hilo RegistroHabitante iniciado");
        
        // Bucle principal del hilo
        while (running.get()) {
            try {
                // Esperar si el hilo está pausado
                synchronized (paused) { 
                    while (paused.get()) paused.wait(); 
                }
                
                // Lógica simulada: alta automática de habitantes que cumplan 18 años
                // En un sistema real, aquí se consultarían registros externos
                Habitante h = new Habitante(null, "Nombre", "Apellido", 18, "Direccion", "", LocalDate.now());
                
                try {
                    // Crear el habitante en la base de datos
                    long id = habitanteDAO.crear(h);
                    notifier.publish("Nuevo habitante registrado id=" + id);
                } catch (Exception ex) {
                    // Manejar errores de base de datos
                    notifier.publish("Error registrando habitante: " + ex.getMessage());
                }
                
                // Esperar antes del siguiente registro
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                // Manejar interrupción del hilo
                Thread.currentThread().interrupt();
                notifier.publish("Hilo RegistroHabitante interrumpido");
            }
        }
        notifier.publish("Hilo RegistroHabitante detenido");
    }
}


