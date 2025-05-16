package models;

/**
 * Represents a strategy for calculating membership fees based on different grades or types
 * of membership. Classes implementing this interface provide specific fee calculation logic
 * and specify the associated membership grade.
 */
public interface MemberFeeStrategy {
    /**
     * Calculates the membership fee based on whether to include the journal fee.
     * @param includeJournal true if journal fee should be added
     * @return the total membership fee
     */
    double calculateFee(boolean includeJournal);

    /**
     * Returns the membership grade name (e.g., "Standard").
     */
    String getGrade();
}
