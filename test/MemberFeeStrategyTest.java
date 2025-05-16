package test;

import models.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The MemberFeeStrategyTest class contains unit tests to validate the behavior and correctness
 * of implementations of the MemberFeeStrategy interface.
 *
 * This ensures:
 * - Correct fee calculation logic for each membership grade.
 * - Accurate handling of journal fees.
 * - Correct grade names returned by each strategy.
 *
 * These tests verify that each membership strategy works as intended using the JUnit testing framework.
 */
public class MemberFeeStrategyTest {

    /**
     * Verifies that the Standard membership fee is calculated correctly without the journal fee.
     */
    @Test
    public void testStandardFeeWithoutJournal() {
        MemberFeeStrategy strategy = new StandardFeeStrategy();
        assertEquals(100.0, strategy.calculateFee(false), 0.01);
    }

    /**
     * Verifies that the Standard membership fee includes the journal fee when applicable.
     */
    @Test
    public void testStandardFeeWithJournal() {
        MemberFeeStrategy strategy = new StandardFeeStrategy();
        assertEquals(108.0, strategy.calculateFee(true), 0.01);
    }

    /**
     * Verifies that the Premium membership fee is correct without journal.
     */
    @Test
    public void testPremiumFeeWithoutJournal() {
        MemberFeeStrategy strategy = new PremiumFeeStrategy();
        assertEquals(150.0, strategy.calculateFee(false), 0.01);
    }

    /**
     * Verifies that the Premium membership fee includes journal fee when applicable.
     */
    @Test
    public void testPremiumFeeWithJournal() {
        MemberFeeStrategy strategy = new PremiumFeeStrategy();
        assertEquals(158.0, strategy.calculateFee(true), 0.01);
    }

    /**
     * Verifies that the VIP membership fee is correct without journal.
     */
    @Test
    public void testVIPFeeWithoutJournal() {
        MemberFeeStrategy strategy = new VIPFeeStrategy();
        assertEquals(200.0, strategy.calculateFee(false), 0.01);
    }

    /**
     * Verifies that the VIP membership fee includes the journal fee when applicable.
     */
    @Test
    public void testVIPFeeWithJournal() {
        MemberFeeStrategy strategy = new VIPFeeStrategy();
        assertEquals(208.0, strategy.calculateFee(true), 0.01);
    }

    /**
     * Confirms that each strategy returns the correct membership grade name.
     */
    @Test
    public void testGradeNames() {
        assertEquals("Standard", new StandardFeeStrategy().getGrade());
        assertEquals("Premium", new PremiumFeeStrategy().getGrade());
        assertEquals("VIP", new VIPFeeStrategy().getGrade());
    }
}
