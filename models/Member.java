package models;

public interface Member {
    String getName();
    String getMembershipGrade();
    double calculateMembershipFee();
    String getReceipt();
}
