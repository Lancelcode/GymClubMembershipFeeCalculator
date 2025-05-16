package models;

/**
 * Implements the membership fee calculation strategy for the "Standard" grade membership.
 * This strategy defines the base fee and journal fee specific to the Standard grade
 * and provides mechanisms to calculate the total fee and identify the grade.
 *
 * The total fee includes an additional journal fee for first-time memberships.
 */
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
