import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class EMT_New_high {
    private void createAndShowGUI() {
        // Create a new JFrame and set its properties
        JFrame frame = new JFrame("JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create an output JTextArea
        JTextArea outputTextArea = new JTextArea(15, 60);
        outputTextArea.setEditable(false);

        // Create a new DefaultTableModel and specify column names
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                    {"▲", "", "Free T4 (ng/dL):"},
                    {"▼", "0.005", "TSH (mIU/L):"},
                    {" ", "0.80", "Anti-TSH-Receptor Antibodies (IU/L <1.75):"},
                    {"▲", "258", "Anti-Thyroglobulin Antibodies (IU/mL <115):"},
                    {"▲", "987", "Anti-Microsomal Antibodies (IU/mL <34):"}
                },
                new Object[]{"Decision", "Value", "Label"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the "Value" column editable (column index 1)
                return column == 1;
            }
        };

        // Create a new JTable using the customized DefaultTableModel
        JTable table = new JTable(model) {
            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                // Override the cell editor to move to the next row when Enter is pressed in the "Value" column
                if (column == 1) {
                    return new DefaultCellEditor(new JTextField()) {
                        @Override
                        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                            JTextField textField = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
                            textField.addKeyListener(new KeyAdapter() {
                                @Override
                                public void keyPressed(KeyEvent e) {
                                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                        if (row < table.getRowCount() - 1) {
                                            table.setRowSelectionInterval(row + 1, row + 1);
                                            table.setColumnSelectionInterval(column, column);
                                        }
                                    }
                                }
                            });
                            return textField;
                        }
                    };
                } else {
                    return super.getCellEditor(row, column);
                }
            }
        };

        // Add a TableModelListener to the table to listen for changes in the "Value" column
        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() == 1) { // Check if the "Value" column has changed
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    String valueStr = (String) table.getValueAt(row, column);

                    try {
                        double value = Double.parseDouble(valueStr);
                        if (value > 5) {
                            table.setValueAt("high", row, 0); // Set "high" in the "Decision" column
                        }
                    } catch (NumberFormatException ex) {
                        // Handle the case where the value is not a valid number
                        System.out.println("Invalid value entered.");
                    }
                }
            }
        });

        // Create a copy button to copy the table data to the output text area
        JButton copyButton = new JButton("Copy");
        copyButton.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            int rowCount = table.getRowCount();
            int columnCount = table.getColumnCount();

            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < columnCount; column++) {
                    Object value = table.getValueAt(row, column);
                    sb.append(value).append("\t");
                }
                sb.append("\n");
            }

            outputTextArea.setText(sb.toString());
        });



        // Create a new JScrollPane for the table
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Create a new JScrollPane for the output JTextArea
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);

        // Create a new JPanel and add the table, copy button, and output components to it
        JPanel panel = new JPanel();
        panel.add(tableScrollPane);
        panel.add(copyButton);
        panel.add(outputScrollPane);

        // Add the JPanel to the JFrame
        frame.add(panel);

        // Set the size and visibility of the JFrame
        frame.setSize(1400, 800);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EMT_New_high().createAndShowGUI();
        });
    }
}
