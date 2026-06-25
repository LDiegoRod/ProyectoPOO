package vista;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import control.PrestamoControlador;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String ARCHIVO_DATOS = "datos_prestamos.dat";

    private PrestamoControlador controlador;
    private JTabbedPane pestañas;

    public VentanaPrincipal() {
        controlador = PrestamoControlador.cargarDatos(ARCHIVO_DATOS);

        setTitle("Sistema de Control de Préstamos");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        pestañas = new JTabbedPane();

        pestañas.addTab("Tipos", new PanelTipos(controlador));
        pestañas.addTab("Categorías", new PanelCategorias(controlador));
        pestañas.addTab("Personas", new PanelPersonas(controlador));
        pestañas.addTab("Ítems", new PanelItems(controlador));
        pestañas.addTab("Préstamos", new PanelPrestamos(controlador));
        pestañas.addTab("Reportes", new PanelReportes(controlador));

        add(pestañas);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarYSalir();
            }
        });
    }

    private void guardarYSalir() {
        boolean guardado = controlador.guardarDatos(ARCHIVO_DATOS);

        if (guardado) {
            JOptionPane.showMessageDialog(this, "Datos guardados correctamente.");
            System.exit(0);
        } else {
            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "No se pudieron guardar los datos. ¿Desea salir de todas formas?",
                    "Error al guardar",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.setVisible(true);
            }
        });
    }
}