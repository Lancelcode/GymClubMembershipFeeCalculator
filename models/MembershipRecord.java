package models;

import java.time.LocalDate;

/**
 * Represents a single membership record, storing information about:
 * - Member's name
 * - Membership grade (e.g., Standard, Premium, VIP)
 * - Fee paid
 * - Registration date
 * - Status ("current" or "past")
 *
 * This class also provides methods to serialize/deserialize records
 * to and from a text format suitable for file storage.
 */
public class MembershipRecord {

    private String name;      // Member's name
    private String grade;     // Membership grade
    private double fee;       // Fee paid
    private LocalDate date;   // Registration date
    private String status;    // "current" or "past"

    /**
     * Constructor for creating a complete membership record.
     *
     * @param name   Member's name
     * @param grade  Membership grade
     * @param fee    Fee paid
     * @param date   Date of registration
     * @param status Status ("current" or "past")
     */
    public MembershipRecord(String name, String grade, double fee, LocalDate date, String status) {
        this.name = name;
        this.grade = grade;
        this.fee = fee;
        this.date = date;
        this.status = status;
    }

    // Accessor (getter) methods

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public double getFee() {
        return fee;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    // Mutator (setter) for status
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Converts this membership record into a file-compatible string.
     * Format: name;grade;fee;date;status
     *
     * @return A string representation suitable for saving to file
     */
    public String toFileString() {
        return name + ";" + grade + ";" + String.format("%.2f", fee) + ";" + date + ";" + status;
    }

    /**
     * Parses a string from file into a MembershipRecord object.
     * If parsing fails or the format is incorrect, returns null.
     *
     * @param line A line from the file in the format: name;grade;fee;date;status
     * @return A MembershipRecord object or null if the line is malformed
     */
    public static MembershipRecord fromFileString(String line) {
        try {
            String[] parts = line.split(";");
            if (parts.length != 5) return null;

            String name = parts[0];
            String grade = parts[1];
            double fee = Double.parseDouble(parts[2].replace(",", ".")); // handles comma as decimal
            LocalDate date = LocalDate.parse(parts[3]);
            String status = parts[4];

            return new MembershipRecord(name, grade, fee, date, status);
        } catch (Exception e) {
            System.out.println(" Error parsing line: " + line + " — " + e.getMessage());
            return null;
        }
    }
}
