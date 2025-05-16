package test;

import models.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The MemberFeeStrategyTest class contains unit tests to validate the behavior and correctness
 * of implementations of the MemberFeeStrategy interface.
 * It ensures that custom fee calculation logic and grade-related methods of different strategies work as expected.
 *
 * Tests include validation for:
 * - Correct fee calculation for various membership grades (Standard, Premium, VIP), both with and without journal fees.
 * - Proper identification of the membership grade names.
 *
 * The tests use JUnit as the testing framework and include assertions to verify outcomes.
 */
public class MemberFeeStrategyTest {

    @Test
    public void testStandardFeeWithoutJournal() {
        MemberFeeStrategy strategy = new StandardFeeStrategy();
        assertEquals(100.0, strategy.calculateFee(false), 0.01);
    }

    @Test
    public void testStandardFeeWithJournal() {
        MemberFeeStrategy strategy = new StandardFeeStrategy();
        assertEquals(108.0, strategy.calculateFee(true), 0.01);
    }

    @Test
    public void testPremiumFeeWithoutJournal() {
        MemberFeeStrategy strategy = new PremiumFeeStrategy();
        assertEquals(150.0, strategy.calculateFee(false), 0.01);
    }

    @Test
    public void testPremiumFeeWithJournal() {
        MemberFeeStrategy strategy = new PremiumFeeStrategy();
        assertEquals(158.0, strategy.calculateFee(true), 0.01);
    }

    @Test
    public void testVIPFeeWithoutJournal() {
        MemberFeeStrategy strategy = new VIPFeeStrategy();
        assertEquals(200.0, strategy.calculateFee(false), 0.01);
    }

    @Test
    public void testVIPFeeWithJournal() {
        MemberFeeStrategy strategy = new VIPFeeStrategy();
        assertEquals(208.0, strategy.calculateFee(true), 0.01);
    }

    @Test
    public void testGradeNames() {
        assertEquals("Standard", new StandardFeeStrategy().getGrade());
        assertEquals("Premium", new PremiumFeeStrategy().getGrade());
        assertEquals("VIP", new VIPFeeStrategy().getGrade());
    }
}
