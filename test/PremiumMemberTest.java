package test;

import models.PremiumMember;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PremiumMemberTest {

    @Test
    public void testCalculateMembershipFee() {
        PremiumMember member = new PremiumMember("Bob");
        double expected = 150.0 + 8.0;
        assertEquals(expected, member.calculateMembershipFee(), 0.01);
    }

    @Test
    public void testGetMembershipGrade() {
        PremiumMember member = new PremiumMember("Bob");
        assertEquals("Premium", member.getMembershipGrade());
    }

    @Test
    public void testGetName() {
        PremiumMember member = new PremiumMember("Bob");
        assertEquals("Bob", member.getName());
    }
}

