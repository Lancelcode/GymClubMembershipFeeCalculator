package models;

/**
 * Implements the membership fee calculation strategy for the "Premium" grade membership.
 * This strategy defines the base fee and journal fee specific to the Premium grade
 * and provides mechanisms to calculate the total fee and identify the grade.
 */
public class PremiumFeeStrategy implements MemberFeeStrategy {
    private static final double BASE_FEE = 150.0;
    private static final double JOURNAL_FEE = 8.0;

    @Override
    public double calculateFee(boolean isFirstTime) {
        return isFirstTime ? BASE_FEE + JOURNAL_FEE : BASE_FEE;
    }

    @Override
    public String getGrade() {
        return "Premium";
    }
}
