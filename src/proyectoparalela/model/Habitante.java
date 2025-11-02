package proyectoparalela.model;

import java.time.LocalDate;

/**
 * Modelo de entidad para la tabla 'habitantes'.
 * Contiene todos los campos del formulario COPACI.
 */
public class Habitante {

    private Long id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String direccion;
    private String telefono;
    private LocalDate fechaRegistro;

    // --- Nuevos campos de la BD ---
    private LocalDate fechaNacimiento;
    private String lugarNacimiento;
    private String estadoCivil;
    private String ocupacion;
    private String gradoEstudios;
    private String manzana;
    private boolean originarioAlmaya;
    private String certificadoComunero;
    private String tipoCertificado;
    private String usuario;
    private String password;

    // --- Constructores ---
    
    public Habitante() {
        // Constructor vacío
    }

    public Habitante(Long id, String nombre, String apellido, Integer edad, String direccion, 
                     String telefono, LocalDate fechaRegistro, LocalDate fechaNacimiento, 
                     String lugarNacimiento, String estadoCivil, String ocupacion, 
                     String gradoEstudios, String manzana, boolean originarioAlmaya, 
                     String certificadoComunero, String tipoCertificado, String usuario, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.fechaNacimiento = fechaNacimiento;
        this.lugarNacimiento = lugarNacimiento;
        this.estadoCivil = estadoCivil;
        this.ocupacion = ocupacion;
        this.gradoEstudios = gradoEstudios;
        this.manzana = manzana;
        this.originarioAlmaya = originarioAlmaya;
        this.certificadoComunero = certificadoComunero;
        this.tipoCertificado = tipoCertificado;
        this.usuario = usuario;
        this.password = password;
    }


    // --- Getters y Setters ---
    
    // (Getters y Setters para campos existentes: id, nombre, apellido, edad, direccion, telefono, fechaRegistro)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    // --- Getters y Setters para NUEVOS campos ---

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getLugarNacimiento() { return lugarNacimiento; }
    public void setLugarNacimiento(String lugarNacimiento) { this.lugarNacimiento = lugarNacimiento; }
    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }
    public String getOcupacion() { return ocupacion; }
    public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }
    public String getGradoEstudios() { return gradoEstudios; }
    public void setGradoEstudios(String gradoEstudios) { this.gradoEstudios = gradoEstudios; }
    public String getManzana() { return manzana; }
    public void setManzana(String manzana) { this.manzana = manzana; }
    public boolean isOriginarioAlmaya() { return originarioAlmaya; }
    public void setOriginarioAlmaya(boolean originarioAlmaya) { this.originarioAlmaya = originarioAlmaya; }
    public String getCertificadoComunero() { return certificadoComunero; }
    public void setCertificadoComunero(String certificadoComunero) { this.certificadoComunero = certificadoComunero; }
    public String getTipoCertificado() { return tipoCertificado; }
    public void setTipoCertificado(String tipoCertificado) { this.tipoCertificado = tipoCertificado; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}