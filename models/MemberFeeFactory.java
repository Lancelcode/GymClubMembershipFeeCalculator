package models;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory class responsible for creating instances of specific
 * membership fee strategies based on a provided membership grade.
 *
 * This class uses the Factory design pattern combined with Java generics and reflection
 * to dynamically instantiate the correct implementation of MemberFeeStrategy at runtime.
 *
 * Supported grades: "Standard", "Premium", "VIP"
 */
public class MemberFeeFactory {

    // A map that links lowercased grade names to their corresponding strategy class
    private static final Map<String, Class<? extends MemberFeeStrategy>> strategyMap = new HashMap<>();

    // Static block to initialize the grade-to-strategy mapping
    static {
        strategyMap.put("standard", StandardFeeStrategy.class);
        strategyMap.put("premium", PremiumFeeStrategy.class);
        strategyMap.put("vip", VIPFeeStrategy.class);
    }

    /**
     * Generic method to return an instance of the correct MemberFeeStrategy implementation
     * based on the input grade string.
     *
     * @param grade The name of the membership grade (e.g., "Standard", "Premium", "VIP")
     * @return A new instance of the corresponding MemberFeeStrategy
     * @throws IllegalArgumentException if the grade is invalid
     * @throws RuntimeException if instantiation fails due to reflection issues
     */
    public static <T extends MemberFeeStrategy> T getStrategy(String grade) {
        // Look up the class from the map (case-insensitive)
        Class<? extends MemberFeeStrategy> clazz = strategyMap.get(grade.toLowerCase());

        if (clazz == null) {
            throw new IllegalArgumentException("Invalid membership grade: " + grade);
        }

        try {
            // Dynamically create a new instance using the no-arg constructor
            return (T) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating fee strategy for grade: " + grade, e);
        }
    }
}
