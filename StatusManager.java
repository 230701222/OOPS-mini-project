import java.sql.*;
import javax.swing.*;

public class StatusManager extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hostel_management";
    private static final String USER = "root";
    private static final String PASS = "adis"; // Replace with your MySQL password

    public StatusManager() {
        setTitle("Check Student/Employee Status");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JButton checkLivingButton = new JButton("Check Living Students");
        checkLivingButton.setBounds(10, 10, 200, 25);
        add(checkLivingButton);

        JButton checkLeavedButton = new JButton("Check Leaved Students");
        checkLeavedButton.setBounds(10, 50, 200, 25);
        add(checkLeavedButton);

        checkLivingButton.addActionListener(e -> checkStatus("Living"));
        checkLeavedButton.addActionListener(e -> checkStatus("Leaved"));
    }

    private void checkStatus(String status) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM hostelites WHERE status = ?")) {
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            StringBuilder result = new StringBuilder();

            while (rs.next()) {
                String name = rs.getString("name");
                String rollNumber = rs.getString("roll_number");
                result.append("Name: ").append(name).append(", Roll No: ").append(rollNumber).append("\n");
            }

            if (result.length() == 0) {
                JOptionPane.showMessageDialog(this, "No students found.");
            } else {
                JOptionPane.showMessageDialog(this, result.toString()); // Convert StringBuilder to String
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StatusManager manager = new StatusManager();
            manager.setVisible(true);
        });
    }
}
