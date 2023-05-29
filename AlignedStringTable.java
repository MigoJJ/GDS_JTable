
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Weightloss.AlignedStringTableExample;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlignedStringTable extends JFrame {

    private JTextArea textArea;

    public AlignedStringTable() {
        initializeUI();
    }

    private void initializeUI() {
        // Sample aligned string
        String alignedString = "▲    22.67         Free T4 (ng/dL):\n" +
                               "▼    0.005         TSH (mIU/L):\n" +
                               "    0.80         Anti-TSH-Receptor Antibodies (IU/L <1.75):\n" +
                               "▲    258         Anti-Thyroglobulin Antibodies (IU/mL <115):\n" +
                               "▲    987        Anti-Microsomal Antibodies (IU/mL <34):\n";

        // Split the aligned string into rows
        String[] rows = alignedString.split("\n");

        // Create the table model with three columns (level, value, and label)
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Level", "Value", "Label"}, 0);

        // Add each row to the table model
        for (String row : rows) {
            String[] parts = row.split("\\s+", 3); // Split by whitespace into three parts (level, value, and label)
            String level = parts[0].trim();
            String value = parts[1].trim();
            String label = parts[2].trim();
            model.addRow(new Object[]{level, value, label});
        }

        // Create the JTable with the table model
        JTable table = new JTable(model);
        table.setRowHeight(25); // Set the row height as needed

        // Set table appearance and behavior
        table.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD)); // Set header font as bold

        // Create a JScrollPane to contain the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JButton to copy rows to the JTextArea
        JButton copyButton = new JButton("Copy Rows");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyRowsToTextArea(table);
            }
        });

        // Create the JTextArea
        textArea = new JTextArea();
        textArea.setEditable(false);

        // Create a JScrollPane to contain the JTextArea
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);

        // Create a JPanel to hold the button and text area
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(copyButton, BorderLayout.NORTH);
        panel.add(textAreaScrollPane, BorderLayout.CENTER);

        // Create a JPanel to hold the table and the panel with the button and text area
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(panel, BorderLayout.EAST);

        // Set JFrame properties
        setTitle("Aligned String Table Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the JFrame on screen

        // Set the content pane of the JFrame
        setContentPane(contentPane);

        // Show the JFrame
        setVisible(true);
    }

    private void copyRowsToTextArea(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        StringBuilder rowsText = new StringBuilder();

        // Append each row to the StringBuilder
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                rowsText.append(model.getValueAt(row, col)).append("\t");
            }
            rowsText.append("\n");
        }

        // Set the text of the JTextArea
        textArea.setText(rowsText.toString());
        System.out.println(rowsText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AlignedStringTableExample();
        });
    }
}
