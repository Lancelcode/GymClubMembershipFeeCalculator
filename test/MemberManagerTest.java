package test;

import models.StandardMember;
import models.PremiumMember;
import models.VIPMember;
import models.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MemberManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MemberManagerTest {

    private MemberManager manager;

    @BeforeEach
    public void setUp() {
        manager = new MemberManager();
    }

    @Test
    public void testAddMemberAndTotalFee() {
        Member m1 = new StandardMember("Alice");
        Member m2 = new PremiumMember("Bob");

        manager.addMember(m1);
        manager.addMember(m2);

        double expectedTotal = m1.calculateMembershipFee() + m2.calculateMembershipFee();
        assertEquals(expectedTotal, manager.getTotalFeesCollected(), 0.01);
    }

    @Test
    public void testGetAllMembers() {
        manager.addMember(new StandardMember("Alice"));
        manager.addMember(new VIPMember("Charlie"));

        List<Member> members = manager.getAllMembers();
        assertEquals(2, members.size());
        assertEquals("Alice", members.get(0).getName());
        assertEquals("Charlie", members.get(1).getName());
    }

    @Test
    public void testFindMemberByName() {
        Member m1 = new VIPMember("Charlie");
        manager.addMember(m1);

        Member found = manager.findMemberByName("charlie"); // case-insensitive
        assertNotNull(found);
        assertEquals("Charlie", found.getName());
    }

    @Test
    public void testFindNonExistentMember() {
        Member found = manager.findMemberByName("NonExistent");
        assertNull(found);
    }

    @Test
    public void testClearAll() {
        manager.addMember(new StandardMember("Alice"));
        manager.clearAll();

        assertEquals(0, manager.getAllMembers().size());
        assertEquals(0.0, manager.getTotalFeesCollected(), 0.01);
    }


    @Test
    public void testAddDuplicateNames() {
        Member m1 = new StandardMember("Sam");
        Member m2 = new PremiumMember("Sam");

        manager.addMember(m1);
        manager.addMember(m2);

        assertEquals(2, manager.getAllMembers().size(), "Should allow duplicate names");
    }

    @Test
    public void testSearchEmptyString() {
        Member result = manager.findMemberByName("");
        assertNull(result, "Searching with empty string should return null");
    }

    @Test
    public void testSearchNull() {
        Member result = manager.findMemberByName(null);
        assertNull(result, "Searching with null should return null");
    }

    @Test
    public void testGetAllMembersWhenEmpty() {
        List<Member> members = manager.getAllMembers();
        assertTrue(members.isEmpty(), "Should return empty list if no members added");
    }
}
