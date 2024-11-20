import java.sql.*;
import javax.swing.*;

public class PaymentManager extends JFrame {
    private final JTextField rollNumberField;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hostel_management";
    private static final String USER = "root";
    private static final String PASS = "adis"; // Replace with your MySQL password

    public PaymentManager() {
        setTitle("Mess Bill Payment");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setBounds(10, 10, 100, 25);
        add(rollLabel);

        rollNumberField = new JTextField();
        rollNumberField.setBounds(120, 10, 150, 25);
        add(rollNumberField);

        JButton payButton = new JButton("Pay Mess Bill");
        payButton.setBounds(10, 50, 150, 25);
        add(payButton);

        payButton.addActionListener(e -> payMessBill());
    }

    private void payMessBill() {
        String rollNumber = rollNumberField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Check if roll_number exists in mess_bills
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM mess_bills WHERE roll_number = ?");
            checkStmt.setString(1, rollNumber);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Update the payment_status to 'Paid' in mess_bills table
                PreparedStatement pstmt = conn.prepareStatement("UPDATE mess_bills SET payment_status = 'Paid' WHERE roll_number = ?");
                pstmt.setString(1, rollNumber);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Mess bill paid successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Error processing payment.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Student not found in mess_bills table!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaymentManager manager = new PaymentManager();
            manager.setVisible(true);
        });
    }
}
