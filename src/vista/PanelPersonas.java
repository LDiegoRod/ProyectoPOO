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
import logica.Persona;

public class PanelPersonas extends JPanel {

    private PrestamoControlador controlador;

    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtCorreoNuevo;
    private JTextArea areaPersonas;

    public PanelPersonas(PrestamoControlador controlador) {
        this.controlador = controlador;

        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Correo actual:"));
        txtCorreo = new JTextField();
        panelFormulario.add(txtCorreo);

        panelFormulario.add(new JLabel("Correo nuevo:"));
        txtCorreoNuevo = new JTextField();
        panelFormulario.add(txtCorreoNuevo);

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

        areaPersonas = new JTextArea();
        areaPersonas.setEditable(false);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaPersonas), BorderLayout.CENTER);

        btnCrear.addActionListener(e -> crearPersona());
        btnModificar.addActionListener(e -> modificarPersona());
        btnBorrar.addActionListener(e -> borrarPersona());
        btnActualizar.addActionListener(e -> actualizarLista());

        actualizarLista();
    }

    private void crearPersona() {
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite nombre, teléfono y correo.");
            return;
        }

        boolean resultado = controlador.crearPersona(nombre, telefono, correo);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Persona creada correctamente.");
            limpiarCampos();
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo crear la persona. Puede que el correo ya exista.");
        }
    }

    private void modificarPersona() {
        String correoActual = txtCorreo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correoNuevo = txtCorreoNuevo.getText().trim();

        if (correoActual.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correoNuevo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite correo actual, nombre, teléfono y correo nuevo.");
            return;
        }

        boolean resultado = controlador.modificarPersona(correoActual, nombre, telefono, correoNuevo);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Persona modificada correctamente.");
            limpiarCampos();
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo modificar la persona.");
        }
    }

    private void borrarPersona() {
        String correo = txtCorreo.getText().trim();

        if (correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite el correo de la persona a borrar.");
            return;
        }

        boolean resultado = controlador.borrarPersona(correo);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Persona borrada correctamente.");
            limpiarCampos();
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo borrar la persona. Puede que no exista o que tenga préstamos asociados.");
        }
    }

    private void actualizarLista() {
        ArrayList<Persona> personas = controlador.obtenerPersonas();

        areaPersonas.setText("PERSONAS REGISTRADAS\n\n");

        for (Persona persona : personas) {
            areaPersonas.append("Nombre: " + persona.getNombre() + "\n");
            areaPersonas.append("Teléfono: " + persona.getTelefono() + "\n");
            areaPersonas.append("Correo: " + persona.getCorreoElectronico() + "\n");
            areaPersonas.append("Préstamos asociados: " + persona.getPrestamos().size() + "\n");
            areaPersonas.append("-----------------------------\n");
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtCorreoNuevo.setText("");
    }
}