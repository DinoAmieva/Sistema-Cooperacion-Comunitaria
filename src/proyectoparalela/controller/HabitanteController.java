package proyectoparalela.controller;

import proyectoparalela.dao.HabitanteDAO;
import proyectoparalela.model.Habitante;
import proyectoparalela.utils.Observable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HabitanteController {
    
    private final HabitanteDAO dao;
    private final Observable<String> notifier;

    public HabitanteController(HabitanteDAO dao, Observable<String> notifier) {
        this.dao = dao;
        this.notifier = notifier;
    }

    /**
     * REFACTORIZADO: Acepta un objeto Habitante completo.
     * Asigna la fecha de registro actual y lo pasa al DAO.
     */
    public long crearHabitante(Habitante h) throws SQLException {
        // Asignar la fecha de registro al momento de la creación
        h.setFechaRegistro(LocalDate.now());
        
        // ¡¡Seguridad!! Aquí deberías hashear la contraseña antes de guardarla
        // Ejemplo: h.setPassword(BCrypt.hashpw(h.getPassword(), BCrypt.gensalt()));
        
        long id = dao.crear(h);
        
        // Notificar a la vista sobre la creación exitosa
        notifier.publish("Habitante creado: " + id);
        return id;
    }

    /**
     * Obtiene un habitante por su ID
     */
    public Habitante obtener(long id) throws SQLException { 
        return dao.leer(id); 
    }
    
    /**
     * Actualiza los datos de un habitante
     */
    public boolean actualizar(Habitante h) throws SQLException { 
        // ¡¡Aquí también deberías verificar si la contraseña cambió y hashearla!!
        boolean ok = dao.actualizar(h); 
        if (ok) notifier.publish("Habitante actualizado: " + h.getId()); 
        return ok; 
    }
    
    /**
     * Elimina un habitante del sistema
     */
    public boolean eliminar(long id) throws SQLException { 
        boolean ok = dao.eliminar(id); 
        if (ok) notifier.publish("Habitante eliminado: " + id); 
        return ok; 
    }
    
    /**
     * Obtiene todos los habitantes del sistema
     */
    public List<Habitante> listar() throws SQLException { 
        return dao.listarTodos(); 
    }

    /**
     * NUEVO: Método de Login
     * Pasa las credenciales al DAO y notifica el resultado.
     */
    public Habitante login(String usuario, String password) throws SQLException {
        Habitante habitante = dao.login(usuario, password);
        
        if (habitante != null) {
            notifier.publish("Inicio de sesión exitoso: " + habitante.getNombre());
        } else {
            notifier.publish("Error: Usuario o contraseña incorrectos");
        }
        return habitante;
    }
}