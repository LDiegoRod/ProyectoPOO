package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.PrestamoControlador;
import logica.Categoria;

public class PanelCategorias extends JPanel {

    private PrestamoControlador controlador;

    private JTextField txtNombre;
    private JTextField txtNombreNuevo;
    private JTextArea areaCategorias;

    public PanelCategorias(PrestamoControlador controlador) {
        this.controlador = controlador;

        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 5, 5));

        panelFormulario.add(new JLabel("Nombre de la categoría:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Nuevo nombre:"));
        txtNombreNuevo = new JTextField();
        panelFormulario.add(txtNombreNuevo);

        JButton btnCrear = new JButton("Crear");
        JButton btnModificar = new JButton("Modificar");
        JButton btnBorrar = new JButton("Borrar");
        JButton btnActualizar = new JButton("Actualizar lista");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCrear);
        panelBotones.add(btnModificar);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnActualizar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        areaCategorias = new JTextArea();
        areaCategorias.setEditable(false);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaCategorias), BorderLayout.CENTER);

        btnCrear.addActionListener(e -> crearCategoria());
        btnModificar.addActionListener(e -> modificarCategoria());
        btnBorrar.addActionListener(e -> borrarCategoria());
        btnActualizar.addActionListener(e -> actualizarLista());

        actualizarLista();
    }

    private void crearCategoria() {
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite el nombre de la categoría.");
            return;
        }

        boolean resultado = controlador.crearCategoria(nombre);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Categoría creada correctamente.");
            txtNombre.setText("");
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo crear la categoría. Puede que ya exista.");
        }
    }

    private void modificarCategoria() {
        String nombreActual = txtNombre.getText().trim();
        String nombreNuevo = txtNombreNuevo.getText().trim();

        if (nombreActual.isEmpty() || nombreNuevo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite el nombre actual y el nuevo nombre.");
            return;
        }

        boolean resultado = controlador.modificarCategoria(nombreActual, nombreNuevo);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Categoría modificada correctamente.");
            txtNombre.setText("");
            txtNombreNuevo.setText("");
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo modificar la categoría.");
        }
    }

    private void borrarCategoria() {
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite el nombre de la categoría a borrar.");
            return;
        }

        boolean resultado = controlador.borrarCategoria(nombre);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Categoría borrada correctamente.");
            txtNombre.setText("");
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo borrar la categoría.");
        }
    }

    private void actualizarLista() {
        ArrayList<Categoria> categorias = controlador.obtenerCategorias();

        areaCategorias.setText("CATEGORÍAS REGISTRADAS\n\n");

        for (Categoria categoria : categorias) {
            areaCategorias.append("- " + categoria.getNombre() + "\n");
        }
    }
}