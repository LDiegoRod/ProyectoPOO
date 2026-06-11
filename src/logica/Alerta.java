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
