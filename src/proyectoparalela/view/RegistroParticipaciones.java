package proyectoparalela.view;

import proyectoparalela.controller.FaenaController;
import proyectoparalela.utils.UIComponents;

import javax.swing.*;
import java.awt.*;

public class RegistroParticipaciones extends JPanel {
    private final FaenaController faenaController;
    private final JTextField txtHabitanteId = UIComponents.createTextField("Habitante ID");
    private final JTextField txtTipo = UIComponents.createTextField("Tipo de Faena");
    private final JTextField txtHoras = UIComponents.createTextField("Horas Trabajadas");
    private final JButton btnAgregar = UIComponents.primaryButton("Agregar participación");

    public RegistroParticipaciones(FaenaController faenaController) {
        this.faenaController = faenaController;
        setLayout(new BorderLayout());
        add(buildForm(), BorderLayout.CENTER);
        btnAgregar.addActionListener(e -> onAgregar());
    }

    private JComponent buildForm() {
        JPanel p = new JPanel(new GridLayout(0,2,12,12));
        p.add(new JLabel("Habitante ID")); p.add(txtHabitanteId);
        p.add(new JLabel("Tipo Faena")); p.add(txtTipo);
        p.add(new JLabel("Horas")); p.add(txtHoras);
        p.add(new JLabel("")); p.add(btnAgregar);
        return p;
    }

    private void onAgregar() {
        try {
            long habitanteId = Long.parseLong(txtHabitanteId.getText());
            String tipo = txtTipo.getText();
            Integer horas = Integer.parseInt(txtHoras.getText());
            long id = faenaController.crear(habitanteId, tipo, null, horas);
            JOptionPane.showMessageDialog(this, "Faena registrada ID=" + id);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}



