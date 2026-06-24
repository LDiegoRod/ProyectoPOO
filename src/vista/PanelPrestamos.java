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
import logica.Item;
import logica.Prestamo;

public class PanelPrestamos extends JPanel {

    private PrestamoControlador controlador;

    private JTextField txtCorreoPersona;
    private JTextField txtCodigosItems;
    private JTextField txtMensajeAlerta;
    private JTextField txtEsRecurrente;
    private JTextField txtTiempo;
    private JTextField txtFechaInicio;
    private JTextField txtPosicionPrestamo;
    private JTextField txtCodigoItemIndividual;

    private JTextArea areaPrestamos;

    public PanelPrestamos(PrestamoControlador controlador) {
        this.controlador = controlador;

        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 5, 5));

        panelFormulario.add(new JLabel("Correo de la persona:"));
        txtCorreoPersona = new JTextField();
        panelFormulario.add(txtCorreoPersona);

        panelFormulario.add(new JLabel("Códigos de ítems separados por coma:"));
        txtCodigosItems = new JTextField();
        panelFormulario.add(txtCodigosItems);

        panelFormulario.add(new JLabel("Mensaje de alerta:"));
        txtMensajeAlerta = new JTextField();
        panelFormulario.add(txtMensajeAlerta);

        panelFormulario.add(new JLabel("¿Es recurrente? true/false:"));
        txtEsRecurrente = new JTextField();
        panelFormulario.add(txtEsRecurrente);

        panelFormulario.add(new JLabel("Tiempo de activación:"));
        txtTiempo = new JTextField();
        panelFormulario.add(txtTiempo);

        panelFormulario.add(new JLabel("Fecha inicio:"));
        txtFechaInicio = new JTextField();
        panelFormulario.add(txtFechaInicio);

        panelFormulario.add(new JLabel("Posición préstamo:"));
        txtPosicionPrestamo = new JTextField();
        panelFormulario.add(txtPosicionPrestamo);

        panelFormulario.add(new JLabel("Código de ítem individual:"));
        txtCodigoItemIndividual = new JTextField();
        panelFormulario.add(txtCodigoItemIndividual);

        JButton btnCrear = new JButton("Crear préstamo");
        JButton btnAgregarItem = new JButton("Agregar ítem");
        JButton btnRetornarItem = new JButton("Retornar ítem");
        JButton btnEliminarItem = new JButton("Eliminar ítem");
        JButton btnFinalizar = new JButton("Finalizar préstamo");
        JButton btnActualizar = new JButton("Actualizar lista");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCrear);
        panelBotones.add(btnAgregarItem);
        panelBotones.add(btnRetornarItem);
        panelBotones.add(btnEliminarItem);
        panelBotones.add(btnFinalizar);
        panelBotones.add(btnActualizar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        areaPrestamos = new JTextArea();
        areaPrestamos.setEditable(false);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaPrestamos), BorderLayout.CENTER);

        btnCrear.addActionListener(e -> crearPrestamo());
        btnAgregarItem.addActionListener(e -> agregarItemAPrestamo());
        btnRetornarItem.addActionListener(e -> retornarItemDePrestamo());
        btnEliminarItem.addActionListener(e -> eliminarItemDePrestamo());
        btnFinalizar.addActionListener(e -> finalizarPrestamo());
        btnActualizar.addActionListener(e -> actualizarLista());

        actualizarLista();
    }

    private void crearPrestamo() {
        String correo = txtCorreoPersona.getText().trim();
        ArrayList<String> codigosItems = obtenerCodigosItemsDesdeTexto();
        String mensajeAlerta = txtMensajeAlerta.getText().trim();
        String textoRecurrente = txtEsRecurrente.getText().trim();
        String textoTiempo = txtTiempo.getText().trim();
        String fechaInicio = txtFechaInicio.getText().trim();

        if (correo.isEmpty() || codigosItems.isEmpty() || mensajeAlerta.isEmpty()
                || textoRecurrente.isEmpty() || textoTiempo.isEmpty() || fechaInicio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los datos del préstamo.");
            return;
        }

        boolean esRecurrente = Boolean.parseBoolean(textoRecurrente);
        int tiempo;

        try {
            tiempo = Integer.parseInt(textoTiempo);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El tiempo debe ser un número entero.");
            return;
        }

        Prestamo prestamo = controlador.crearPrestamo(correo, codigosItems, mensajeAlerta, esRecurrente, tiempo,
                fechaInicio);

        if (prestamo != null) {
            JOptionPane.showMessageDialog(this, "Préstamo creado correctamente.");
            limpiarCampos();
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo crear el préstamo. Revise persona, ítems o que los ítems no estén prestados.");
        }
    }

    private void agregarItemAPrestamo() {
        Integer posicion = obtenerPosicionPrestamo();
        String codigoItem = txtCodigoItemIndividual.getText().trim();

        if (posicion == null || codigoItem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite posición del préstamo y código del ítem.");
            return;
        }

        boolean resultado = controlador.agregarItemAPrestamo(posicion, codigoItem);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Ítem agregado al préstamo.");
            limpiarCamposOperacion();
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo agregar el ítem. Revise la posición, el código o si el ítem ya está prestado.");
        }
    }

    private void retornarItemDePrestamo() {
        Integer posicion = obtenerPosicionPrestamo();
        String codigoItem = txtCodigoItemIndividual.getText().trim();

        if (posicion == null || codigoItem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite posición del préstamo y código del ítem.");
            return;
        }

        boolean resultado = controlador.retornarItemDePrestamo(posicion, codigoItem);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Ítem retornado correctamente.");
            limpiarCamposOperacion();
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo retornar el ítem.");
        }
    }

    private void eliminarItemDePrestamo() {
        Integer posicion = obtenerPosicionPrestamo();
        String codigoItem = txtCodigoItemIndividual.getText().trim();

        if (posicion == null || codigoItem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite posición del préstamo y código del ítem.");
            return;
        }

        boolean resultado = controlador.eliminarItemDePrestamo(posicion, codigoItem);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Ítem eliminado del préstamo.");
            limpiarCamposOperacion();
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el ítem del préstamo.");
        }
    }

    private void finalizarPrestamo() {
        Integer posicion = obtenerPosicionPrestamo();

        if (posicion == null) {
            JOptionPane.showMessageDialog(this, "Digite la posición del préstamo.");
            return;
        }

        boolean resultado = controlador.finalizarPrestamo(posicion);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Préstamo finalizado correctamente.");
            limpiarCamposOperacion();
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo finalizar el préstamo.");
        }
    }

    private ArrayList<String> obtenerCodigosItemsDesdeTexto() {
        ArrayList<String> codigos = new ArrayList<String>();

        String texto = txtCodigosItems.getText().trim();

        if (!texto.isEmpty()) {
            String[] partes = texto.split(",");

            for (String parte : partes) {
                String codigo = parte.trim();

                if (!codigo.isEmpty()) {
                    codigos.add(codigo);
                }
            }
        }

        return codigos;
    }

    private Integer obtenerPosicionPrestamo() {
        String texto = txtPosicionPrestamo.getText().trim();

        if (texto.isEmpty()) {
            return null;
        }

        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La posición del préstamo debe ser un número entero.");
            return null;
        }
    }

    private void actualizarLista() {
        ArrayList<Prestamo> prestamos = controlador.obtenerPrestamos();

        areaPrestamos.setText("PRÉSTAMOS REGISTRADOS\n\n");

        for (int i = 0; i < prestamos.size(); i++) {
            Prestamo prestamo = prestamos.get(i);

            areaPrestamos.append("Posición: " + i + "\n");

            if (prestamo.getPrestatario() != null) {
                areaPrestamos.append("Persona: " + prestamo.getPrestatario().getNombre() + "\n");
                areaPrestamos.append("Correo: " + prestamo.getPrestatario().getCorreoElectronico() + "\n");
            }

            areaPrestamos.append("Fecha préstamo: " + prestamo.getFechaPrestamo() + "\n");

            if (prestamo.getAlertaAsociada() != null) {
                areaPrestamos.append("Alerta: " + prestamo.getAlertaAsociada().toString() + "\n");
            }

            areaPrestamos.append("Ítems prestados:\n");

            for (Item item : prestamo.getItemsPrestados()) {
                areaPrestamos.append("- " + item.toString() + "\n");
            }

            areaPrestamos.append("-----------------------------\n");
        }
    }

    private void limpiarCampos() {
        txtCorreoPersona.setText("");
        txtCodigosItems.setText("");
        txtMensajeAlerta.setText("");
        txtEsRecurrente.setText("");
        txtTiempo.setText("");
        txtFechaInicio.setText("");
        txtPosicionPrestamo.setText("");
        txtCodigoItemIndividual.setText("");
    }

    private void limpiarCamposOperacion() {
        txtPosicionPrestamo.setText("");
        txtCodigoItemIndividual.setText("");
    }
}