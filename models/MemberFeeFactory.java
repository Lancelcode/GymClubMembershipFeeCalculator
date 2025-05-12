package models;

public class MemberFeeFactory {

    /**
     * Returns the appropriate MemberFeeStrategy based on membership grade.
     * @param grade The membership grade string (e.g., "Standard")
     * @return The corresponding MemberFeeStrategy implementation
     */
    public static MemberFeeStrategy getStrategy(String grade) {
        switch (grade.toLowerCase()) {
            case "standard":
                return new StandardFeeStrategy();
            case "premium":
                return new PremiumFeeStrategy();
            case "vip":
                return new VIPFeeStrategy();
            default:
                throw new IllegalArgumentException("Invalid membership grade: " + grade);
        }
    }
}
