package models;

/**
 * Implements the membership fee calculation strategy for the "Premium" grade membership.
 *
 * This class defines:
 * - The base fee for Premium members (£150.00)
 * - An optional journal fee (£8.00) added only for first-time registration
 *
 * It adheres to the MemberFeeStrategy interface, enabling flexible use
 * in the fee calculation system via the Strategy design pattern.
 */
public class PremiumFeeStrategy implements MemberFeeStrategy {

    private static final double BASE_FEE = 150.0;     // Annual fee for Premium membership
    private static final double JOURNAL_FEE = 8.0;    // One-time journal fee (only for new members)

    /**
     * Calculates the total fee for a Premium member.
     *
     * @param isFirstTime true if the user is registering for the first time
     * @return the membership fee, including journal fee if applicable
     */
    @Override
    public double calculateFee(boolean isFirstTime) {
        return isFirstTime ? BASE_FEE + JOURNAL_FEE : BASE_FEE;
    }

    /**
     * Returns the grade name associated with this strategy.
     *
     * @return "Premium"
     */
    @Override
    public String getGrade() {
        return "Premium";
    }
}
