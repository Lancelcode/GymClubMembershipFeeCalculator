package test;

import models.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
