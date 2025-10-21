package proyectoparalela.utils;

/**
 * Interfaz Observer que define el contrato para recibir notificaciones
 * del patrón Observer. Los objetos que implementen esta interfaz
 * pueden suscribirse a un Observable para recibir actualizaciones
 * @param <T> Tipo de dato que se notifica
 */
public interface Observer<T> {
    /**
     * Método llamado cuando el Observable publica un evento
     * @param event Evento publicado por el Observable
     */
    void onUpdate(T event);
}


