package proyectoparalela.view;

import proyectoparalela.controller.*;
import proyectoparalela.utils.Observable;
import proyectoparalela.utils.ThemeManager;
import proyectoparalela.utils.UIComponents;
// import proyectoparalela.utils.ChartFactory;
// import proyectoparalela.utils.ThreadStatusBadge;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final JLabel statusLabel = new JLabel("Listo", SwingConstants.LEFT);
    private final JTabbedPane tabs = new JTabbedPane();

    public MainWindow(Observable<String> notifier,
                      HabitanteController habitanteController,
                      ExpedienteController expedienteController,
                      CooperacionController cooperacionController,
                      FaenaController faenaController,
                      Object threadManager) {
        super("Sistema de Cooperación Comunitaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1400, 900));
        setLayout(ThemeManager.defaultBorderLayout());

        ThemeManager.applyLightTheme();

        setJMenuBar(buildMenuBar());

        // Sidebar estilo referencia
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(new Color(10,34,64));
        sidebar.setPreferredSize(new Dimension(220, 0));
        JLabel brand = new JLabel("CooperaTec", SwingConstants.LEFT);
        brand.setForeground(Color.WHITE);
        brand.setBorder(new javax.swing.border.EmptyBorder(16,16,16,16));
        brand.setFont(brand.getFont().deriveFont(Font.BOLD, 20f));
        sidebar.add(brand, BorderLayout.NORTH);

        JPanel nav = new JPanel();
        nav.setLayout(new GridLayout(0,1,0,6));
        nav.setBackground(new Color(10,34,64));
        JButton btnDash = UIComponents.sidebarButton("Dashboard");
        JButton btnAlta = UIComponents.sidebarButton("Alta COPACI/CAP");
        JButton btnMensual = UIComponents.sidebarButton("Registro Mensual");
        JButton btnPart = UIComponents.sidebarButton("Participaciones");
        JButton btnConsulta = UIComponents.sidebarButton("Consulta Expediente");
        nav.add(btnDash); nav.add(btnAlta); nav.add(btnMensual); nav.add(btnPart); nav.add(btnConsulta);
        sidebar.add(nav, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);

        // Contenido central con tabs invisibles (navegados vía sidebar)
        tabs.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        tabs.setForeground(ThemeManager.TEXT_PRIMARY);
        tabs.setBackground(ThemeManager.BACKGROUND);
        tabs.addTab("Dashboard", buildDashboard());
        tabs.addTab("Alta COPACI/CAP", new AltaCOPACICAP(habitanteController, expedienteController));
        tabs.addTab("Registro Mensual", new RegistroMensualCooperaciones(cooperacionController));
        tabs.addTab("Participaciones", new RegistroParticipaciones(faenaController));
        tabs.addTab("Consulta Expediente", new ConsultaExpediente(expedienteController));
        add(tabs, BorderLayout.CENTER);

        btnDash.addActionListener(e -> tabs.setSelectedIndex(0));
        btnAlta.addActionListener(e -> tabs.setSelectedIndex(1));
        btnMensual.addActionListener(e -> tabs.setSelectedIndex(2));
        btnPart.addActionListener(e -> tabs.setSelectedIndex(3));
        btnConsulta.addActionListener(e -> tabs.setSelectedIndex(4));

        add(buildStatusBar(), BorderLayout.SOUTH);

        // Subscribir status
        notifier.subscribe(event -> statusLabel.setText(event));

        pack();
    }

    private JMenuBar buildMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem salir = new JMenuItem("Salir");
        salir.addActionListener(e -> System.exit(0));
        menuArchivo.add(salir);

        JMenu menuVer = new JMenu("Ver");
        JMenuItem dashboardItem = new JMenuItem("Dashboard");
        dashboardItem.addActionListener(e -> tabs.setSelectedIndex(0));
        menuVer.add(dashboardItem);

        bar.add(menuArchivo);
        bar.add(menuVer);
        return bar;
    }

    private JPanel buildDashboard() {
        JPanel panel = new JPanel(new BorderLayout(16, 16));
        panel.setBorder(new javax.swing.border.EmptyBorder(16, 16, 16, 16));
        panel.setOpaque(false);
        
        // Header con búsqueda
        JPanel header = new JPanel(new BorderLayout(8,8));
        header.setOpaque(false);
        JLabel title = new JLabel("Dashboard - Estadísticas en tiempo real", SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        title.setForeground(ThemeManager.TEXT_PRIMARY);
        JTextField search = new JTextField();
        search.putClientProperty("JTextField.placeholderText", "Buscar...");
        search.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,0,0,40)),
                new javax.swing.border.EmptyBorder(10,12,10,12)));
        header.add(title, BorderLayout.WEST);
        header.add(search, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);
        
        // Panel principal con scroll
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new javax.swing.border.EmptyBorder(0, 0, 16, 0));
        
        // Cards de métricas
        JPanel metricsPanel = new JPanel(new GridLayout(2, 2, 12, 12));
        metricsPanel.setOpaque(false);
        metricsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        
        // Card 1: Total Habitantes
        JPanel card1 = UIComponents.createCardPanel();
        JLabel label1 = new JLabel("Total Habitantes", SwingConstants.CENTER);
        label1.setFont(label1.getFont().deriveFont(Font.BOLD, 14f));
        label1.setForeground(ThemeManager.TEXT_SECONDARY);
        JLabel value1 = new JLabel("1,247", SwingConstants.CENTER);
        value1.setFont(value1.getFont().deriveFont(Font.BOLD, 28f));
        value1.setForeground(ThemeManager.PRIMARY);
        card1.setLayout(new BorderLayout());
        card1.add(label1, BorderLayout.NORTH);
        card1.add(value1, BorderLayout.CENTER);
        metricsPanel.add(card1);
        
        // Card 2: Cooperaciones Activas
        JPanel card2 = UIComponents.createCardPanel();
        JLabel label2 = new JLabel("Cooperaciones Activas", SwingConstants.CENTER);
        label2.setFont(label2.getFont().deriveFont(Font.BOLD, 14f));
        label2.setForeground(ThemeManager.TEXT_SECONDARY);
        JLabel value2 = new JLabel("89", SwingConstants.CENTER);
        value2.setFont(value2.getFont().deriveFont(Font.BOLD, 28f));
        value2.setForeground(new Color(76, 175, 80));
        card2.setLayout(new BorderLayout());
        card2.add(label2, BorderLayout.NORTH);
        card2.add(value2, BorderLayout.CENTER);
        metricsPanel.add(card2);
        
        // Card 3: Faenas Completadas
        JPanel card3 = UIComponents.createCardPanel();
        JLabel label3 = new JLabel("Faenas Completadas", SwingConstants.CENTER);
        label3.setFont(label3.getFont().deriveFont(Font.BOLD, 14f));
        label3.setForeground(ThemeManager.TEXT_SECONDARY);
        JLabel value3 = new JLabel("156", SwingConstants.CENTER);
        value3.setFont(value3.getFont().deriveFont(Font.BOLD, 28f));
        value3.setForeground(new Color(255, 152, 0));
        card3.setLayout(new BorderLayout());
        card3.add(label3, BorderLayout.NORTH);
        card3.add(value3, BorderLayout.CENTER);
        metricsPanel.add(card3);
        
        // Card 4: Expedientes Pendientes
        JPanel card4 = UIComponents.createCardPanel();
        JLabel label4 = new JLabel("Expedientes Pendientes", SwingConstants.CENTER);
        label4.setFont(label4.getFont().deriveFont(Font.BOLD, 14f));
        label4.setForeground(ThemeManager.TEXT_SECONDARY);
        JLabel value4 = new JLabel("23", SwingConstants.CENTER);
        value4.setFont(value4.getFont().deriveFont(Font.BOLD, 28f));
        value4.setForeground(new Color(244, 67, 54));
        card4.setLayout(new BorderLayout());
        card4.add(label4, BorderLayout.NORTH);
        card4.add(value4, BorderLayout.CENTER);
        metricsPanel.add(card4);
        
        contentPanel.add(metricsPanel);
        contentPanel.add(Box.createVerticalStrut(16));
        
        // Estado de hilos
        JPanel threadsPanel = new JPanel();
        threadsPanel.setLayout(new BoxLayout(threadsPanel, BoxLayout.Y_AXIS));
        threadsPanel.setOpaque(false);
        threadsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        
        JLabel threadsTitle = new JLabel("Estado de Hilos del Sistema");
        threadsTitle.setFont(threadsTitle.getFont().deriveFont(Font.BOLD, 16f));
        threadsTitle.setForeground(ThemeManager.TEXT_PRIMARY);
        threadsTitle.setBorder(new javax.swing.border.EmptyBorder(0, 0, 8, 0));
        threadsPanel.add(threadsTitle);
        
        // Badges de estado de hilos
        JPanel badgesPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        badgesPanel.setOpaque(false);
        badgesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        // Placeholders para badges de hilos
        badgesPanel.add(createThreadBadge("Registro Habitantes", "ACTIVO"));
        badgesPanel.add(createThreadBadge("Generación Expedientes", "ACTIVO"));
        badgesPanel.add(createThreadBadge("Registro Mensual", "PAUSADO"));
        badgesPanel.add(createThreadBadge("Registro Faenas", "ACTIVO"));
        badgesPanel.add(createThreadBadge("Persistencia", "ACTIVO"));
        badgesPanel.add(createThreadBadge("Sesiones", "ACTIVO"));
        
        threadsPanel.add(badgesPanel);
        contentPanel.add(threadsPanel);
        contentPanel.add(Box.createVerticalStrut(16));
        
        // Botones de Acceso Rápido
        JPanel quickAccessPanel = new JPanel();
        quickAccessPanel.setLayout(new BoxLayout(quickAccessPanel, BoxLayout.Y_AXIS));
        quickAccessPanel.setOpaque(false);
        
        JLabel quickAccessTitle = new JLabel("Acceso Rápido");
        quickAccessTitle.setFont(quickAccessTitle.getFont().deriveFont(Font.BOLD, 16f));
        quickAccessTitle.setForeground(ThemeManager.TEXT_PRIMARY);
        quickAccessTitle.setBorder(new javax.swing.border.EmptyBorder(0, 0, 8, 0));
        quickAccessPanel.add(quickAccessTitle);
        
        // Panel de botones en grid
        JPanel buttonsGrid = new JPanel(new GridLayout(1, 2, 12, 12));
        buttonsGrid.setOpaque(false);
        buttonsGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        // Botón COPACI
        JPanel copaciCard = UIComponents.createCardPanel();
        copaciCard.setLayout(new BorderLayout());
        JButton btnCOPACI = new JButton("COPACI");
        btnCOPACI.setFont(new Font("Arial", Font.BOLD, 18));
        btnCOPACI.setBackground(new Color(33, 150, 243));
        btnCOPACI.setForeground(Color.WHITE);
        btnCOPACI.setBorderPainted(false);
        btnCOPACI.setFocusPainted(false);
        btnCOPACI.setPreferredSize(new Dimension(200, 80));
        btnCOPACI.addActionListener(e -> tabs.setSelectedIndex(1)); // Redirige a Alta COPACI/CAP
        copaciCard.add(btnCOPACI, BorderLayout.CENTER);
        buttonsGrid.add(copaciCard);
        
        // Botón CAP
        JPanel capCard = UIComponents.createCardPanel();
        capCard.setLayout(new BorderLayout());
        JButton btnCAP = new JButton("CAP");
        btnCAP.setFont(new Font("Arial", Font.BOLD, 18));
        btnCAP.setBackground(new Color(76, 175, 80));
        btnCAP.setForeground(Color.WHITE);
        btnCAP.setBorderPainted(false);
        btnCAP.setFocusPainted(false);
        btnCAP.setPreferredSize(new Dimension(200, 80));
        btnCAP.addActionListener(e -> tabs.setSelectedIndex(1)); // Redirige a Alta COPACI/CAP
        
        // Efectos hover para botones
        btnCOPACI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCOPACI.setBackground(new Color(25, 118, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCOPACI.setBackground(new Color(33, 150, 243));
            }
        });
        
        btnCAP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCAP.setBackground(new Color(56, 142, 60));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCAP.setBackground(new Color(76, 175, 80));
            }
        });
        
        capCard.add(btnCAP, BorderLayout.CENTER);
        buttonsGrid.add(capCard);
        
        quickAccessPanel.add(buttonsGrid);
        contentPanel.add(quickAccessPanel);
        
        scrollPane.setViewportView(contentPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel buildStatusBar() {
        JPanel status = new JPanel(new BorderLayout());
        status.setBackground(new Color(245, 245, 245));
        statusLabel.setText("Hilos activos: 6 | BD: OK");
        status.add(statusLabel, BorderLayout.WEST);
        return status;
    }

    public void updateStatus(String text) { statusLabel.setText(text); }
    
    private JPanel createThreadBadge(String threadName, String status) {
        JPanel badge = new JPanel(new BorderLayout(8, 0));
        badge.setBackground(Color.WHITE);
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(getStatusColor(status), 2),
                new javax.swing.border.EmptyBorder(8, 12, 8, 12)));
        badge.setPreferredSize(new Dimension(180, 35));
        
        // Punto de estado
        JLabel dot = new JLabel("●");
        dot.setForeground(getStatusColor(status));
        dot.setFont(dot.getFont().deriveFont(Font.BOLD, 16f));
        
        // Texto del hilo
        JLabel nameLabel = new JLabel(threadName);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 11f));
        nameLabel.setForeground(ThemeManager.TEXT_PRIMARY);
        
        // Estado
        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.PLAIN, 10f));
        statusLabel.setForeground(getStatusColor(status));
        
        badge.add(dot, BorderLayout.WEST);
        badge.add(nameLabel, BorderLayout.CENTER);
        badge.add(statusLabel, BorderLayout.EAST);
        
        return badge;
    }
    
    private Color getStatusColor(String status) {
        switch (status.toUpperCase()) {
            case "ACTIVO": return new Color(76, 175, 80);    // Verde
            case "PAUSADO": return new Color(255, 152, 0);    // Naranja
            case "DETENIDO": return new Color(244, 67, 54);    // Rojo
            case "ERROR": return new Color(156, 39, 176);   // Púrpura
            default: return new Color(158, 158, 158);  // Gris
        }
    }

    
}


