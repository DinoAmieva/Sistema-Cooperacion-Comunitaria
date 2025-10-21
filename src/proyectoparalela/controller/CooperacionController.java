package proyectoparalela.controller;

import proyectoparalela.dao.CooperacionDAO;
import proyectoparalela.model.Cooperacion;
import proyectoparalela.utils.Observable;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CooperacionController {
    private final CooperacionDAO dao;
    private final Observable<String> notifier;

    public CooperacionController(CooperacionDAO dao, Observable<String> notifier) {
        this.dao = dao;
        this.notifier = notifier;
    }

    public long crearCooperacion(long habitanteId, String tipoCooperacion, String descripcion, Integer puntos) throws SQLException {
        Cooperacion cpr = new Cooperacion(null, habitanteId, tipoCooperacion, descripcion, LocalDate.now(), puntos);
        long id = dao.crear(cpr);
        notifier.publish("Cooperación creada: " + id);
        return id;
    }

    public Cooperacion obtener(long id) throws SQLException { 
        return dao.leer(id); 
    }

    public boolean actualizar(Cooperacion cpr) throws SQLException { 
        boolean ok = dao.actualizar(cpr); 
        if (ok) notifier.publish("Cooperación actualizada: " + cpr.getId()); 
        return ok; 
    }

    public boolean eliminar(long id) throws SQLException { 
        boolean ok = dao.eliminar(id); 
        if (ok) notifier.publish("Cooperación eliminada: " + id); 
        return ok; 
    }

    public List<Cooperacion> listar() throws SQLException { 
        return dao.listarTodos(); 
    }

    public List<Cooperacion> buscarPorHabitante(long habitanteId) throws SQLException {
        return dao.buscarPorHabitante(habitanteId);
    }

    public List<Cooperacion> generarReporteMensual(int año, int mes) throws SQLException {
        List<Cooperacion> cooperaciones = dao.buscarPorMes(año, mes);
        notifier.publish("Reporte mensual generado: " + cooperaciones.size() + " cooperaciones para " + mes + "/" + año);
        return cooperaciones;
    }

    public int contarCooperacionesPorMes(int año, int mes) throws SQLException {
        return dao.contarCooperacionesPorMes(año, mes);
    }

    public int sumarPuntosPorMes(int año, int mes) throws SQLException {
        return dao.sumarPuntosPorMes(año, mes);
    }

    public void crearCooperacionesDePrueba() throws SQLException {
        // Crear algunas cooperaciones de prueba para el mes actual
        LocalDate hoy = LocalDate.now();
        int año = hoy.getYear();
        int mes = hoy.getMonthValue();
        
        // Crear cooperaciones de prueba
        crearCooperacion(366, "Mantenimiento", "Limpieza de áreas comunes", 10);
        crearCooperacion(366, "Seguridad", "Vigilancia nocturna", 15);
        crearCooperacion(366, "Social", "Organización de evento comunitario", 20);
        
        notifier.publish("Cooperaciones de prueba creadas para " + mes + "/" + año);
    }
}
