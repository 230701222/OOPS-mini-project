import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HosteliteManager extends JFrame {
    private final JTextField nameField, rollNumberField, roomNumberField, genderField, courseField, statusField;
    private final JButton addButton, removeButton, updateButton, searchButton, displayButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hostel_management";
    private static final String USER = "root";
    private static final String PASS = "adis"; // Replace with your MySQL password

    public HosteliteManager() {
        setTitle("Hostelites Management");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Input fields and labels
        addLabelAndField("Name:", 10, 10, 100, 25, nameField = new JTextField());
        addLabelAndField("Roll Number:", 10, 50, 100, 25, rollNumberField = new JTextField());
        addLabelAndField("Room Number:", 10, 90, 100, 25, roomNumberField = new JTextField());
        addLabelAndField("Gender:", 10, 130, 100, 25, genderField = new JTextField());
        addLabelAndField("Course:", 10, 170, 100, 25, courseField = new JTextField());
        addLabelAndField("Status:", 10, 210, 100, 25, statusField = new JTextField());

        // Buttons
        addButton = new JButton("Add Student");
        addButton.setBounds(10, 250, 150, 25);
        add(addButton);

        removeButton = new JButton("Remove Student");
        removeButton.setBounds(180, 250, 150, 25);
        add(removeButton);

        updateButton = new JButton("Update Student");
        updateButton.setBounds(10, 290, 150, 25);
        add(updateButton);

        searchButton = new JButton("Search Student");
        searchButton.setBounds(180, 290, 150, 25);
        add(searchButton);

        displayButton = new JButton("Display All Students");
        displayButton.setBounds(10, 330, 320, 25);
        add(displayButton);

        // Button actions
        addButton.addActionListener(e -> addStudent());
        removeButton.addActionListener(e -> removeStudent());
        updateButton.addActionListener(e -> updateStudent());
        searchButton.addActionListener(e -> searchStudent());
        displayButton.addActionListener(e -> displayAllStudents());
    }

    private void addLabelAndField(String label, int x, int y, int width, int height, JTextField field) {
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(x, y, width, height);
        add(jLabel);

        field.setBounds(x + 120, y, 150, 25);
        add(field);
    }

    private void addStudent() {
        String name = nameField.getText();
        String rollNumber = rollNumberField.getText();
        String roomNumber = roomNumberField.getText();
        String gender = genderField.getText();
        String course = courseField.getText();
        String status = statusField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO hostelites (name, roll_number, room_number, gender, course, status) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, rollNumber);
            pstmt.setString(3, roomNumber);
            pstmt.setString(4, gender);
            pstmt.setString(5, course);
            pstmt.setString(6, status);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void removeStudent() {
        String rollNumber = rollNumberField.getText().trim();

        if (rollNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a roll number!");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM hostelites WHERE roll_number = ?")) {
            pstmt.setString(1, rollNumber);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student removed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }

            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void updateStudent() {
        String rollNumber = rollNumberField.getText();
        String name = nameField.getText();
        String roomNumber = roomNumberField.getText();
        String gender = genderField.getText();
        String course = courseField.getText();
        String status = statusField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE hostelites SET name = ?, room_number = ?, gender = ?, course = ?, status = ? WHERE roll_number = ?")) {
            pstmt.setString(1, name);
            pstmt.setString(2, roomNumber);
            pstmt.setString(3, gender);
            pstmt.setString(4, course);
            pstmt.setString(5, status);
            pstmt.setString(6, rollNumber);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void searchStudent() {
        String rollNumber = rollNumberField.getText();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM hostelites WHERE roll_number = ?")) {
            pstmt.setString(1, rollNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String roomNumber = rs.getString("room_number");
                String gender = rs.getString("gender");
                String course = rs.getString("course");
                String status = rs.getString("status");

                String studentDetails = "Name: " + name + "\n" +
                                        "Roll Number: " + rollNumber + "\n" +
                                        "Room Number: " + roomNumber + "\n" +
                                        "Gender: " + gender + "\n" +
                                        "Course: " + course + "\n" +
                                        "Status: " + status;
                JOptionPane.showMessageDialog(this, studentDetails, "Student Details", JOptionPane.INFORMATION_MESSAGE);

                nameField.setText(name);
                roomNumberField.setText(roomNumber);
                genderField.setText(gender);
                courseField.setText(course);
                statusField.setText(status);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
                clearFields();
            }
        } catch (SQLException e) {
            
        }
    }

    private void displayAllStudents() {
        JFrame tableFrame = new JFrame("All Students");
        tableFrame.setSize(600, 400);

        String[] columnNames = {"Name", "Roll Number", "Room Number", "Gender", "Course", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM hostelites")) {

            while (rs.next()) {
                String name = rs.getString("name");
                String rollNumber = rs.getString("roll_number");
                String roomNumber = rs.getString("room_number");
                String gender = rs.getString("gender");
                String course = rs.getString("course");
                String status = rs.getString("status");

                model.addRow(new Object[]{name, rollNumber, roomNumber, gender, course, status});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        tableFrame.add(scrollPane);
        tableFrame.setVisible(true);
    }

    private void clearFields() {
        nameField.setText("");
        rollNumberField.setText("");
        roomNumberField.setText("");
        genderField.setText("");
        courseField.setText("");
        statusField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HosteliteManager().setVisible(true));
    }
}
