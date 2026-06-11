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