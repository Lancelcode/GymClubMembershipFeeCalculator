package models;

public class PremiumMember extends BaseMember {
    public PremiumMember(String name) {
        super(name);
    }

    @Override
    public String getMembershipGrade() {
        return "Premium";
    }

    @Override
    public double calculateMembershipFee() {
        return getBaseRate() + journalFee;
    }

    @Override
    protected double getBaseRate() {
        return 150.00;
    }
}
