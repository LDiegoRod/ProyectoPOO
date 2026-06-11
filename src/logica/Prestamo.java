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

    public void setPrestatario(Persona prestatario) {
        if (this.prestatario != null) {
            this.prestatario.borrarPrestamo(this);
        }

        this.prestatario = prestatario;

        if (prestatario != null) {
            prestatario.agregarPrestamo(this);
        }
    }

    public ArrayList<Item> getItemsPrestados() {
        return itemsPrestados;
    }

    public void agregarItem(Item item) {
        if (item != null && !itemsPrestados.contains(item)) {
            itemsPrestados.add(item);
        }
    }

    public void eliminarItem(Item item) {
        if (item != null) {
            itemsPrestados.remove(item);
        }
    }

    public Alerta getAlertaAsociada() {
        return alertaAsociada;
    }

    public void setAlertaAsociada(Alerta alertaAsociada) {
        this.alertaAsociada = alertaAsociada;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    @Override
    public String toString() {
        return "Prestamo de " + prestatario + " - Items: " + itemsPrestados.size();
    }
}