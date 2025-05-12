package models;

import java.time.LocalDate;

public abstract class BaseMember implements Member {
    protected String name;
    protected LocalDate joinDate;
    protected final double journalFee = 8.00;

    public BaseMember(String name) {
        this.name = name;
        this.joinDate = LocalDate.now();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append(String.format("Statement for %s %d for %s - NEW %s Membership\n",
                joinDate.getMonth(), joinDate.getYear(), name, getMembershipGrade()));
        receipt.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        receipt.append(getMembershipGrade() + " Member Enrolment\n");
        receipt.append("   Date: " + joinDate + "\n\n");
        receipt.append("Purchases:\n");
        receipt.append(String.format("   1: Annual Membership: £%.2f\n", getBaseRate()));
        receipt.append(String.format("   2: Journal Fee:        £%.2f\n", journalFee));
        receipt.append(String.format("\nTotal Price: £%.2f\n", calculateMembershipFee()));
        receipt.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        return receipt.toString();
    }

    protected abstract double getBaseRate();
}

