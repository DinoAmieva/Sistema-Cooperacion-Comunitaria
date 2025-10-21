package proyectoparalela.controller;

import proyectoparalela.dao.FaenaDAO;
import proyectoparalela.model.Faena;
import proyectoparalela.utils.Observable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FaenaController {
    private final FaenaDAO dao;
    private final Observable<String> notifier;

    public FaenaController(FaenaDAO dao, Observable<String> notifier) {
        this.dao = dao;
        this.notifier = notifier;
    }

    public long crear(long habitanteId, String tipo, String desc, Integer horas) throws SQLException {
        Faena f = new Faena(null, habitanteId, tipo, desc, LocalDate.now(), horas);
        long id = dao.crear(f);
        notifier.publish("Faena creada: " + id);
        return id;
    }

    public Faena obtener(long id) throws SQLException { return dao.leer(id); }
    public boolean actualizar(Faena f) throws SQLException { return dao.actualizar(f); }
    public boolean eliminar(long id) throws SQLException { return dao.eliminar(id); }
    public List<Faena> listar() throws SQLException { return dao.listarTodos(); }
    public List<Faena> porHabitante(long habitanteId) throws SQLException { return dao.buscarPorHabitante(habitanteId); }
}









