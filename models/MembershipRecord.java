package models;

import java.time.LocalDate;

/**
 * Represents a record for a membership, storing details such as the member's name,
 * membership grade, membership fee, registration date, and membership status.
 * This class provides methods to retrieve and modify these attributes,
 * as well as utilities to serialize and deserialize membership records
 * to and from a file-compatible string format.
 */
public class MembershipRecord {
    private String name;
    private String grade;
    private double fee;
    private LocalDate date;
    private String status; // "current" or "past"

    public MembershipRecord(String name, String grade, double fee, LocalDate date, String status) {
        this.name = name;
        this.grade = grade;
        this.fee = fee;
        this.date = date;
        this.status = status;
    }

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

    public void setStatus(String status) {
        this.status = status;
    }

    public String toFileString() {
        return name + ";" + grade + ";" + String.format("%.2f", fee) + ";" + date + ";" + status;
    }

    public static MembershipRecord fromFileString(String line) {
        try {
            String[] parts = line.split(";");
            if (parts.length != 5) return null;

            String name = parts[0];
            String grade = parts[1];
            double fee = Double.parseDouble(parts[2].replace(",", ".")); // handle decimal comma
            LocalDate date = LocalDate.parse(parts[3]);
            String status = parts[4];

            return new MembershipRecord(name, grade, fee, date, status);
        } catch (Exception e) {
            System.out.println(" Error parsing line: " + line + " — " + e.getMessage());
            return null;
        }
    }
}
