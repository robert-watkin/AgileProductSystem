/*
Written By : Robert Watkin
Date Created : 11/03/2020
*/
package JTable;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class JTableButtonRenderer implements TableCellRenderer {
    @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JButton button = (JButton)value;
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(UIManager.getColor("Button.background"));
        }
        return button;
    }
}