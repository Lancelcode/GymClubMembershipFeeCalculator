package models;

public class StandardFeeStrategy implements MemberFeeStrategy {

    private static final double BASE_FEE = 100.0;
    private static final double JOURNAL_FEE = 8.0;

    @Override
    public double calculateFee(boolean includeJournal) {
        return BASE_FEE + (includeJournal ? JOURNAL_FEE : 0);
    }

    @Override
    public String getGrade() {
        return "Standard";
    }
}