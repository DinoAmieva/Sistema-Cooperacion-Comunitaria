package proyectoparalela.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Sesion implements Serializable {
    private Long id;
    private String usuario;
    private String token;
    private LocalDateTime inicio;
    private LocalDateTime ultimoAcceso;
    private boolean activa;

    public Sesion() {}

    public Sesion(Long id, String usuario, String token, LocalDateTime inicio, LocalDateTime ultimoAcceso, boolean activa) {
        this.id = id;
        setUsuario(usuario);
        setToken(token);
        this.inicio = inicio;
        this.ultimoAcceso = ultimoAcceso;
        this.activa = activa;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) throw new IllegalArgumentException("usuario requerido");
        this.usuario = usuario.trim();
    }

    public String getToken() { return token; }
    public void setToken(String token) {
        if (token == null || token.trim().isEmpty()) throw new IllegalArgumentException("token requerido");
        this.token = token.trim();
    }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getUltimoAcceso() { return ultimoAcceso; }
    public void setUltimoAcceso(LocalDateTime ultimoAcceso) { this.ultimoAcceso = ultimoAcceso; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    @Override
    public String toString() {
        return "Sesion{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", token='" + token + '\'' +
                ", inicio=" + inicio +
                ", ultimoAcceso=" + ultimoAcceso +
                ", activa=" + activa +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sesion)) return false;
        Sesion sesion = (Sesion) o;
        return Objects.equals(id, sesion.id);
    }

    @Override
    public int hashCode() { return Objects.hashCode(id); }
}


