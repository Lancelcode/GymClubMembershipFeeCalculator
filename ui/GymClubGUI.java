package ui;

import models.MembershipRecord;
import utils.MemberManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GymClubGUI extends JFrame {

    private MemberManager manager;

    public GymClubGUI() {
        setTitle("Gym Club Membership Fee Calculator");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        manager = new MemberManager();

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton addMemberBtn = new JButton("Add a New Member");
        JButton viewMembersBtn = new JButton("Display List of Members");
        JButton viewReceiptBtn = new JButton("Display Member’s Receipt");
        JButton exitBtn = new JButton("Exit");

        addMemberBtn.addActionListener(e -> openAddMemberDialog());
        viewMembersBtn.addActionListener(e -> showMemberList());
        viewReceiptBtn.addActionListener(e -> showReceiptDialog());
        exitBtn.addActionListener(e -> System.exit(0));

        panel.add(addMemberBtn);
        panel.add(viewMembersBtn);
        panel.add(viewReceiptBtn);
        panel.add(exitBtn);

        add(panel);
        setVisible(true);
    }

    private void openAddMemberDialog() {
        JTextField nameField = new JTextField();
        String[] grades = {"Standard", "Premium", "VIP"};
        JComboBox<String> gradeBox = new JComboBox<>(grades);

        JPanel form = new JPanel(new GridLayout(0, 1));
        form.add(new JLabel("Enter member name:"));
        form.add(nameField);
        form.add(new JLabel("Select membership grade:"));
        form.add(gradeBox);

        int result = JOptionPane.showConfirmDialog(this, form, "Add New Member",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String newGrade = (String) gradeBox.getSelectedItem();

            if (name.isEmpty() || name.length() < 1 || name.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,
                        "Invalid name.\n• It must not be empty.\n• Must be at least 2 characters.\n• Cannot be just numbers.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            MembershipRecord current = manager.findCurrentRecordByName(name);

            if (current != null) {
                String currentGrade = current.getGrade();
                int choice = JOptionPane.showConfirmDialog(this,
                        "You already have a subscription with '" + currentGrade + "'.\nChange to '" + newGrade + "'?\nYou will lose any benefits from the current membership.",
                        "Confirm Membership Change",
                        JOptionPane.YES_NO_OPTION);

                if (choice != JOptionPane.YES_OPTION) return;
            }

            manager.addMember(name, newGrade);
            JOptionPane.showMessageDialog(this, newGrade + " member '" + name + "' added!");
        }
    }

    private void showMemberList() {
        StringBuilder list = new StringBuilder();
        for (String entry : manager.getAllMembers()) {
            String[] parts = entry.split(";");
            list.append("- ").append(parts[0])
                    .append(" (Grade: ").append(parts[1]).append(")\n");
        }
        if (list.length() == 0) list.append("No members registered.");
        JOptionPane.showMessageDialog(this, list.toString(), "Registered Members", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showReceiptDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter member name to view receipt:");
        if (name == null || name.trim().isEmpty()) return;

        List<MembershipRecord> history = manager.getHistoryForMember(name.trim());
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Member not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder display = new StringBuilder();
        display.append("--------------------------------------------------------------\n");
        display.append(String.format("%-12s %-10s %-10s %-8s\n", "Date", "Grade", "Fee", "Status"));
        display.append("--------------------------------------------------------------\n");

        double totalPaid = 0.0;
        for (MembershipRecord record : history) {
            display.append(String.format("%-12s %-10s £%-9.2f %-8s\n",
                    record.getDate().toString(),
                    record.getGrade(),
                    record.getFee(),
                    record.getStatus()));
            totalPaid += record.getFee();
        }
        display.append("--------------------------------------------------------------\n");
        display.append(String.format("Total paid across all memberships: £%.2f\n", totalPaid));

        JTextArea textArea = new JTextArea(display.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Membership Receipt History", JOptionPane.INFORMATION_MESSAGE);
    }
}