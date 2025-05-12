package ui;

import models.*;
import utils.MemberManager;

import javax.swing.*;
import java.awt.*;

public class GymClubGUI extends JFrame {

    private MemberManager manager;

    public GymClubGUI() {
        setTitle("Gym Club Membership Fee Calculator");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        manager = new MemberManager();

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton addMemberBtn = new JButton("Add a New Member");
        JButton viewMembersBtn = new JButton("Display List of Members");
        JButton viewReceiptBtn = new JButton("Display Member’s Receipt");
        JButton exitBtn = new JButton("Exit");

        addMemberBtn.addActionListener(e -> openAddMemberDialog());
        viewMembersBtn.addActionListener(e -> showMemberList());
        viewReceiptBtn.addActionListener(e -> showReceiptDialog());
        exitBtn.addActionListener(e -> System.exit(0));

        JButton viewTotalFeesBtn = new JButton("View Total Fees Collected");
        viewTotalFeesBtn.addActionListener(e -> {
            double total = manager.getTotalFeesCollected();
            JOptionPane.showMessageDialog(this,
                    String.format("Total Membership Fees Collected: £%.2f", total),
                    "Total Fees",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        panel.add(addMemberBtn);
        panel.add(viewMembersBtn);
        panel.add(viewReceiptBtn);
        panel.add(viewTotalFeesBtn);
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
            String grade = (String) gradeBox.getSelectedItem();

            if (name.isEmpty() || name.length() < 2 || name.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,
                        "Invalid name.\n• It must not be empty.\n• Must be at least 2 characters.\n• Cannot be just numbers.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Member newMember;
            switch (grade) {
                case "Standard":
                    newMember = new StandardMember(name);
                    break;
                case "Premium":
                    newMember = new PremiumMember(name);
                    break;
                case "VIP":
                    newMember = new VIPMember(name);
                    break;
                default:
                    newMember = null;
                    break;
            }

            manager.addMember(newMember);
            JOptionPane.showMessageDialog(this, grade + " member '" + name + "' added!");
        }
    }

    private void showMemberList() {
        StringBuilder list = new StringBuilder();
        for (Member m : manager.getAllMembers()) {
            list.append("- ").append(m.getName())
                    .append(" (").append(m.getMembershipGrade()).append(")\n");
        }
        if (list.length() == 0) list.append("No members registered.");
        JOptionPane.showMessageDialog(this, list.toString(), "Registered Members", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showReceiptDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter member name to view receipt:");
        if (name == null || name.trim().isEmpty()) return;

        Member m = manager.findMemberByName(name.trim());
        if (m == null) {
            JOptionPane.showMessageDialog(this, "Member not found.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, m.getReceipt(), "Member Receipt", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
