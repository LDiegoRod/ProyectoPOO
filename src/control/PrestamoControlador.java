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

    // =========================================================

    public boolean crearItem(String codigo, String nombre, String descripcion, Tipo tipo, ArrayList<Categoria> categorias) {
        if (consultarItem(codigo) != null) {
            return false;
        }

        Item item = new Item(codigo, nombre, descripcion);
        item.setTipo(tipo);

        if (categorias != null) {
            for (Categoria categoria : categorias) {
                item.agregarCategoria(categoria);
            }
        }

        listaItems.add(item);
        return true;
    }

    public boolean modificarItem(Item item, String nombre, String descripcion, Tipo tipo, ArrayList<Categoria> categorias) {
        if (item == null || !listaItems.contains(item)) {
            return false;
        }

        item.setNombre(nombre);
        item.setDescripcion(descripcion);
        item.setTipo(tipo);

        ArrayList<Categoria> categoriasActuales = new ArrayList<Categoria>(item.getCategorias());

        for (Categoria categoria : categoriasActuales) {
            item.eliminarCategoria(categoria);
        }

        if (categorias != null) {
            for (Categoria categoria : categorias) {
                item.agregarCategoria(categoria);
            }
        }

        return true;
    }

    public boolean borrarItem(Item item) {
        if (item == null || !listaItems.contains(item)) {
            return false;
        }

        if (itemEstaPrestado(item)) {
            return false;
        }

        if (item.getTipo() != null) {
            item.getTipo().borrarItem(item);
        }

        ArrayList<Categoria> categoriasActuales = new ArrayList<Categoria>(item.getCategorias());

        for (Categoria categoria : categoriasActuales) {
            item.eliminarCategoria(categoria);
        }

        listaItems.remove(item);
        return true;
    }

    public Item consultarItem(String codigo) {
        for (Item item : listaItems) {
            if (item.getCodigo().equalsIgnoreCase(codigo)) {
                return item;
            }
        }

        return null;
    }

    public ArrayList<Item> obtenerItems() {
        return listaItems;
    }

    public boolean itemEstaPrestado(Item item) {
        for (Prestamo prestamo : listaPrestamos) {
            if (prestamo.getItemsPrestados().contains(item)) {
                return true;
            }
        }

        return false;
    }

    // =========================================================

    public boolean crearPersona(String nombre, String telefono, String correoElectronico) {
        if (consultarPersona(correoElectronico) != null) {
            return false;
        }

        Persona persona = new Persona(nombre, telefono, correoElectronico);
        listaPersonas.add(persona);
        return true;
    }

    public boolean modificarPersona(Persona persona, String nombre, String telefono, String correoElectronico) {
        if (persona == null || !listaPersonas.contains(persona)) {
            return false;
        }

        persona.setNombre(nombre);
        persona.setTelefono(telefono);
        persona.setCorreoElectronico(correoElectronico);
        return true;
    }

    public boolean borrarPersona(Persona persona) {
        if (persona == null || !listaPersonas.contains(persona)) {
            return false;
        }

        if (!persona.getPrestamos().isEmpty()) {
            return false;
        }

        listaPersonas.remove(persona);
        return true;
    }

    public Persona consultarPersona(String correoElectronico) {
        for (Persona persona : listaPersonas) {
            if (persona.getCorreoElectronico().equalsIgnoreCase(correoElectronico)) {
                return persona;
            }
        }

        return null;
    }

    public ArrayList<Persona> obtenerPersonas() {
        return listaPersonas;
    }


    // =========================================================

    public boolean crearCategoria(String nombre) {
        if (consultarCategoria(nombre) != null) {
            return false;
        }

        Categoria categoria = new Categoria(nombre);
        listaCategorias.add(categoria);
        return true;
    }

    public boolean modificarCategoria(Categoria categoria, String nombre) {
        if (categoria == null || !listaCategorias.contains(categoria)) {
            return false;
        }

        categoria.setNombre(nombre);
        return true;
    }

    public boolean borrarCategoria(Categoria categoria) {
        if (categoria == null || !listaCategorias.contains(categoria)) {
            return false;
        }

        ArrayList<Item> itemsDeCategoria = new ArrayList<Item>(categoria.getItems());

        for (Item item : itemsDeCategoria) {
            item.eliminarCategoria(categoria);
        }

        listaCategorias.remove(categoria);
        return true;
    }

    public Categoria consultarCategoria(String nombre) {
        for (Categoria categoria : listaCategorias) {
            if (categoria.getNombre().equalsIgnoreCase(nombre)) {
                return categoria;
            }
        }

        return null;
    }

    public ArrayList<Categoria> obtenerCategorias() {
        return listaCategorias;
    }


    // =========================================================

    public boolean crearTipo(String nombre) {
        if (consultarTipo(nombre) != null) {
            return false;
        }

        Tipo tipo = new Tipo(nombre);
        listaTipos.add(tipo);
        return true;
    }

    public boolean modificarTipo(Tipo tipo, String nombre) {
        if (tipo == null || !listaTipos.contains(tipo)) {
            return false;
        }

        tipo.setNombre(nombre);
        return true;
    }

    public boolean borrarTipo(Tipo tipo) {
        if (tipo == null || !listaTipos.contains(tipo)) {
            return false;
        }

        if (!tipo.getItems().isEmpty()) {
            return false;
        }

        listaTipos.remove(tipo);
        return true;
    }

    public Tipo consultarTipo(String nombre) {
        for (Tipo tipo : listaTipos) {
            if (tipo.getNombre().equalsIgnoreCase(nombre)) {
                return tipo;
            }
        }

        return null;
    }

    public ArrayList<Tipo> obtenerTipos() {
        return listaTipos;
    }
    
    // =========================================================

    public Prestamo crearPrestamo(Persona prestatario, ArrayList<Item> items, Alerta alerta) {
        if (prestatario == null || items == null || items.isEmpty()) {
            return null;
        }

        for (Item item : items) {
            if (itemEstaPrestado(item)) {
                return null;
            }
        }

        Prestamo prestamo = new Prestamo(prestatario, items, alerta);
        listaPrestamos.add(prestamo);
        return prestamo;
    }

    public boolean agregarItemAPrestamo(Prestamo prestamo, Item item) {
        if (prestamo == null || item == null) {
            return false;
        }

        if (!listaPrestamos.contains(prestamo)) {
            return false;
        }

        if (itemEstaPrestado(item)) {
            return false;
        }

        prestamo.agregarItem(item);
        return true;
    }

    public boolean eliminarItemDePrestamo(Prestamo prestamo, Item item) {
        if (prestamo == null || item == null) {
            return false;
        }

        if (!listaPrestamos.contains(prestamo)) {
            return false;
        }

        prestamo.eliminarItem(item);
        return true;
    }

    public boolean retornarItemDePrestamo(Prestamo prestamo, Item item) {
        if (prestamo == null || item == null) {
            return false;
        }

        if (!listaPrestamos.contains(prestamo)) {
            return false;
        }

        prestamo.eliminarItem(item);
        return true;
    }

    public boolean finalizarPrestamo(Prestamo prestamo) {
        if (prestamo == null || !listaPrestamos.contains(prestamo)) {
            return false;
        }

        if (prestamo.getPrestatario() != null) {
            prestamo.getPrestatario().borrarPrestamo(prestamo);
        }

        listaPrestamos.remove(prestamo);
        return true;
    }

    public ArrayList<Prestamo> obtenerPrestamos() {
        return listaPrestamos;
    }