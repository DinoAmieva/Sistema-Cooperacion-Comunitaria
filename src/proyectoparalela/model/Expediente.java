package proyectoparalela.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Expediente implements Serializable {
    private Long id;
    private Long habitanteId;
    private String tipoExpediente;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private String estado;

    public Expediente() {}

    public Expediente(Long id, Long habitanteId, String tipoExpediente, String descripcion, LocalDateTime fechaCreacion, String estado) {
        this.id = id;
        setHabitanteId(habitanteId);
        setTipoExpediente(tipoExpediente);
        setDescripcion(descripcion);
        this.fechaCreacion = fechaCreacion;
        setEstado(estado);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getHabitanteId() { return habitanteId; }
    public void setHabitanteId(Long habitanteId) {
        if (habitanteId == null || habitanteId <= 0) throw new IllegalArgumentException("habitanteId inválido");
        this.habitanteId = habitanteId;
    }

    public String getTipoExpediente() { return tipoExpediente; }
    public void setTipoExpediente(String tipoExpediente) {
        if (tipoExpediente == null || tipoExpediente.trim().isEmpty()) throw new IllegalArgumentException("tipoExpediente requerido");
        this.tipoExpediente = tipoExpediente.trim();
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion == null ? null : descripcion.trim(); }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado == null ? null : estado.trim(); }

    @Override
    public String toString() {
        return "Expediente{" +
                "id=" + id +
                ", habitanteId=" + habitanteId +
                ", tipoExpediente='" + tipoExpediente + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", estado='" + estado + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expediente)) return false;
        Expediente that = (Expediente) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }
}


