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
import logica.Tipo;

public class PanelTipos extends JPanel {

    private PrestamoControlador controlador;

    private JTextField txtNombre;
    private JTextField txtNombreNuevo;
    private JTextArea areaTipos;

    public PanelTipos(PrestamoControlador controlador) {
        this.controlador = controlador;

        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 5, 5));

        panelFormulario.add(new JLabel("Nombre del tipo:"));
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

        areaTipos = new JTextArea();
        areaTipos.setEditable(false);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaTipos), BorderLayout.CENTER);

        btnCrear.addActionListener(e -> crearTipo());
        btnModificar.addActionListener(e -> modificarTipo());
        btnBorrar.addActionListener(e -> borrarTipo());
        btnActualizar.addActionListener(e -> actualizarLista());

        actualizarLista();
    }

    private void crearTipo() {
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite el nombre del tipo.");
            return;
        }

        boolean resultado = controlador.crearTipo(nombre);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Tipo creado correctamente.");
            txtNombre.setText("");
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo crear el tipo. Puede que ya exista.");
        }
    }

    private void modificarTipo() {
        String nombreActual = txtNombre.getText().trim();
        String nombreNuevo = txtNombreNuevo.getText().trim();

        if (nombreActual.isEmpty() || nombreNuevo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite el nombre actual y el nuevo nombre.");
            return;
        }

        boolean resultado = controlador.modificarTipo(nombreActual, nombreNuevo);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Tipo modificado correctamente.");
            txtNombre.setText("");
            txtNombreNuevo.setText("");
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo modificar el tipo.");
        }
    }

    private void borrarTipo() {
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite el nombre del tipo a borrar.");
            return;
        }

        boolean resultado = controlador.borrarTipo(nombre);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Tipo borrado correctamente.");
            txtNombre.setText("");
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo borrar el tipo. Puede que no exista o que tenga ítems asociados.");
        }
    }

    private void actualizarLista() {
        ArrayList<Tipo> tipos = controlador.obtenerTipos();

        areaTipos.setText("TIPOS REGISTRADOS\n\n");

        for (Tipo tipo : tipos) {
            areaTipos.append("- " + tipo.getNombre() + "\n");
        }
    }
}