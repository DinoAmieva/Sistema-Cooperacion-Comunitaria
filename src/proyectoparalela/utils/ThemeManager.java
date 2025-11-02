package proyectoparalela.utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ThemeManager {
    public static final Color PRIMARY = Color.decode("#2196F3");
    public static final Color SECONDARY = Color.decode("#00BCD4");
    public static final Color ACCENT = Color.decode("#FF5722");
    public static final Color BACKGROUND = Color.decode("#F5F5F5");
    public static final Color TEXT_PRIMARY = Color.decode("#212121");
    public static final Color TEXT_SECONDARY = Color.decode("#757575");

    public static void applyLightTheme() {
        UIManager.put("Panel.background", BACKGROUND);
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.foreground", TEXT_PRIMARY);
        UIManager.put("Button.focus", new Color(0,0,0,0));
        UIManager.put("Label.foreground", TEXT_PRIMARY);
        UIManager.put("TabbedPane.selected", Color.WHITE);
        UIManager.put("TabbedPane.contentAreaColor", BACKGROUND);
        UIManager.put("Table.alternateRowColor", Color.decode("#F8F9FA"));
        UIManager.put("ScrollBar.width", 10);
        UIManager.put("ToolTip.background", Color.WHITE);
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(0,0,0,40)));
        UIManager.put("ToolTip.foreground", TEXT_PRIMARY);
        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
    }

    public static JPanel gradientTitleBar(String title, Icon icon) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0,0, PRIMARY, getWidth(), getHeight(), SECONDARY);
                g2.setPaint(gp);
                g2.fillRect(0,0,getWidth(),getHeight());
                g2.dispose();
            }
        };
    }

    public static BorderLayout defaultBorderLayout() { return new BorderLayout(12,12); }

    public static EmptyBorder defaultContentPadding() { return new EmptyBorder(16,16,16,16); }
}











