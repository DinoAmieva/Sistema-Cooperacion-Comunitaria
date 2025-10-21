package proyectoparalela.view;

import proyectoparalela.controller.ExpedienteController;
import proyectoparalela.model.Expediente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import proyectoparalela.utils.UIComponents;
import proyectoparalela.utils.TableStyler;
import java.awt.*;
import java.util.List;

public class ConsultaExpediente extends JPanel {
    private final ExpedienteController expedienteController;
    private final JTextField txtHabitanteId = UIComponents.createTextField("Habitante ID");
    private final JButton btnBuscar = UIComponents.primaryButton("Buscar");
    private final JTable table = new JTable(new DefaultTableModel(new Object[]{"ID","Tipo","Estado","Fecha"}, 0));

    public ConsultaExpediente(ExpedienteController expedienteController) {
        this.expedienteController = expedienteController;
        setLayout(new BorderLayout());
        add(buildTop(), BorderLayout.NORTH);
        JScrollPane sp = new JScrollPane(table);
        TableStyler.applyModernStyle(table);
        add(sp, BorderLayout.CENTER);
        btnBuscar.addActionListener(e -> onBuscar());
    }

    private JComponent buildTop() {
        JPanel p = new JPanel(new BorderLayout(8,8));
        p.add(new JLabel("Habitante ID:"), BorderLayout.WEST);
        p.add(txtHabitanteId, BorderLayout.CENTER);
        p.add(btnBuscar, BorderLayout.EAST);
        return p;
    }

    private void onBuscar() {
        try {
            long habitanteId = Long.parseLong(txtHabitanteId.getText());
            List<Expediente> list = expedienteController.porHabitante(habitanteId);
            DefaultTableModel m = (DefaultTableModel) table.getModel();
            m.setRowCount(0);
            for (Expediente e : list) {
                m.addRow(new Object[]{e.getId(), e.getTipoExpediente(), e.getEstado(), e.getFechaCreacion()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}



