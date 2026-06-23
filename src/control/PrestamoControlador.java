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
    // =========================================================
    public boolean crearItem(String codigo, String nombre, String descripcion, String nombreTipo,
            ArrayList<String> nombresCategorias) {

        if (consultarItem(codigo) != null) {
            return false;
        }

        Tipo tipo = consultarTipo(nombreTipo);

        if (tipo == null) {
            return false;
        }

        Item item = new Item(codigo, nombre, descripcion);
        item.setTipo(tipo);

        if (nombresCategorias != null) {
            for (String nombreCategoria : nombresCategorias) {
                Categoria categoria = consultarCategoria(nombreCategoria);

                if (categoria != null) {
                    item.agregarCategoria(categoria);
                }
            }
        }

        listaItems.add(item);
        return true;
    }

    public boolean modificarItem(String codigo, String nombre, String descripcion, String nombreTipo,
            ArrayList<String> nombresCategorias) {

        Item item = consultarItem(codigo);

        if (item == null) {
            return false;
        }

        Tipo tipo = consultarTipo(nombreTipo);

        if (tipo == null) {
            return false;
        }

        item.setNombre(nombre);
        item.setDescripcion(descripcion);
        item.setTipo(tipo);

        ArrayList<Categoria> categoriasActuales = new ArrayList<Categoria>(item.getCategorias());

        for (Categoria categoria : categoriasActuales) {
            item.eliminarCategoria(categoria);
        }

        if (nombresCategorias != null) {
            for (String nombreCategoria : nombresCategorias) {
                Categoria categoria = consultarCategoria(nombreCategoria);

                if (categoria != null) {
                    item.agregarCategoria(categoria);
                }
            }
        }

        return true;
    }

    public boolean borrarItem(String codigo) {
        Item item = consultarItem(codigo);

        if (item == null) {
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

    // =========================================================

    public boolean crearPersona(String nombre, String telefono, String correo) {
        if (consultarPersona(correo) != null) {
            return false;
        }

        Persona persona = new Persona(nombre, telefono, correo);
        listaPersonas.add(persona);
        return true;
    }

    public boolean modificarPersona(String correoActual, String nombre, String telefono, String correoNuevo) {
        Persona persona = consultarPersona(correoActual);

        if (persona == null) {
            return false;
        }

        if (!correoActual.equalsIgnoreCase(correoNuevo) && consultarPersona(correoNuevo) != null) {
            return false;
        }

        persona.setNombre(nombre);
        persona.setTelefono(telefono);
        persona.setCorreoElectronico(correoNuevo);
        return true;
    }

    public boolean borrarPersona(String correo) {
        Persona persona = consultarPersona(correo);

        if (persona == null) {
            return false;
        }

        if (!persona.getPrestamos().isEmpty()) {
            return false;
        }

        listaPersonas.remove(persona);
        return true;
    }

    public Persona consultarPersona(String correo) {
        for (Persona persona : listaPersonas) {
            if (persona.getCorreoElectronico().equalsIgnoreCase(correo)) {
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

    public boolean modificarCategoria(String nombreActual, String nombreNuevo) {
        Categoria categoria = consultarCategoria(nombreActual);

        if (categoria == null) {
            return false;
        }

        if (!nombreActual.equalsIgnoreCase(nombreNuevo) && consultarCategoria(nombreNuevo) != null) {
            return false;
        }

        categoria.setNombre(nombreNuevo);
        return true;
    }

    public boolean borrarCategoria(String nombre) {
        Categoria categoria = consultarCategoria(nombre);

        if (categoria == null) {
            return false;
        }

        ArrayList<Item> itemsCategoria = new ArrayList<Item>(categoria.getItems());

        for (Item item : itemsCategoria) {
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

    public boolean modificarTipo(String nombreActual, String nombreNuevo) {
        Tipo tipo = consultarTipo(nombreActual);

        if (tipo == null) {
            return false;
        }

        if (!nombreActual.equalsIgnoreCase(nombreNuevo) && consultarTipo(nombreNuevo) != null) {
            return false;
        }

        tipo.setNombre(nombreNuevo);
        return true;
    }

    public boolean borrarTipo(String nombre) {
        Tipo tipo = consultarTipo(nombre);

        if (tipo == null) {
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
    public Prestamo crearPrestamo(String correoPrestatario, ArrayList<String> codigosItems, String mensajeAlerta,
            boolean esRecurrente, int tiempo, String fechaInicio) {

        Persona prestatario = consultarPersona(correoPrestatario);

        if (prestatario == null || codigosItems == null || codigosItems.isEmpty()) {
            return null;
        }

        ArrayList<Item> items = new ArrayList<Item>();

        for (String codigoItem : codigosItems) {
            Item item = consultarItem(codigoItem);

            if (item == null) {
                return null;
            }

            if (itemEstaPrestado(item)) {
                return null;
            }

            items.add(item);
        }

        Alerta alerta = new Alerta(mensajeAlerta, esRecurrente, tiempo, fechaInicio);
        Prestamo prestamo = new Prestamo(prestatario, items, alerta);

        listaPrestamos.add(prestamo);
        return prestamo;
    }

    public boolean agregarItemAPrestamo(int posicionPrestamo, String codigoItem) {
        Prestamo prestamo = obtenerPrestamoPorPosicion(posicionPrestamo);
        Item item = consultarItem(codigoItem);

        if (prestamo == null || item == null) {
            return false;
        }

        if (itemEstaPrestado(item)) {
            return false;
        }

        prestamo.agregarItem(item);
        return true;
    }

    public boolean eliminarItemDePrestamo(int posicionPrestamo, String codigoItem) {
        Prestamo prestamo = obtenerPrestamoPorPosicion(posicionPrestamo);
        Item item = consultarItem(codigoItem);

        if (prestamo == null || item == null) {
            return false;
        }

        prestamo.eliminarItem(item);
        return true;
    }

    public boolean retornarItemDePrestamo(int posicionPrestamo, String codigoItem) {
        Prestamo prestamo = obtenerPrestamoPorPosicion(posicionPrestamo);
        Item item = consultarItem(codigoItem);

        if (prestamo == null || item == null) {
            return false;
        }

        prestamo.eliminarItem(item);
        return true;
    }

    public boolean finalizarPrestamo(int posicionPrestamo) {
        Prestamo prestamo = obtenerPrestamoPorPosicion(posicionPrestamo);

        if (prestamo == null) {
            return false;
        }

        ArrayList<Item> itemsPrestados = new ArrayList<Item>(prestamo.getItemsPrestados());

        for (Item item : itemsPrestados) {
            prestamo.eliminarItem(item);
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
    // =========================================================
    public String reportePorUsuario() {
        String reporte = "REPORTE POR USUARIO\n";

        for (Persona persona : listaPersonas) {
            reporte += "\nUsuario: " + persona.getNombre() + "\n";
            reporte += "Telefono: " + persona.getTelefono() + "\n";
            reporte += "Correo: " + persona.getCorreoElectronico() + "\n";
            reporte += "Prestamos:\n";

            for (Prestamo prestamo : persona.getPrestamos()) {
                reporte += "- " + prestamo.toString() + "\n";
            }
        }

        return reporte;
    }

    public String reportePorItem() {
        String reporte = "REPORTE POR ITEM\n";

        for (Item item : listaItems) {
            reporte += "\nItem: " + item.toString() + "\n";
            reporte += "Descripcion: " + item.getDescripcion() + "\n";
            reporte += "Tipo: " + item.getTipo() + "\n";
            reporte += "Prestado: " + (itemEstaPrestado(item) ? "Si" : "No") + "\n";
        }

        return reporte;
    }

    public String reportePorCategoria() {
        String reporte = "REPORTE POR CATEGORIA\n";

        for (Categoria categoria : listaCategorias) {
            reporte += "\nCategoria: " + categoria.getNombre() + "\n";

            for (Item item : categoria.getItems()) {
                reporte += "- " + item.toString() + "\n";
            }
        }

        return reporte;
    }

    public String reportePorTipo() {
        String reporte = "REPORTE POR TIPO\n";

        for (Tipo tipo : listaTipos) {
            reporte += "\nTipo: " + tipo.getNombre() + "\n";

            for (Item item : tipo.getItems()) {
                reporte += "- " + item.toString() + "\n";
            }
        }

        return reporte;
    }
    // =========================================================
    private Prestamo obtenerPrestamoPorPosicion(int posicionPrestamo) {
        if (posicionPrestamo < 0 || posicionPrestamo >= listaPrestamos.size()) {
            return null;
        }

        return listaPrestamos.get(posicionPrestamo);
    }

    private boolean itemEstaPrestado(Item item) {
        for (Prestamo prestamo : listaPrestamos) {
            if (prestamo.getItemsPrestados().contains(item)) {
                return true;
            }
        }

        return false;
    }
}