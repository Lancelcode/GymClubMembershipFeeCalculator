package models;

/**
 * Implements the membership fee calculation strategy for the "Standard" grade membership.
 *
 * This class defines:
 * - A base membership fee of £100
 * - An optional one-time journal fee of £8 (only charged to first-time members)
 *
 * It implements the MemberFeeStrategy interface, allowing the application to
 * dynamically calculate fees using the Strategy design pattern.
 */
public class StandardFeeStrategy implements MemberFeeStrategy {

    private static final double BASE_FEE = 100.0;    // Annual fee for Standard membership
    private static final double JOURNAL_FEE = 8.0;   // One-time journal fee (only for new members)

    /**
     * Calculates the total membership fee for a Standard member.
     *
     * @param isFirstTime true if it's the member's first registration (includes journal fee)
     * @return Total fee based on the registration context
     */
    @Override
    public double calculateFee(boolean isFirstTime) {
        return isFirstTime ? BASE_FEE + JOURNAL_FEE : BASE_FEE;
    }

    /**
     * Returns the name of the grade associated with this strategy.
     *
     * @return "Standard"
     */
    @Override
    public String getGrade() {
        return "Standard";
    }
}
