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
