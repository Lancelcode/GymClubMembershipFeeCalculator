package test;

import models.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
