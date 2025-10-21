package proyectoparalela.threads;

import proyectoparalela.dao.SesionDAO;
import proyectoparalela.model.Sesion;
import proyectoparalela.utils.Observable;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Hilo para el manejo de sesiones de usuario
 * Mantiene un registro de sesiones activas y maneja timeouts automáticos
 * Implementa heartbeat y expiración automática de sesiones
 */
public class SesionThread implements Runnable {
    // Sistema de notificaciones para comunicar eventos
    private final Observable<String> notifier;
    // DAO para operaciones de base de datos de sesiones
    private final SesionDAO sesionDAO;
    // Mapa thread-safe de sesiones activas (usuario -> sesión)
    private final Map<String, Sesion> sesiones = new ConcurrentHashMap<>();
    // Control de estado del hilo (thread-safe)
    private final AtomicBoolean running = new AtomicBoolean(false);
    // Intervalo de heartbeat en milisegundos
    private long heartbeatMillis = 5_000;
    // Timeout de sesión en milisegundos
    private long timeoutMillis = 60_000;

    /**
     * Constructor del hilo de sesiones
     * @param notifier Sistema de notificaciones
     * @param sesionDAO DAO para operaciones de base de datos
     */
    public SesionThread(Observable<String> notifier, SesionDAO sesionDAO) {
        this.notifier = notifier;
        this.sesionDAO = sesionDAO;
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
     * Establece el intervalo de heartbeat
     * @param ms Milisegundos entre heartbeats (mínimo 1000ms)
     */
    public void setHeartbeatMillis(long ms) { 
        heartbeatMillis = Math.max(1000, ms); 
    }
    
    /**
     * Establece el timeout de sesión
     * @param ms Milisegundos de inactividad antes de expirar (mínimo 5000ms)
     */
    public void setTimeoutMillis(long ms) { 
        timeoutMillis = Math.max(5000, ms); 
    }

    /**
     * Inicia una nueva sesión para un usuario
     * @param usuario Nombre de usuario
     * @param token Token de autenticación
     */
    public void login(String usuario, String token) {
        // Crear nueva sesión
        Sesion s = new Sesion(null, usuario, token, LocalDateTime.now(), LocalDateTime.now(), true);
        sesiones.put(usuario, s);
        notifier.publish("Sesión iniciada: " + usuario);
        
        try { 
            // Persistir sesión en base de datos
            long id = sesionDAO.crear(s); 
            s.setId(id); 
        } catch (Exception e) { 
            notifier.publish("Error creando sesión: " + e.getMessage()); 
        }
    }

    /**
     * Actualiza el último acceso de una sesión (touch)
     * @param usuario Nombre de usuario
     */
    public void touch(String usuario) {
        Sesion s = sesiones.get(usuario);
        if (s != null) s.setUltimoAcceso(LocalDateTime.now());
    }

    /**
     * Método principal del hilo
     * Monitorea las sesiones activas y maneja timeouts
     */
    @Override
    public void run() {
        start();
        notifier.publish("Hilo SesionThread iniciado");
        
        // Bucle principal del hilo
        while (running.get()) {
            try {
                // Revisar cada sesión activa
                for (Sesion s : sesiones.values()) {
                    // Calcular tiempo de inactividad
                    long idleMs = java.time.Duration.between(s.getUltimoAcceso(), LocalDateTime.now()).toMillis();
                    
                    if (idleMs > timeoutMillis) {
                        // Sesión expirada - cerrar sesión
                        s.setActiva(false);
                        notifier.publish("Sesión expirada: " + s.getUsuario());
                        
                        try { 
                            // Actualizar estado en base de datos
                            sesionDAO.actualizar(s); 
                        } catch (Exception ignored) {}
                        
                        // Remover de sesiones activas
                        sesiones.remove(s.getUsuario());
                    } else {
                        // Sesión activa - enviar heartbeat
                        notifier.publish("Heartbeat sesión: " + s.getUsuario());
                    }
                }
                
                // Esperar antes del siguiente ciclo
                Thread.sleep(heartbeatMillis);
            } catch (InterruptedException e) {
                // Manejar interrupción del hilo
                Thread.currentThread().interrupt();
                notifier.publish("Hilo SesionThread interrumpido");
            }
        }
        notifier.publish("Hilo SesionThread detenido");
    }
}




