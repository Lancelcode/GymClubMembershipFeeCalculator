package models;

/**
 * Implements the membership fee calculation strategy for the "VIP" grade membership.
 *
 * This class defines:
 * - A base membership fee of £200
 * - An optional journal fee of £8 (only applied to first-time VIP members)
 *
 * It conforms to the MemberFeeStrategy interface, supporting dynamic and interchangeable
 * fee calculation behavior via the Strategy design pattern.
 */
public class VIPFeeStrategy implements MemberFeeStrategy {

    private static final double BASE_FEE = 200.0;    // Annual fee for VIP membership
    private static final double JOURNAL_FEE = 8.0;   // One-time journal fee for new members

    /**
     * Calculates the total membership fee for a VIP member.
     *
     * @param isFirstTime true if the member is registering for the first time
     * @return Total calculated fee based on membership status
     */
    @Override
    public double calculateFee(boolean isFirstTime) {
        return isFirstTime ? BASE_FEE + JOURNAL_FEE : BASE_FEE;
    }

    /**
     * Returns the name of the grade associated with this strategy.
     *
     * @return "VIP"
     */
    @Override
    public String getGrade() {
        return "VIP";
    }
}
