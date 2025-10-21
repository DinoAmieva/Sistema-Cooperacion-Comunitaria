package proyectoparalela.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Habitante implements Serializable {
    private Long id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String direccion;
    private String telefono;
    private LocalDate fechaRegistro;

    public Habitante() {}

    public Habitante(Long id, String nombre, String apellido, Integer edad, String direccion, String telefono, LocalDate fechaRegistro) {
        this.id = id;
        setNombre(nombre);
        setApellido(apellido);
        setEdad(edad);
        setDireccion(direccion);
        setTelefono(telefono);
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("Nombre no puede ser vacío");
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) throw new IllegalArgumentException("Apellido no puede ser vacío");
        this.apellido = apellido.trim();
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        if (edad != null && (edad < 0 || edad > 120)) throw new IllegalArgumentException("Edad inválida");
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        if (direccion != null) this.direccion = direccion.trim(); else this.direccion = null;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono != null) this.telefono = telefono.trim(); else this.telefono = null;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Habitante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habitante)) return false;
        Habitante that = (Habitante) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}


