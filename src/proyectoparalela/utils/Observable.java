package proyectoparalela.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Clase Observable que implementa el patrón Observer
 * Permite que múltiples objetos se suscriban para recibir notificaciones
 * cuando se publiquen cambios en el sistema
 * @param <T> Tipo de dato que se notifica
 */
public class Observable<T> {
    // Lista thread-safe de observadores suscritos
    private final List<Observer<T>> observers = new CopyOnWriteArrayList<>();

    /**
     * Suscribe un observador para recibir notificaciones
     * @param o Observador a suscribir (no puede ser null)
     */
    public void subscribe(Observer<T> o) { 
        if (o != null) observers.add(o); 
    }
    
    /**
     * Desuscribe un observador para dejar de recibir notificaciones
     * @param o Observador a desuscribir
     */
    public void unsubscribe(Observer<T> o) { 
        observers.remove(o); 
    }
    
    /**
     * Publica un evento a todos los observadores suscritos
     * @param event Evento a notificar a los observadores
     */
    public void publish(T event) { 
        for (Observer<T> o : observers) o.onUpdate(event); 
    }
}


