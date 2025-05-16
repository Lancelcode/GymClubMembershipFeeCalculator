package test;

import models.MembershipRecord;
import org.junit.jupiter.api.Test;
import utils.MemberManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The MembershipFlowTest class contains unit tests to validate the membership
 * management workflow, specifically focusing on upgrading memberships and
 * maintaining a consistent record history. This test ensures the integrity
 * of operations such as adding new members, upgrading member grades, maintaining
 * historical records, and calculating total fees.
 *
 * TestMemberManager is used here to override file-saving methods to ensure
 * no actual file I/O occurs during testing.
 */
public class MembershipFlowTest {

    /**
     * In-memory test subclass of MemberManager.
     * Overrides load/save methods to avoid reading/writing from disk.
     */
    static class TestMemberManager extends MemberManager {
        @Override
        protected void saveMembersToFile() {
            // Disable file saving during test
        }

        @Override
        protected void loadMembersFromFile() {
            // Disable file loading during test
        }
    }

    /**
     * This test simulates a full upgrade flow for a member and validates:
     * - Initial membership record creation with journal fee.
     * - Membership upgrade with no journal fee.
     * - Correct historical status assignment.
     * - Accurate total fee calculation.
     */
    @Test
    public void testMembershipUpgradeFlow() {
        TestMemberManager manager = new TestMemberManager();

        // Step 1: Add a new member with Standard membership (includes journal)
        manager.addMember("Alice", "Standard");
        MembershipRecord first = manager.findCurrentRecordByName("Alice");
        assertNotNull(first);
        assertEquals("Standard", first.getGrade());
        assertEquals("current", first.getStatus());

        // Step 2: Upgrade member to Premium (no additional journal fee)
        manager.addMember("Alice", "Premium");
        MembershipRecord current = manager.findCurrentRecordByName("Alice");
        assertNotNull(current);
        assertEquals("Premium", current.getGrade());
        assertEquals("current", current.getStatus());

        // Step 3: Verify full history contains 2 entries
        List<MembershipRecord> history = manager.getHistoryForMember("Alice");
        assertEquals(2, history.size());

        // Ensure the earlier record is now marked as "past"
        MembershipRecord past = history.get(0);
        assertEquals("Standard", past.getGrade());
        assertEquals("past", past.getStatus());

        // Step 4: Confirm total fees = 108 (Standard) + 150 (Premium)
        double total = history.stream().mapToDouble(MembershipRecord::getFee).sum();
        assertEquals(258.0, total, 0.01);
    }
}
