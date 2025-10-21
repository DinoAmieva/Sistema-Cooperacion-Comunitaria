package proyectoparalela.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Faena implements Serializable {
    private Long id;
    private Long habitanteId;
    private String tipoFaena;
    private String descripcion;
    private LocalDate fechaParticipacion;
    private Integer horasTrabajadas;

    public Faena() {}

    public Faena(Long id, Long habitanteId, String tipoFaena, String descripcion, LocalDate fechaParticipacion, Integer horasTrabajadas) {
        this.id = id;
        setHabitanteId(habitanteId);
        setTipoFaena(tipoFaena);
        this.descripcion = descripcion;
        this.fechaParticipacion = fechaParticipacion;
        setHorasTrabajadas(horasTrabajadas);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getHabitanteId() { return habitanteId; }
    public void setHabitanteId(Long habitanteId) {
        if (habitanteId == null || habitanteId <= 0) throw new IllegalArgumentException("habitanteId inválido");
        this.habitanteId = habitanteId;
    }

    public String getTipoFaena() { return tipoFaena; }
    public void setTipoFaena(String tipoFaena) {
        if (tipoFaena == null || tipoFaena.trim().isEmpty()) throw new IllegalArgumentException("tipoFaena requerido");
        this.tipoFaena = tipoFaena.trim();
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion == null ? null : descripcion.trim(); }

    public LocalDate getFechaParticipacion() { return fechaParticipacion; }
    public void setFechaParticipacion(LocalDate fechaParticipacion) { this.fechaParticipacion = fechaParticipacion; }

    public Integer getHorasTrabajadas() { return horasTrabajadas; }
    public void setHorasTrabajadas(Integer horasTrabajadas) {
        if (horasTrabajadas != null && (horasTrabajadas < 0 || horasTrabajadas > 24*7)) throw new IllegalArgumentException("horasTrabajadas inválidas");
        this.horasTrabajadas = horasTrabajadas;
    }

    @Override
    public String toString() {
        return "Faena{" +
                "id=" + id +
                ", habitanteId=" + habitanteId +
                ", tipoFaena='" + tipoFaena + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaParticipacion=" + fechaParticipacion +
                ", horasTrabajadas=" + horasTrabajadas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faena)) return false;
        Faena that = (Faena) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }
}


