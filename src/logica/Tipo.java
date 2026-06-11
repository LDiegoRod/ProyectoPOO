package logica;

import java.util.ArrayList;

public class Tipo {

    private String nombre;
    private ArrayList<Item> items;

    public Tipo(String nombre) {
        this.nombre = nombre;
        this.items = new ArrayList<Item>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void agregarItem(Item item) {
        if (item != null && !items.contains(item)) {
            items.add(item);
        }
    }

    public void borrarItem(Item item) {
        if (item != null) {
            items.remove(item);
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}