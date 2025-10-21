import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Backend class for Item
class Item {
    String name;
    int quantity;
    double price;

    Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    double getTotal() {
        return quantity * price;
    }
}

// Main Application
public class BillingSystem extends JFrame {

    private JTextField itemNameField, quantityField, priceField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private ArrayList<Item> itemList = new ArrayList<>();

    public BillingSystem() {
        setTitle("Billing Management System");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Title
        JLabel title = new JLabel("Billing Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(33, 150, 243));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Item"));
        inputPanel.add(new JLabel("Item Name:"));
        itemNameField = new JTextField();
        inputPanel.add(itemNameField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        JButton addButton = new JButton("Add Item");
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);
        inputPanel.add(addButton);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Item", "Quantity", "Price", "Total"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Total Panel
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(new Color(255, 87, 34));
        totalPanel.add(totalLabel);

        JButton clearButton = new JButton("Clear All");
        clearButton.setBackground(new Color(244, 67, 54));
        clearButton.setForeground(Color.WHITE);
        totalPanel.add(clearButton);

        // Actions
        addButton.addActionListener(e -> addItem());
        clearButton.addActionListener(e -> clearAll());

        // Layout
        setLayout(new BorderLayout(10, 10));
        add(title, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        add(totalPanel, BorderLayout.PAGE_END);
    }

    private void addItem() {
        try {
            String name = itemNameField.getText();
            int qty = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());

            if (name.isEmpty() || qty <= 0 || price <= 0) {
                JOptionPane.showMessageDialog(this, "Enter valid item details.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Item item = new Item(name, qty, price);
            itemList.add(item);
            tableModel.addRow(new Object[]{item.name, item.quantity, item.price, item.getTotal()});
            updateTotal();

            // Clear fields
            itemNameField.setText("");
            quantityField.setText("");
            priceField.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Enter numbers for quantity and price.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTotal() {
        double total = 0;
        for (Item item : itemList) {
            total += item.getTotal();
        }
        totalLabel.setText(String.format("Total: ₹%.2f", total));
    }

    private void clearAll() {
        itemList.clear();
        tableModel.setRowCount(0);
        totalLabel.setText("Total: ₹0.00");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BillingSystem().setVisible(true));
    }
}
