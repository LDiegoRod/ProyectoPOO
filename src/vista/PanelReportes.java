package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import control.PrestamoControlador;

public class PanelReportes extends JPanel {

    private PrestamoControlador controlador;
    private JTextArea areaReportes;

    public PanelReportes(PrestamoControlador controlador) {
        this.controlador = controlador;

        setLayout(new BorderLayout());

        JButton btnReporteUsuario = new JButton("Reporte por usuario");
        JButton btnReporteItem = new JButton("Reporte por ítem");
        JButton btnReporteCategoria = new JButton("Reporte por categoría");
        JButton btnReporteTipo = new JButton("Reporte por tipo");
        JButton btnLimpiar = new JButton("Limpiar");

        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 5, 5));

        panelBotones.add(btnReporteUsuario);
        panelBotones.add(btnReporteItem);
        panelBotones.add(btnReporteCategoria);
        panelBotones.add(btnReporteTipo);
        panelBotones.add(btnLimpiar);

        areaReportes = new JTextArea();
        areaReportes.setEditable(false);

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(areaReportes), BorderLayout.CENTER);

        btnReporteUsuario.addActionListener(e -> mostrarReportePorUsuario());
        btnReporteItem.addActionListener(e -> mostrarReportePorItem());
        btnReporteCategoria.addActionListener(e -> mostrarReportePorCategoria());
        btnReporteTipo.addActionListener(e -> mostrarReportePorTipo());
        btnLimpiar.addActionListener(e -> limpiarReporte());
    }

    private void mostrarReportePorUsuario() {
        areaReportes.setText(controlador.reportePorUsuario());
    }

    private void mostrarReportePorItem() {
        areaReportes.setText(controlador.reportePorItem());
    }

    private void mostrarReportePorCategoria() {
        areaReportes.setText(controlador.reportePorCategoria());
    }

    private void mostrarReportePorTipo() {
        areaReportes.setText(controlador.reportePorTipo());
    }

    private void limpiarReporte() {
        areaReportes.setText("");
    }
}