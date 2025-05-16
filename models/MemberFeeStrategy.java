package models;

/**
 * Represents a strategy interface for calculating membership fees for different
 * membership grades (e.g., Standard, Premium, VIP).
 *
 * This interface defines the contract that all membership fee strategy classes must follow.
 * Implementing classes will provide their own logic for fee calculation and grade identification.
 *
 * This design follows the Strategy Pattern, allowing the system to easily switch between
 * different membership fee policies without modifying existing code.
 */
public interface MemberFeeStrategy {

    /**
     * Calculates the membership fee.
     *
     * @param includeJournal true if the journal fee (£8) should be included
     *                       (e.g., for first-time registration), false otherwise
     * @return The total calculated fee as a double
     */
    double calculateFee(boolean includeJournal);

    /**
     * Returns the name of the membership grade associated with the strategy.
     * For example: "Standard", "Premium", or "VIP"
     *
     * @return Grade name as a string
     */
    String getGrade();
}
