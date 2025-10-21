package proyectoparalela.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginWindow extends JFrame {
    private final JTextField txtUsuario = new JTextField();
    private final JPasswordField txtContrasena = new JPasswordField();
    private final JButton btnEntrar = new JButton("ENTRAR");
    private final JButton btnRegistrarse = new JButton("REGISTRARSE");
    private final JLabel lblTitulo = new JLabel("INICIAR SESIÓN");
    private final JLabel lblUsuario = new JLabel("USUARIO");
    private final JLabel lblContrasena = new JLabel("CONTRASEÑA");
    private final JLabel lblCooperaTec = new JLabel("CooperaTec");
    
    private LoginCallback callback;

    public interface LoginCallback {
        void onLoginSuccess();
        void onLoginFailure(String message);
    }

    public LoginWindow(LoginCallback callback) {
        this.callback = callback;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        applyStyling();
    }

    private void initializeComponents() {
        setTitle("CooperaTec - Sistema de Cooperación Comunitaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Panel izquierdo (formulario)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(new javax.swing.border.EmptyBorder(60, 80, 60, 40));
        
        // Logo y título
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        
        // Logo con círculos
        JPanel logoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Círculo exterior
                g2d.setColor(new Color(33, 150, 243));
                g2d.fillOval(5, 5, 20, 20);
                
                // Círculo interior
                g2d.setColor(new Color(100, 181, 246));
                g2d.fillOval(8, 8, 14, 14);
            }
        };
        logoPanel.setPreferredSize(new Dimension(30, 30));
        logoPanel.setBackground(Color.WHITE);
        
        lblCooperaTec.setFont(new Font("Arial", Font.BOLD, 18));
        lblCooperaTec.setForeground(Color.BLACK);
        
        headerPanel.add(logoPanel);
        headerPanel.add(lblCooperaTec);
        
        // Formulario de login
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        
        // Título
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(lblTitulo, gbc);
        
        // Usuario
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsuario.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(lblUsuario, gbc);
        
        txtUsuario.setPreferredSize(new Dimension(300, 40));
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new javax.swing.border.EmptyBorder(10, 15, 10, 15)
        ));
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(txtUsuario, gbc);
        
        // Contraseña
        lblContrasena.setFont(new Font("Arial", Font.BOLD, 14));
        lblContrasena.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(lblContrasena, gbc);
        
        txtContrasena.setPreferredSize(new Dimension(300, 40));
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new javax.swing.border.EmptyBorder(10, 15, 10, 15)
        ));
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(txtContrasena, gbc);
        
        // Botones
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        btnEntrar.setPreferredSize(new Dimension(300, 45));
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEntrar.setBackground(new Color(33, 150, 243));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setFocusPainted(false);
        
        btnRegistrarse.setPreferredSize(new Dimension(300, 45));
        btnRegistrarse.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarse.setBackground(new Color(33, 150, 243));
        btnRegistrarse.setForeground(Color.WHITE);
        btnRegistrarse.setBorderPainted(false);
        btnRegistrarse.setFocusPainted(false);
        
        buttonPanel.add(btnEntrar);
        buttonPanel.add(btnRegistrarse);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);
        
        leftPanel.add(headerPanel, BorderLayout.NORTH);
        leftPanel.add(formPanel, BorderLayout.CENTER);
        
        // Panel derecho (decorativo)
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradiente de fondo
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(33, 150, 243),
                    0, getHeight(), new Color(100, 181, 246)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Formas decorativas
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillOval(50, 100, 120, 120);
                g2d.fillOval(200, 300, 80, 80);
                g2d.fillOval(150, 450, 100, 100);
                
                // Logo grande
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 120));
                FontMetrics fm = g2d.getFontMetrics();
                String text = "D";
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 50;
                g2d.drawString(text, x, y);
                
                // Texto CooperaTec
                g2d.setFont(new Font("Arial", Font.BOLD, 24));
                fm = g2d.getFontMetrics();
                text = "CooperaTec";
                x = (getWidth() - fm.stringWidth(text)) / 2;
                y = y + 80;
                g2d.drawString(text, x, y);
            }
        };
        rightPanel.setPreferredSize(new Dimension(400, 0));
        
        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        btnEntrar.addActionListener(e -> performLogin());
        btnRegistrarse.addActionListener(e -> showRegisterMessage());
        
        // Enter key para login
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        };
        
        txtUsuario.addKeyListener(enterKeyListener);
        txtContrasena.addKeyListener(enterKeyListener);
    }

    private void applyStyling() {
        // Efectos hover para botones
        btnEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrar.setBackground(new Color(25, 118, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrar.setBackground(new Color(33, 150, 243));
            }
        });
        
        btnRegistrarse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistrarse.setBackground(new Color(25, 118, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistrarse.setBackground(new Color(33, 150, 243));
            }
        });
    }

    private void performLogin() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, complete todos los campos.", 
                "Campos Vacíos", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (usuario.equals("Admin") && contrasena.equals("123")) {
            JOptionPane.showMessageDialog(this, 
                "¡Bienvenido, Administrador!", 
                "Login Exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
            if (callback != null) {
                callback.onLoginSuccess();
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Usuario o contraseña incorrectos.\nUsuario: Admin\nContraseña: 123", 
                "Error de Login", 
                JOptionPane.ERROR_MESSAGE);
            if (callback != null) {
                callback.onLoginFailure("Credenciales incorrectas");
            }
        }
    }

    private void showRegisterMessage() {
        JOptionPane.showMessageDialog(this, 
            "El registro de nuevos administradores debe ser realizado por el administrador principal.", 
            "Registro", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void showLogin() {
        setVisible(true);
        txtUsuario.requestFocus();
    }
}
