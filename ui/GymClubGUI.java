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

            if (name.isEmpty() || name.length() < 2 || name.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,
                        "Invalid name.\n• It must not be empty.\n• Must be at least 2 characters.\n• Cannot be just numbers.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            MembershipRecord existing = manager.findCurrentRecordByName(name);
            if (existing != null) {
                String currentGrade = existing.getGrade();
                int confirm = JOptionPane.showConfirmDialog(this,
                        String.format("You already have a subscription with '%s'.\nWould you like to change to '%s'?\nThe new membership will take effect immediately. You will lose access to your current '%s' membership and any related benefits.",
                                currentGrade, newGrade, currentGrade),
                        "Confirm Membership Change",
                        JOptionPane.YES_NO_OPTION);

                if (confirm != JOptionPane.YES_OPTION) return;
            }

            manager.addMember(name, newGrade);
            JOptionPane.showMessageDialog(this, newGrade + " membership for '" + name + "' has been recorded.");
        }
    }

    private void showMemberList() {
        List<String> members = manager.getAllMembers();
        StringBuilder list = new StringBuilder();
        for (String entry : members) {
            String[] parts = entry.split(";");
            if (parts.length == 2) {
                list.append("- ").append(parts[0])
                        .append(" (").append(parts[1]).append(")\n");
            }
        }
        if (list.length() == 0) list.append("No members registered.");
        JOptionPane.showMessageDialog(this, list.toString(), "Registered Members", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showReceiptDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter member name to view receipt:");
        if (name == null || name.trim().isEmpty()) return;

        MembershipRecord current = manager.findCurrentRecordByName(name.trim());
        if (current == null) {
            JOptionPane.showMessageDialog(this, "Member not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder receipt = new StringBuilder();
        receipt.append("Statement for ").append(current.getDate().getMonth()).append(" ")
                .append(current.getDate().getYear()).append(" for ").append(current.getName()).append(" - CURRENT ")
                .append(current.getGrade()).append(" Membership\n");
        receipt.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        receipt.append(current.getGrade()).append(" Member Enrolment\n   Date: ").append(current.getDate()).append("\n\n");
        receipt.append("Purchases:\n   1: Membership Fee: £").append(String.format("%.2f", current.getFee())).append("\n");
        receipt.append("Total Price: £").append(String.format("%.2f", current.getFee())).append("\n");
        receipt.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

        JOptionPane.showMessageDialog(this, receipt.toString(), "Current Membership Receipt", JOptionPane.INFORMATION_MESSAGE);

        int showAll = JOptionPane.showConfirmDialog(this, "Would you like to view full receipt history?", "View History", JOptionPane.YES_NO_OPTION);
        if (showAll == JOptionPane.YES_OPTION) {
            List<MembershipRecord> history = manager.getHistoryForMember(name.trim());
            StringBuilder historyText = new StringBuilder("Receipt History for " + name + ":\n\n");
            for (MembershipRecord r : history) {
                historyText.append("- ").append(r.getGrade())
                        .append(" (£").append(String.format("%.2f", r.getFee())).append(") on ")
                        .append(r.getDate());
                if (r.getStatus().equalsIgnoreCase("current")) historyText.append(" (Current)");
                historyText.append("\n");
            }
            JOptionPane.showMessageDialog(this, historyText.toString(), "Full Receipt History", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}