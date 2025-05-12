package models;

public class StandardFeeStrategy implements MemberFeeStrategy {
    private static final double BASE_FEE = 100.0;
    private static final double JOURNAL_FEE = 8.0;

    @Override
    public double calculateFee(boolean isFirstTime) {
        return isFirstTime ? BASE_FEE + JOURNAL_FEE : BASE_FEE;
    }

    @Override
    public String getGrade() {
        return "Standard";
    }
}
