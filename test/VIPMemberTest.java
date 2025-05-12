package test;

import models.VIPMember;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VIPMemberTest {

    @Test
    public void testCalculateMembershipFee() {
        VIPMember member = new VIPMember("Charlie");
        double expected = 200.0 + 8.0;
        assertEquals(expected, member.calculateMembershipFee(), 0.01);
    }

    @Test
    public void testGetMembershipGrade() {
        VIPMember member = new VIPMember("Charlie");
        assertEquals("VIP", member.getMembershipGrade());
    }

    @Test
    public void testGetName() {
        VIPMember member = new VIPMember("Charlie");
        assertEquals("Charlie", member.getName());
    }
}
