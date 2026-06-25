package logica;

import java.io.Serializable;

public class Alerta implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mensaje;
    private boolean esRecurrente;
    private int tiempoActivacion;
    private String fechaInicio;

    public Alerta(String mensaje, boolean esRecurrente, int tiempoActivacion, String fechaInicio) {
        this.mensaje = mensaje;
        this.esRecurrente = esRecurrente;
        this.tiempoActivacion = tiempoActivacion;
        this.fechaInicio = fechaInicio;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isRecurrente() {
        return esRecurrente;
    }

    public void setRecurrente(boolean esRecurrente) {
        this.esRecurrente = esRecurrente;
    }

    public int getTiempoActivacion() {
        return tiempoActivacion;
    }

    public void setTiempoActivacion(int tiempoActivacion) {
        this.tiempoActivacion = tiempoActivacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public String toString() {
        String tipo = esRecurrente ? "Recurrente" : "Una vez";
        return mensaje + " (" + tipo + ", tiempo: " + tiempoActivacion + ", inicio: " + fechaInicio + ")";
    }
}
