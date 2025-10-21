package proyectoparalela.view;

import proyectoparalela.controller.CooperacionController;
import proyectoparalela.model.Cooperacion;
import proyectoparalela.utils.UIComponents;
import proyectoparalela.utils.TableStyler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class RegistroMensualCooperaciones extends JPanel {
    private final CooperacionController cooperacionController;
    private final JComboBox<Integer> cmbAño = new JComboBox<>();
    private final JComboBox<Integer> cmbMes = new JComboBox<>();
    private final JButton btnGenerar = UIComponents.primaryButton("Generar reporte mensual");
    private final JTable table = new JTable(new DefaultTableModel(new Object[]{"ID", "Habitante ID", "Tipo", "Descripción", "Fecha", "Puntos"}, 0));
    private final JLabel lblEstadisticas = new JLabel("Selecciona un mes y año para generar el reporte");

    public RegistroMensualCooperaciones(CooperacionController cooperacionController) {
        this.cooperacionController = cooperacionController;
        setLayout(new BorderLayout());
        add(buildTop(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildBottom(), BorderLayout.SOUTH);
        setupEventHandlers();
        initializeComboBoxes();
    }

    private JComponent buildTop() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.add(new JLabel("Registro mensual y reportes"), BorderLayout.NORTH);
        
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        controls.add(new JLabel("Año:"));
        controls.add(cmbAño);
        controls.add(new JLabel("Mes:"));
        controls.add(cmbMes);
        controls.add(btnGenerar);
        
        p.add(controls, BorderLayout.CENTER);
        return p;
    }

    private JComponent buildCenter() {
        JPanel p = new JPanel(new BorderLayout());
        
        // Tabla de resultados
        JScrollPane scrollPane = new JScrollPane(table);
        TableStyler.applyModernStyle(table);
        p.add(scrollPane, BorderLayout.CENTER);
        
        // Estadísticas
        p.add(lblEstadisticas, BorderLayout.SOUTH);
        
        return p;
    }

    private JComponent buildBottom() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnDatosPrueba = new JButton("Crear datos de prueba");
        btnDatosPrueba.addActionListener(e -> crearDatosPrueba());
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarReporte());
        p.add(btnDatosPrueba);
        p.add(btnLimpiar);
        return p;
    }

    private void setupEventHandlers() {
        btnGenerar.addActionListener(e -> generarReporte());
    }

    private void initializeComboBoxes() {
        // Años (2020-2030)
        for (int año = 2020; año <= 2030; año++) {
            cmbAño.addItem(año);
        }
        cmbAño.setSelectedItem(LocalDate.now().getYear());
        
        // Meses
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                         "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (int i = 1; i <= 12; i++) {
            cmbMes.addItem(i);
        }
        cmbMes.setSelectedItem(LocalDate.now().getMonthValue());
    }

    private void generarReporte() {
        try {
            int año = (Integer) cmbAño.getSelectedItem();
            int mes = (Integer) cmbMes.getSelectedItem();
            
            // Generar reporte
            List<Cooperacion> cooperaciones = cooperacionController.generarReporteMensual(año, mes);
            
            // Actualizar tabla
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            
            for (Cooperacion c : cooperaciones) {
                model.addRow(new Object[]{
                    c.getId(),
                    c.getHabitanteId(),
                    c.getTipoCooperacion(),
                    c.getDescripcion(),
                    c.getFechaCooperacion(),
                    c.getPuntos()
                });
            }
            
            // Actualizar estadísticas
            int totalCooperaciones = cooperacionController.contarCooperacionesPorMes(año, mes);
            int totalPuntos = cooperacionController.sumarPuntosPorMes(año, mes);
            
            String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                             "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
            
            lblEstadisticas.setText(String.format(
                "Reporte de %s %d: %d cooperaciones, %d puntos totales",
                meses[mes-1], año, totalCooperaciones, totalPuntos
            ));
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al generar reporte: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarReporte() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        lblEstadisticas.setText("Selecciona un mes y año para generar el reporte");
    }

    private void crearDatosPrueba() {
        try {
            cooperacionController.crearCooperacionesDePrueba();
            JOptionPane.showMessageDialog(this, 
                "Datos de prueba creados exitosamente.\nAhora puedes generar el reporte del mes actual.", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear datos de prueba: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}




