package test;

import models.MembershipRecord;
import org.junit.jupiter.api.Test;
import utils.MemberManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MembershipFlowTest {

    // In-memory subclass of MemberManager to avoid file I/O
    static class TestMemberManager extends MemberManager {
        @Override
        protected void saveMembersToFile() {
            // Skip saving during test
        }

        @Override
        protected void loadMembersFromFile() {
            // Skip loading during test
        }
    }

    @Test
    public void testMembershipUpgradeFlow() {
        TestMemberManager manager = new TestMemberManager();

        // Step 1: Add a new Standard member.
        manager.addMember("Alice", "Standard");
        MembershipRecord first = manager.findCurrentRecordByName("Alice");
        assertNotNull(first);
        assertEquals("Standard", first.getGrade());
        assertEquals("current", first.getStatus());

        // Step 2: Upgrade to Premium
        manager.addMember("Alice", "Premium");
        MembershipRecord current = manager.findCurrentRecordByName("Alice");
        assertNotNull(current);
        assertEquals("Premium", current.getGrade());
        assertEquals("current", current.getStatus());

        // Step 3: Validate history
        List<MembershipRecord> history = manager.getHistoryForMember("Alice");
        assertEquals(2, history.size());

        MembershipRecord past = history.get(0);
        assertEquals("Standard", past.getGrade());
        assertEquals("past", past.getStatus());

        // Step 4: Validate total fee only includes one journal fee
        double total = 0.0;
        for (MembershipRecord r : history) {
            total += r.getFee();
        }
        assertEquals(258.0, total, 0.01);
    }
}
