package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nombre;
    private String descripcion;
    private Tipo tipo;
    private ArrayList<Categoria> categorias;
    private ArrayList<Prestamo> prestamos;

    public Item(String codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categorias = new ArrayList<Categoria>();
        this.prestamos = new ArrayList<Prestamo>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        if (this.tipo != null) {
            this.tipo.borrarItem(this);
        }

        this.tipo = tipo;

        if (tipo != null) {
            tipo.agregarItem(this);
        }
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public void agregarCategoria(Categoria categoria) {
        if (categoria != null && !categorias.contains(categoria)) {
            categorias.add(categoria);
            categoria.agregarItem(this);
        }
    }

    public void eliminarCategoria(Categoria categoria) {
        if (categoria != null) {
            categorias.remove(categoria);
            categoria.borrarItem(this);
        }
    }

    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        if (prestamo != null && !prestamos.contains(prestamo)) {
            prestamos.add(prestamo);
        }
    }

    public void borrarPrestamo(Prestamo prestamo) {
        if (prestamo != null) {
            prestamos.remove(prestamo);
        }
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Item)) {
            return false;
        }

        Item otro = (Item) obj;
        return Objects.equals(codigo, otro.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}