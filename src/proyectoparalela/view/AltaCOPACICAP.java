package proyectoparalela.view;

import proyectoparalela.controller.HabitanteController;
import proyectoparalela.controller.ExpedienteController;

import javax.swing.*;
import java.awt.*;
import proyectoparalela.utils.UIComponents;

public class AltaCOPACICAP extends JPanel {
    private final HabitanteController habitanteController;
    private final ExpedienteController expedienteController;

    private final JTextField txtNombre = UIComponents.createTextField("Nombre");
    private final JTextField txtApellido = UIComponents.createTextField("Apellido");
    private final JTextField txtEdad = UIComponents.createTextField("Edad");
    private final JButton btnRegistrar = UIComponents.primaryButton("Registrar y crear expediente");

    public AltaCOPACICAP(HabitanteController habitanteController, ExpedienteController expedienteController) {
        this.habitanteController = habitanteController;
        this.expedienteController = expedienteController;
        setLayout(new BorderLayout());
        add(buildForm(), BorderLayout.CENTER);
        btnRegistrar.addActionListener(e -> onRegistrar());
    }

    private JComponent buildForm() {
        JPanel p = new JPanel(new GridLayout(0,2,12,12));
        p.add(new JLabel("Nombre")); p.add(txtNombre);
        p.add(new JLabel("Apellido")); p.add(txtApellido);
        p.add(new JLabel("Edad")); p.add(txtEdad);
        p.add(new JLabel("")); p.add(btnRegistrar);
        return p;
    }

    private void onRegistrar() {
        try {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            Integer edad = Integer.parseInt(txtEdad.getText());
            long idHab = habitanteController.crearHabitante(nombre, apellido, edad, null, null);
            expedienteController.crear(idHab, "COPACI-CAP", "Alta inicial");
            JOptionPane.showMessageDialog(this, "Registrado con éxito, ID=" + idHab);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}



