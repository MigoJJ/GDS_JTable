import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class EMR_New_tab {
    private void createAndShowGUI() {
        // Create a new JFrame and set its properties
        JFrame frame = new JFrame("JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define the input string
        String inputString = "▲\t0.10\tAnti-TSH-Receptor Antibodies (IU/L <1.75):\n"
        		+ "▲\t0.20\tAnti-TSH-Receptor Antibodies (IU/L <1.75):\n"
        		+ "▲\t0.30\tAnti-TSH-Receptor Antibodies (IU/L <1.75):\n";

        // Split the input string by newline to get rows
        String[] rows = inputString.split("\n");

        // Create a new 2D array to store the table data
        Object[][] AAA = new Object[rows.length][3];

        // Iterate over the rows
        for (int i = 0; i < rows.length; i++) {
            // Split the row by tabs to get columns
            String[] columns = rows[i].split("\t");

            // Check if the columns array has the required length
            if (columns.length >= 3) {
                // Assign the values to specific columns
                AAA[i][0] = columns[0].trim().equals("▲") ? "Decision" : "";
                AAA[i][1] = columns[1].trim();
                AAA[i][2] = columns[2].trim();
            }
        }

        // Create a new Object array for column names
        Object[] columnNames = new Object[]{"Decision", "Value", "Label"};

        // Create a new DefaultTableModel using the table data and column names
        DefaultTableModel model = new DefaultTableModel(AAA, columnNames);

        // Create a new JTable using the DefaultTableModel
        JTable table = new JTable(model);

        // Create a new JScrollPane and add the JTable to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the JScrollPane to a JPanel
        JPanel panel = new JPanel();
        panel.add(scrollPane);

        // Add the JPanel to the JFrame
        frame.add(panel);

        // Set the size and visibility of the JFrame
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EMR_New_tab().createAndShowGUI();
        });
    }
}
