package proyectoparalela.threads;

import proyectoparalela.utils.Observable;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Hilo para el registro mensual automático
 * Programa la ejecución de tareas mensuales el primer día de cada mes
 * Utiliza Timer y TimerTask para programar ejecuciones periódicas
 */
public class RegistroMensualThread implements Runnable {
    // Sistema de notificaciones para comunicar eventos
    private final Observable<String> notifier;
    // Control de estado del hilo (thread-safe)
    private final AtomicBoolean running = new AtomicBoolean(false);
    // Timer para programar ejecuciones periódicas
    private Timer timer;

    /**
     * Constructor del hilo de registro mensual
     * @param notifier Sistema de notificaciones
     */
    public RegistroMensualThread(Observable<String> notifier) {
        this.notifier = notifier;
    }

    /**
     * Inicia el hilo y programa la primera ejecución mensual
     */
    public void start() { 
        running.set(true); 
        scheduleNext(); 
    }
    
    /**
     * Detiene el hilo y cancela el timer
     */
    public void stop() { 
        running.set(false); 
        if (timer != null) timer.cancel(); 
    }

    /**
     * Programa la próxima ejecución mensual
     * Calcula el tiempo hasta el primer día del próximo mes
     */
    private void scheduleNext() {
        if (!running.get()) return;
        
        // Obtener fecha actual
        LocalDate now = LocalDate.now();
        // Calcular el primer día del próximo mes
        LocalDate next = now.with(TemporalAdjusters.firstDayOfNextMonth());
        // Calcular milisegundos hasta la próxima ejecución
        long delayMs = java.time.Duration.between(now.atStartOfDay(), next.atStartOfDay()).toMillis();
        
        // Crear timer como daemon thread
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override 
            public void run() { 
                executeMonthly(); 
                scheduleNext(); // Programar la siguiente ejecución
            }
        }, Math.max(1000, delayMs)); // Mínimo 1 segundo de delay
    }

    /**
     * Ejecuta las tareas mensuales programadas
     * En un sistema real, aquí se generarían reportes y respaldos
     */
    private void executeMonthly() {
        notifier.publish("Ejecución mensual: generación de reportes y respaldos");
        // TODO: Implementar lógica de generación de reportes mensuales
        // TODO: Implementar lógica de respaldos automáticos
        // TODO: Implementar cálculos de estadísticas mensuales
    }

    /**
     * Método principal del hilo
     * Inicia el hilo y programa las ejecuciones mensuales
     */
    @Override
    public void run() { 
        start(); 
        notifier.publish("Hilo RegistroMensual iniciado"); 
    }
}




