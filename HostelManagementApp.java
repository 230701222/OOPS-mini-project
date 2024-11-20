import javax.swing.*;

public class HostelManagementApp extends JFrame {

    public HostelManagementApp() {
        setTitle("Hostel Management System - Main Menu");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton hostelitesButton = new JButton("Hostelites Management");
        hostelitesButton.setBounds(100, 30, 200, 30);
        add(hostelitesButton);

        JButton messBillButton = new JButton("Receive Mess Bill Payment");
        messBillButton.setBounds(100, 70, 200, 30);
        add(messBillButton);

        JButton checkStatusButton = new JButton("Check Student/Employee Status");
        checkStatusButton.setBounds(100, 110, 200, 30);
        add(checkStatusButton);

        JButton roomAvailabilityButton = new JButton("Check Room Availability");
        roomAvailabilityButton.setBounds(100, 150, 200, 30);
        add(roomAvailabilityButton);

        JButton logoutButton = new JButton("Back to Login");
        logoutButton.setBounds(100, 190, 200, 30);
        add(logoutButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(100, 230, 200, 30);
        add(exitButton);

        // Using lambda expressions to handle button clicks
        hostelitesButton.addActionListener(e -> new HosteliteManager().setVisible(true));
        messBillButton.addActionListener(e -> new PaymentManager().setVisible(true));
        checkStatusButton.addActionListener(e -> new StatusManager().setVisible(true));
        roomAvailabilityButton.addActionListener(e -> new RoomAvailabilityManager().setVisible(true));
        logoutButton.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
        exitButton.addActionListener(e -> System.exit(0));
    }
}
