package proyectoparalela.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Cooperacion implements Serializable {
    private Long id;
    private Long habitanteId;
    private String tipoCooperacion;
    private String descripcion;
    private LocalDate fechaCooperacion;
    private Integer puntos;

    public Cooperacion() {}

    public Cooperacion(Long id, Long habitanteId, String tipoCooperacion, String descripcion, LocalDate fechaCooperacion, Integer puntos) {
        this.id = id;
        setHabitanteId(habitanteId);
        setTipoCooperacion(tipoCooperacion);
        this.descripcion = descripcion;
        this.fechaCooperacion = fechaCooperacion;
        setPuntos(puntos);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getHabitanteId() { return habitanteId; }
    public void setHabitanteId(Long habitanteId) {
        if (habitanteId == null || habitanteId <= 0) throw new IllegalArgumentException("habitanteId inválido");
        this.habitanteId = habitanteId;
    }

    public String getTipoCooperacion() { return tipoCooperacion; }
    public void setTipoCooperacion(String tipoCooperacion) {
        if (tipoCooperacion == null || tipoCooperacion.trim().isEmpty()) throw new IllegalArgumentException("tipoCooperacion requerido");
        this.tipoCooperacion = tipoCooperacion.trim();
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion == null ? null : descripcion.trim(); }

    public LocalDate getFechaCooperacion() { return fechaCooperacion; }
    public void setFechaCooperacion(LocalDate fechaCooperacion) { this.fechaCooperacion = fechaCooperacion; }

    public Integer getPuntos() { return puntos; }
    public void setPuntos(Integer puntos) {
        if (puntos != null && puntos < 0) throw new IllegalArgumentException("puntos no puede ser negativo");
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "Cooperacion{" +
                "id=" + id +
                ", habitanteId=" + habitanteId +
                ", tipoCooperacion='" + tipoCooperacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCooperacion=" + fechaCooperacion +
                ", puntos=" + puntos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cooperacion)) return false;
        Cooperacion that = (Cooperacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }
}


