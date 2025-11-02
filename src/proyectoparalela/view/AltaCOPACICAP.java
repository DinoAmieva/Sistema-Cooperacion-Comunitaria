package proyectoparalela.view;

import proyectoparalela.controller.HabitanteController;
import proyectoparalela.controller.ExpedienteController;
import proyectoparalela.model.Habitante; // Importar el modelo
import proyectoparalela.utils.UIComponents;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AltaCOPACICAP extends JPanel {
    private final HabitanteController habitanteController;
    private final ExpedienteController expedienteController;

    // --- Declaración de TODOS los componentes de UI ---
    
    // Campos existentes
    private final JTextField txtNombre = UIComponents.createTextField("Nombre(s)");
    private final JTextField txtApellido = UIComponents.createTextField("Apellidos");
    private final JTextField txtEdad = UIComponents.createTextField("Edad");
    
    // Nuevos campos
    private final JTextField txtFechaNacimiento = UIComponents.createTextField("YYYY-MM-DD");
    private final JTextField txtLugarNacimiento = UIComponents.createTextField("Lugar de nacimiento");
    private final JTextField txtDireccion = UIComponents.createTextField("Domicilio actual");
    private final JComboBox<String> cmbEstadoCivil = new JComboBox<>(new String[]{
        "Soltero(a)", "Casado(a)", "Viudo(a)", "Divorciado(a)", "Unión Libre"
    });
    private final JTextField txtOcupacion = UIComponents.createTextField("Ocupación");
    private final JComboBox<String> cmbGradoEstudios = new JComboBox<>(new String[]{
        "Ninguno", "Primaria", "Secundaria", "Preparatoria", "Licenciatura", "Posgrado"
    });
    private final JTextField txtTelefono = UIComponents.createTextField("Teléfono");
    private final JTextField txtManzana = UIComponents.createTextField("Manzana");
    private final JCheckBox chkOriginario = new JCheckBox("Originario de Almaya");
    private final JTextField txtCertificadoComunero = UIComponents.createTextField("No. de Certificado");
    private final JComboBox<String> cmbTipoCertificado = new JComboBox<>(new String[]{"Normal", "Extemporánea"});

    // Campos de Login
    private final JTextField txtUsuario = UIComponents.createTextField("Nombre de Usuario");
    private final JPasswordField txtPassword = new JPasswordField();

    private final JButton btnRegistrar = UIComponents.primaryButton("Registrar y crear expediente");

    public AltaCOPACICAP(HabitanteController habitanteController, ExpedienteController expedienteController) {
        this.habitanteController = habitanteController;
        this.expedienteController = expedienteController;
        
        // Usar BorderLayout para el JScrollPane
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Construir el formulario y meterlo en un ScrollPane
        JScrollPane scrollPane = new JScrollPane(buildForm());
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        add(scrollPane, BorderLayout.CENTER);
        btnRegistrar.addActionListener(e -> onRegistrar());
    }

    /**
     * Construye el panel del formulario con todos los campos
     */
    private JComponent buildForm() {
        // Usar GridLayout con 2 columnas (etiqueta y campo)
        JPanel p = new JPanel(new GridLayout(0, 2, 10, 10)); // 0 filas, 2 cols, gaps de 10px
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createTitledBorder("Datos Personales (Formato COPACI)"));

        // Añadir todos los campos
        p.add(new JLabel("Nombre(s):")); p.add(txtNombre);
        p.add(new JLabel("Apellidos:")); p.add(txtApellido);
        p.add(new JLabel("Fecha Nacimiento (YYYY-MM-DD):")); p.add(txtFechaNacimiento);
        p.add(new JLabel("Edad:")); p.add(txtEdad);
        p.add(new JLabel("Lugar de Nacimiento:")); p.add(txtLugarNacimiento);
        p.add(new JLabel("Estado Civil:")); p.add(cmbEstadoCivil);
        p.add(new JLabel("Ocupación:")); p.add(txtOcupacion);
        p.add(new JLabel("Grado Máximo de Estudios:")); p.add(cmbGradoEstudios);
        p.add(new JLabel("Domicilio Actual:")); p.add(txtDireccion);
        p.add(new JLabel("Teléfono:")); p.add(txtTelefono);
        p.add(new JLabel("Manzana:")); p.add(txtManzana);
        p.add(new JLabel("Certificado de Comunero No.:")); p.add(txtCertificadoComunero);
        p.add(new JLabel("Tipo de Certificado:")); p.add(cmbTipoCertificado);
        
        // Checkbox y un label vacío para alinear
        p.add(chkOriginario); p.add(new JLabel("")); 

        // Separador para la sección de Login
        p.add(new JSeparator()); p.add(new JSeparator());
        p.add(new JLabel("Usuario (para Login):")); p.add(txtUsuario);
        p.add(new JLabel("Contraseña (para Login):")); p.add(txtPassword);
        p.add(new JSeparator()); p.add(new JSeparator());

        // Botón
        p.add(new JLabel("")); // Espacio vacío
        p.add(btnRegistrar);
        
        return p;
    }

    /**
     * Acción del botón "Registrar".
     * Recolecta TODOS los datos, crea un objeto Habitante y lo envía al controlador.
     */
    private void onRegistrar() {
        try {
            // 1. Crear el objeto Habitante
            Habitante h = new Habitante();
            
            // 2. Recolectar datos del formulario y validarlos
            
            // Campos de texto básicos
            h.setNombre(txtNombre.getText());
            h.setApellido(txtApellido.getText());
            h.setLugarNacimiento(txtLugarNacimiento.getText());
            h.setOcupacion(txtOcupacion.getText());
            h.setDireccion(txtDireccion.getText());
            h.setTelefono(txtTelefono.getText());
            h.setManzana(txtManzana.getText());
            h.setCertificadoComunero(txtCertificadoComunero.getText());
            h.setUsuario(txtUsuario.getText());
            h.setPassword(new String(txtPassword.getPassword()));

            // Campos con posible error de formato
            try {
                h.setEdad(Integer.parseInt(txtEdad.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El campo 'Edad' debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                if (!txtFechaNacimiento.getText().isEmpty()) {
                    h.setFechaNacimiento(LocalDate.parse(txtFechaNacimiento.getText()));
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "El formato de 'Fecha Nacimiento' debe ser YYYY-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Campos de ComboBox/CheckBox
            h.setEstadoCivil((String) cmbEstadoCivil.getSelectedItem());
            h.setGradoEstudios((String) cmbGradoEstudios.getSelectedItem());
            h.setTipoCertificado((String) cmbTipoCertificado.getSelectedItem());
            h.setOriginarioAlmaya(chkOriginario.isSelected());

            // 3. Enviar el objeto al controlador
            long idHab = habitanteController.crearHabitante(h);
            
            // 4. Crear el expediente asociado
            expedienteController.crear(idHab, "COPACI-CAP", "Alta inicial del habitante");
            
            JOptionPane.showMessageDialog(this, "Habitante registrado con éxito. ID = " + idHab);
            
            // 5. Limpiar formulario (Opcional, pero recomendado)
            limpiarFormulario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    /**
     * Limpia todos los campos del formulario después de un registro exitoso.
     */
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEdad.setText("");
        txtFechaNacimiento.setText("");
        txtLugarNacimiento.setText("");
        txtDireccion.setText("");
        cmbEstadoCivil.setSelectedIndex(0);
        txtOcupacion.setText("");
        cmbGradoEstudios.setSelectedIndex(0);
        txtTelefono.setText("");
        txtManzana.setText("");
        chkOriginario.setSelected(false);
        txtCertificadoComunero.setText("");
        cmbTipoCertificado.setSelectedIndex(0);
        txtUsuario.setText("");
        txtPassword.setText("");
    }
}


