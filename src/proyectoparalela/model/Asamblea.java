package proyectoparalela.model;

public class Asamblea {
    
    private Long id;
    private long habitanteId;
    private int año;
    private int trimestre; // 1, 2, 3, o 4
    private boolean asistio;

    public Asamblea() { }

    public Asamblea(Long id, long habitanteId, int año, int trimestre, boolean asistio) {
        this.id = id;
        this.habitanteId = habitanteId;
        this.año = año;
        this.trimestre = trimestre;
        this.asistio = asistio;
    }

    // --- Getters y Setters ---
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public long getHabitanteId() { return habitanteId; }
    public void setHabitanteId(long habitanteId) { this.habitanteId = habitanteId; }
    public int getAño() { return año; }
    public void setAño(int año) { this.año = año; }
    public int getTrimestre() { return trimestre; }
    public void setTrimestre(int trimestre) { this.trimestre = trimestre; }
    public boolean isAsistio() { return asistio; }
    public void setAsistio(boolean asistio) { this.asistio = asistio; }
}