package control;

import java.util.ArrayList;

import logica.Alerta;
import logica.Categoria;
import logica.Item;
import logica.Persona;
import logica.Prestamo;
import logica.Tipo;

public class PrestamoControlador {

    private ArrayList<Item> listaItems;
    private ArrayList<Persona> listaPersonas;
    private ArrayList<Categoria> listaCategorias;
    private ArrayList<Tipo> listaTipos;
    private ArrayList<Prestamo> listaPrestamos;

    public PrestamoControlador() {
        this.listaItems = new ArrayList<Item>();
        this.listaPersonas = new ArrayList<Persona>();
        this.listaCategorias = new ArrayList<Categoria>();
        this.listaTipos = new ArrayList<Tipo>();
        this.listaPrestamos = new ArrayList<Prestamo>();
    }
    
    public ArrayList<Item> getListaItems() {
        return listaItems;
    }

    public ArrayList<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public ArrayList<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public ArrayList<Tipo> getListaTipos() {
        return listaTipos;
    }

    public ArrayList<Prestamo> getListaPrestamos() {
        return listaPrestamos;
    }
 // ------------------------------------------------------------------------------

    public boolean crearItem(String codigo, String nombre, String descripcion, Tipo tipo) {
        if (buscarItem(codigo) != null) {
            return false;
        }

        Item item = new Item(codigo, nombre, descripcion);
        item.setTipo(tipo);
        listaItems.add(item);
        return true;
    }

    public Item buscarItem(String codigo) {
        for (Item item : listaItems) {
            if (item.getCodigo().equalsIgnoreCase(codigo)) {
                return item;
            }
        }
        return null;
    }

    public boolean modificarItem(String codigo, String nuevoNombre, String nuevaDescripcion, Tipo nuevoTipo) {
        Item item = buscarItem(codigo);

        if (item == null) {
            return false;
        }

        item.setNombre(nuevoNombre);
        item.setDescripcion(nuevaDescripcion);
        item.setTipo(nuevoTipo);
        return true;
    }

    public boolean borrarItem(String codigo) {
        Item item = buscarItem(codigo);

        if (item == null || itemEstaPrestado(item)) {
            return false;
        }

        if (item.getTipo() != null) {
            item.getTipo().borrarItem(item);
        }

        for (Categoria categoria : item.getCategorias()) {
            categoria.borrarItem(item);
        }

        listaItems.remove(item);
        return true;
    }

    public boolean itemEstaPrestado(Item item) {
        for (Prestamo prestamo : listaPrestamos) {
            if (prestamo.getItemsPrestados().contains(item)) {
                return true;
            }
        }
        return false;
    }