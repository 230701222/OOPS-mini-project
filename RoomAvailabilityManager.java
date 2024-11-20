
import java.sql.*;
import javax.swing.*;

public class RoomAvailabilityManager extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hostel_management";
    private static final String USER = "root";
    private static final String PASS = "adis"; // Replace with your MySQL password
    private static final int TOTAL_ROOMS = 5; // Define total rooms as 10

    private final JLabel availabilityLabel;

    public RoomAvailabilityManager() {
        setTitle("Room Availability");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        availabilityLabel = new JLabel("Checking room availability...");
        availabilityLabel.setBounds(20, 20, 250, 30);
        add(availabilityLabel);

        // Call the method to check availability after setting up the UI
        checkRoomAvailability();
    }

    private void checkRoomAvailability() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS student_count FROM hostelites")) {

            if (rs.next()) {
                int studentCount = rs.getInt("student_count");
                if (studentCount >= TOTAL_ROOMS) {
                    availabilityLabel.setText("Status: Full");
                } else {
                    availabilityLabel.setText("Status: Vacant");
                }
            }
        } catch (SQLException e) {
            // Displaying specific error details in a dialog
            JOptionPane.showMessageDialog(this, "Error checking availability: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            System.out.println();
        }
    }
}