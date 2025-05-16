package test;

import models.MembershipRecord;
import org.junit.jupiter.api.*;
import utils.MemberManager;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MemberManager class.
 * This test suite verifies the functionality of the MemberManager class, ensuring
 * operations such as adding members, managing membership records, updating statuses,
 * and calculating total fees are performed correctly and as expected.
 */
public class MemberManagerTest {

    // WARNING: Using the real data file — ideally switch to "data/test_members.txt"
    private static final String TEST_FILE_PATH = "data/test_members.txt";

    /**
     * Clears the membership data file before each test to ensure a clean test environment.
     * Note: This currently affects the real file. For safety, update to use a test file.
     */
    @BeforeEach
    public void clearDataFile() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();
    }

    /**
     * Verifies that when a first-time member is added, the journal fee is included in the total fee.
     */
    @Test
    public void testAddFirstTimeMemberIncludesJournal() {
        MemberManager manager = new MemberManager();
        manager.addMember("Alice", "Standard");

        MembershipRecord record = manager.findCurrentRecordByName("Alice");
        assertNotNull(record);
        assertEquals(108.0, record.getFee(), 0.01);  // 100 + 8 journal
        assertEquals("Standard", record.getGrade());
    }

    /**
     * Verifies that when a member is added again, the journal fee is not charged again.
     * Also checks that the newest membership is marked as 'current' and the previous one becomes 'past'.
     */
    @Test
    public void testAddSameMemberTwiceJournalOnlyOnce() {
        MemberManager manager = new MemberManager();
        manager.addMember("Bob", "Standard");  // First membership (journal included)
        manager.addMember("Bob", "Premium");   // Second membership (journal excluded)

        List<MembershipRecord> history = manager.getHistoryForMember("Bob");
        assertEquals(2, history.size());

        MembershipRecord current = manager.findCurrentRecordByName("Bob");
        assertEquals("Premium", current.getGrade());
        assertEquals(150.0, current.getFee(), 0.01);  // Premium only, no journal

        double total = history.stream().mapToDouble(MembershipRecord::getFee).sum();
        assertEquals(108.0 + 150.0, total, 0.01);  // First with journal, second without
    }

    /**
     * Verifies that only one record is marked as 'current' and all previous ones as 'past'.
     */
    @Test
    public void testQueuePreservesStatus() {
        MemberManager manager = new MemberManager();
        manager.addMember("Cara", "VIP");
        manager.addMember("Cara", "Standard");

        List<MembershipRecord> history = manager.getHistoryForMember("Cara");
        assertEquals(2, history.size());

        boolean hasCurrent = history.stream()
                .anyMatch(r -> r.getStatus().equalsIgnoreCase("current"));
        long pastCount = history.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("past")).count();

        assertTrue(hasCurrent);     // Must have one 'current'
        assertEquals(1, pastCount); // Only one should be 'past'
    }

    /**
     * Verifies that totalFeesCollected adds up correctly after multiple member additions.
     */
    @Test
    public void testTotalFeesCollected() {
        MemberManager manager = new MemberManager();
        manager.addMember("Dan", "Standard");  // 100 + 8
        manager.addMember("Eve", "Premium");   // 150 + 8
        manager.addMember("Dan", "VIP");       // 200 (no journal again)

        double total = manager.getTotalFeesCollected();
        assertEquals(108 + 158 + 200, total, 0.01);
    }
}
