package models;

import java.util.HashMap;
import java.util.Map;

public class MemberFeeFactory {

    private static final Map<String, Class<? extends MemberFeeStrategy>> strategyMap = new HashMap<>();

    static {
        strategyMap.put("standard", StandardFeeStrategy.class);
        strategyMap.put("premium", PremiumFeeStrategy.class);
        strategyMap.put("vip", VIPFeeStrategy.class);
    }

    public static <T extends MemberFeeStrategy> T getStrategy(String grade) {
        Class<? extends MemberFeeStrategy> clazz = strategyMap.get(grade.toLowerCase());
        if (clazz == null) {
            throw new IllegalArgumentException("Invalid membership grade: " + grade);
        }
        try {
            return (T) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating fee strategy for grade: " + grade, e);
        }
    }
}

