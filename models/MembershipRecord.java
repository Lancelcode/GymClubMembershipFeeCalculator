package models;

import java.time.LocalDate;

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
        return String.format("%s;%s;%.2f;%s;%s", name, grade, fee, date, status);
    }

    public static MembershipRecord fromFileString(String line) {
        String[] parts = line.split(";");
        if (parts.length != 5) return null;

        try {
            String name = parts[0].trim();
            String grade = parts[1].trim();
            double fee = Double.parseDouble(parts[2]);
            LocalDate date = LocalDate.parse(parts[3].trim());
            String status = parts[4].trim();
            return new MembershipRecord(name, grade, fee, date, status);
        } catch (Exception e) {
            return null;
        }
    }
}
