package logica;

import java.util.ArrayList;

public class Persona {

    private String nombre;
    private String telefono;
    private String correoElectronico;
    private ArrayList<Prestamo> prestamos;

    public Persona(String nombre, String telefono, String correoElectronico) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.prestamos = new ArrayList<Prestamo>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
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
        return nombre + " - " + telefono;
    }
}