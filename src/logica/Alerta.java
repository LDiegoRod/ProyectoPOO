package logica;

public class Alerta {

    private String mensaje;
    private boolean esRecurrente;
    private int tiempoActivacion;

    public Alerta(String mensaje, boolean esRecurrente, int tiempoActivacion) {
        this.mensaje = mensaje;
        this.esRecurrente = esRecurrente;
        this.tiempoActivacion = tiempoActivacion;
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

    @Override
    public String toString() {
        String tipo = esRecurrente ? "Recurrente" : "Una vez";
        return mensaje + " (" + tipo + ", tiempo: " + tiempoActivacion + ")";
    }
}
