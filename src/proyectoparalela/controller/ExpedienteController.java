package proyectoparalela.controller;

import proyectoparalela.dao.ExpedienteDAO;
import proyectoparalela.model.Expediente;
import proyectoparalela.utils.Observable;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ExpedienteController {
    private final ExpedienteDAO dao;
    private final Observable<String> notifier;

    public ExpedienteController(ExpedienteDAO dao, Observable<String> notifier) {
        this.dao = dao;
        this.notifier = notifier;
    }

    public long crear(long habitanteId, String tipo, String desc) throws SQLException {
        Expediente e = new Expediente(null, habitanteId, tipo, desc, LocalDateTime.now(), "ACTIVO");
        long id = dao.crear(e);
        notifier.publish("Expediente creado: " + id);
        return id;
    }

    public Expediente obtener(long id) throws SQLException { return dao.leer(id); }
    public boolean actualizar(Expediente e) throws SQLException { return dao.actualizar(e); }
    public boolean eliminar(long id) throws SQLException { return dao.eliminar(id); }
    public List<Expediente> listar() throws SQLException { return dao.listarTodos(); }
    public List<Expediente> porHabitante(long habitanteId) throws SQLException { return dao.buscarPorHabitante(habitanteId); }
}




