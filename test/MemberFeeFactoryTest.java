package test;

import models.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MemberFeeFactory class, ensuring its correct behavior
 * when creating and managing membership fee strategies based on membership grades.
 *
 * This class verifies the following:
 * - The appropriate MemberFeeStrategy subclass is instantiated for valid membership grades.
 * - The grade returned by the instantiated strategy matches the input grade.
 * - Case insensitivity is handled when resolving membership grades.
 * - Exceptions are thrown for invalid membership grades.
 */
public class MemberFeeFactoryTest {

    @Test
    public void testStandardFactory() {
        MemberFeeStrategy strategy = MemberFeeFactory.getStrategy("Standard");
        assertTrue(strategy instanceof StandardFeeStrategy);
        assertEquals("Standard", strategy.getGrade());
    }

    @Test
    public void testPremiumFactory() {
        MemberFeeStrategy strategy = MemberFeeFactory.getStrategy("Premium");
        assertTrue(strategy instanceof PremiumFeeStrategy);
        assertEquals("Premium", strategy.getGrade());
    }

    @Test
    public void testVIPFactory() {
        MemberFeeStrategy strategy = MemberFeeFactory.getStrategy("VIP");
        assertTrue(strategy instanceof VIPFeeStrategy);
        assertEquals("VIP", strategy.getGrade());
    }

    @Test
    public void testCaseInsensitiveFactory() {
        MemberFeeStrategy strategy = MemberFeeFactory.getStrategy("standard");
        assertTrue(strategy instanceof StandardFeeStrategy);
    }

    @Test
    public void testInvalidGradeThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MemberFeeFactory.getStrategy("Gold");
        });
        assertTrue(exception.getMessage().contains("Invalid membership grade"));
    }
}
