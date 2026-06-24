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
import logica.Item;

public class PanelItems extends JPanel {

    private PrestamoControlador controlador;

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtTipo;
    private JTextField txtCategorias;
    private JTextArea areaItems;

    public PanelItems(PrestamoControlador controlador) {
        this.controlador = controlador;

        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelFormulario.add(txtDescripcion);

        panelFormulario.add(new JLabel("Tipo:"));
        txtTipo = new JTextField();
        panelFormulario.add(txtTipo);

        panelFormulario.add(new JLabel("Categorías separadas por coma:"));
        txtCategorias = new JTextField();
        panelFormulario.add(txtCategorias);

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

        areaItems = new JTextArea();
        areaItems.setEditable(false);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaItems), BorderLayout.CENTER);

        btnCrear.addActionListener(e -> crearItem());
        btnModificar.addActionListener(e -> modificarItem());
        btnBorrar.addActionListener(e -> borrarItem());
        btnActualizar.addActionListener(e -> actualizarLista());

        actualizarLista();
    }

    private void crearItem() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String tipo = txtTipo.getText().trim();
        ArrayList<String> categorias = obtenerCategoriasDesdeTexto();

        if (codigo.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite código, nombre, descripción y tipo.");
            return;
        }

        boolean resultado = controlador.crearItem(codigo, nombre, descripcion, tipo, categorias);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Ítem creado correctamente.");
            limpiarCampos();
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo crear el ítem. Revise que el código no exista y que el tipo esté registrado.");
        }
    }

    private void modificarItem() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String tipo = txtTipo.getText().trim();
        ArrayList<String> categorias = obtenerCategoriasDesdeTexto();

        if (codigo.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite código, nombre, descripción y tipo.");
            return;
        }

        boolean resultado = controlador.modificarItem(codigo, nombre, descripcion, tipo, categorias);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Ítem modificado correctamente.");
            limpiarCampos();
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo modificar el ítem. Revise que el ítem exista y que el tipo esté registrado.");
        }
    }

    private void borrarItem() {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite el código del ítem a borrar.");
            return;
        }

        boolean resultado = controlador.borrarItem(codigo);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Ítem borrado correctamente.");
            limpiarCampos();
            actualizarLista();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo borrar el ítem. Puede que no exista o que esté prestado.");
        }
    }

    private ArrayList<String> obtenerCategoriasDesdeTexto() {
        ArrayList<String> categorias = new ArrayList<String>();

        String texto = txtCategorias.getText().trim();

        if (!texto.isEmpty()) {
            String[] partes = texto.split(",");

            for (String parte : partes) {
                String categoria = parte.trim();

                if (!categoria.isEmpty()) {
                    categorias.add(categoria);
                }
            }
        }

        return categorias;
    }

    private void actualizarLista() {
        ArrayList<Item> items = controlador.obtenerItems();

        areaItems.setText("ÍTEEMS REGISTRADOS\n\n");

        for (Item item : items) {
            areaItems.append("Código: " + item.getCodigo() + "\n");
            areaItems.append("Nombre: " + item.getNombre() + "\n");
            areaItems.append("Descripción: " + item.getDescripcion() + "\n");
            areaItems.append("Tipo: " + item.getTipo() + "\n");

            areaItems.append("Categorías: ");
            for (Categoria categoria : item.getCategorias()) {
                areaItems.append(categoria.getNombre() + " ");
            }

            areaItems.append("\nPréstamos asociados: " + item.getPrestamos().size() + "\n");
            areaItems.append("-----------------------------\n");
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtTipo.setText("");
        txtCategorias.setText("");
    }
}
