package proyectoparalela.utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIComponents {
    public static JTextField createTextField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setBorder(new EmptyBorder(10, 12, 10, 12));
        tf.setBackground(Color.WHITE);
        tf.setForeground(ThemeManager.TEXT_PRIMARY);
        tf.setCaretColor(ThemeManager.PRIMARY);
        tf.setToolTipText(placeholder);
        tf.setColumns(20);
        tf.setBorder(new LineBorder(new Color(0,0,0,40), 1, true));
        tf.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) { tf.setBorder(new LineBorder(ThemeManager.PRIMARY, 2, true)); }
            @Override public void focusLost(java.awt.event.FocusEvent e) { tf.setBorder(new LineBorder(new Color(0,0,0,40), 1, true)); }
        });
        return tf;
    }

    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(ThemeManager.PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(10,16,10,16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(ThemeManager.SECONDARY); }
            @Override public void mouseExited(MouseEvent e) { b.setBackground(ThemeManager.PRIMARY); }
        });
        return b;
    }

    public static JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,0,0,30)),
                new EmptyBorder(16,16,16,16)));
        return card;
    }
    
    public static JPanel cardPanel(String title, String value, Color color) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,0,0,30)),
                new EmptyBorder(12,12,12,12)));
        JLabel t = new JLabel(title);
        t.setForeground(ThemeManager.TEXT_SECONDARY);
        JLabel v = new JLabel(value);
        v.setFont(v.getFont().deriveFont(Font.BOLD, 28f));
        v.setForeground(color);
        p.add(t, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);
        return p;
    }

    public static JButton sidebarButton(String text) {
        JButton b = new JButton(text);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBackground(new Color(10, 34, 64));
        b.setForeground(Color.WHITE);
        b.setBorder(new EmptyBorder(10,14,10,14));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { b.setBackground(new Color(18, 54, 98)); }
            @Override public void mouseExited(MouseEvent e) { b.setBackground(new Color(10, 34, 64)); }
        });
        return b;
    }
}


