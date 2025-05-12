package models;

public class VIPMember extends BaseMember {
    public VIPMember(String name) {
        super(name);
    }

    @Override
    public String getMembershipGrade() {
        return "VIP";
    }

    @Override
    public double calculateMembershipFee() {
        return getBaseRate() + journalFee;
    }

    @Override
    protected double getBaseRate() {
        return 200.00;
    }
}
