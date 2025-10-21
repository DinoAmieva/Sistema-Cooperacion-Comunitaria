package proyectoparalela.controller;

import proyectoparalela.dao.HabitanteDAO;
import proyectoparalela.model.Habitante;
import proyectoparalela.utils.Observable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador para manejar la lógica de negocio relacionada con Habitantes
 * Actúa como intermediario entre la vista y el DAO
 * Implementa el patrón MVC (Model-View-Controller)
 */
public class HabitanteController {
    // DAO para acceso a datos
    private final HabitanteDAO dao;
    // Sistema de notificaciones para comunicación con la vista
    private final Observable<String> notifier;

    /**
     * Constructor del controlador
     * @param dao DAO para acceso a datos de habitantes
     * @param notifier Sistema de notificaciones
     */
    public HabitanteController(HabitanteDAO dao, Observable<String> notifier) {
        this.dao = dao;
        this.notifier = notifier;
    }

    /**
     * Crea un nuevo habitante en el sistema
     * @param nombre Nombre del habitante
     * @param apellido Apellido del habitante
     * @param edad Edad del habitante (puede ser null)
     * @param direccion Dirección del habitante
     * @param telefono Teléfono del habitante
     * @return ID del habitante creado
     * @throws SQLException Si hay error en la operación de base de datos
     */
    public long crearHabitante(String nombre, String apellido, Integer edad, String direccion, String telefono) throws SQLException {
        // Crear objeto Habitante con fecha actual
        Habitante h = new Habitante(null, nombre, apellido, edad, direccion, telefono, LocalDate.now());
        long id = dao.crear(h);
        // Notificar a la vista sobre la creación exitosa
        notifier.publish("Habitante creado: " + id);
        return id;
    }

    /**
     * Obtiene un habitante por su ID
     * @param id ID del habitante
     * @return Objeto Habitante encontrado
     * @throws SQLException Si hay error en la operación de base de datos
     */
    public Habitante obtener(long id) throws SQLException { 
        return dao.leer(id); 
    }
    
    /**
     * Actualiza los datos de un habitante
     * @param h Objeto Habitante con los datos actualizados
     * @return true si la actualización fue exitosa
     * @throws SQLException Si hay error en la operación de base de datos
     */
    public boolean actualizar(Habitante h) throws SQLException { 
        boolean ok = dao.actualizar(h); 
        if (ok) notifier.publish("Habitante actualizado: " + h.getId()); 
        return ok; 
    }
    
    /**
     * Elimina un habitante del sistema
     * @param id ID del habitante a eliminar
     * @return true si la eliminación fue exitosa
     * @throws SQLException Si hay error en la operación de base de datos
     */
    public boolean eliminar(long id) throws SQLException { 
        boolean ok = dao.eliminar(id); 
        if (ok) notifier.publish("Habitante eliminado: " + id); 
        return ok; 
    }
    
    /**
     * Obtiene todos los habitantes del sistema
     * @return Lista de todos los habitantes
     * @throws SQLException Si hay error en la operación de base de datos
     */
    public List<Habitante> listar() throws SQLException { 
        return dao.listarTodos(); 
    }
}


