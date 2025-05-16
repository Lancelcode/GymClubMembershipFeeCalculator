package models;

/**
 * Implements the membership fee calculation strategy for the "VIP" grade membership.
 * This strategy defines the base fee and additional journal fee specific to the VIP grade,
 * and provides mechanisms to calculate the total fee and identify the grade.
 *
 * For first-time VIP members, the total fee includes an additional journal fee.
 */
public class VIPFeeStrategy implements MemberFeeStrategy {
    private static final double BASE_FEE = 200.0;
    private static final double JOURNAL_FEE = 8.0;

    @Override
    public double calculateFee(boolean isFirstTime) {
        return isFirstTime ? BASE_FEE + JOURNAL_FEE : BASE_FEE;
    }

    @Override
    public String getGrade() {
        return "VIP";
    }
}
