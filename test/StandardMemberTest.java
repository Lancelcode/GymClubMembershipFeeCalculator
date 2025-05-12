package test;

import models.StandardMember;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StandardMemberTest {

    @Test
    public void testCalculateMembershipFee() {
        StandardMember member = new StandardMember("Alice");
        double expected = 100.0 + 8.0; // base + journal fee
        assertEquals(expected, member.calculateMembershipFee(), 0.01);
    }

    @Test
    public void testGetMembershipGrade() {
        StandardMember member = new StandardMember("Alice");
        assertEquals("Standard", member.getMembershipGrade());
    }

    @Test
    public void testGetName() {
        StandardMember member = new StandardMember("Alice");
        assertEquals("Alice", member.getName());
    }
}
