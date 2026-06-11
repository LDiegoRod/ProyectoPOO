package logica;

import java.util.ArrayList;

public class Prestamo {

    private Persona prestatario;
    private ArrayList<Item> itemsPrestados;
    private Alerta alertaAsociada;
    private String fechaPrestamo;

    public Prestamo(Persona prestatario, ArrayList<Item> items, Alerta alertaAsociada) {
        this.prestatario = prestatario;
        this.itemsPrestados = new ArrayList<Item>();
        this.alertaAsociada = alertaAsociada;
        this.fechaPrestamo = "";

        if (items != null) {
            for (Item item : items) {
                agregarItem(item);
            }
        }

        if (prestatario != null) {
            prestatario.agregarPrestamo(this);
        }
    }

    public Persona getPrestatario() {
        return prestatario;
    }
