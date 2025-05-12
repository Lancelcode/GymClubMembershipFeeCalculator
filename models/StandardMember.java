package models;

public class StandardMember extends BaseMember {
    public StandardMember(String name) {
        super(name);
    }

    @Override
    public String getMembershipGrade() {
        return "Standard";
    }

    @Override
    public double calculateMembershipFee() {
        return getBaseRate() + journalFee;
    }

    @Override
    protected double getBaseRate() {
        return 100.00;
    }
}
