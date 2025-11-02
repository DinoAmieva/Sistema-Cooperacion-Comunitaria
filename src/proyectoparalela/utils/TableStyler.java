package proyectoparalela.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class TableStyler {
    public static void applyModernStyle(JTable table) {
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(33, 150, 243, 40));
        table.setSelectionForeground(ThemeManager.TEXT_PRIMARY);

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setOpaque(false);
        header.setBackground(new Color(10, 34, 64));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        header.setFont(header.getFont().deriveFont(Font.BOLD, 13f));

        // Alternar filas
        table.setDefaultRenderer(Object.class, new AlternatingRowRenderer());
    }

    static class AlternatingRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? Color.WHITE : Color.decode("#F8F9FA"));
                c.setForeground(ThemeManager.TEXT_PRIMARY);
            }
            setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
            return c;
        }
    }
}











