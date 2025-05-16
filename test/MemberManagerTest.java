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
 * operations such as adding members, managing membership records, and calculating fees
 * are performed correctly.
 */
public class MemberManagerTest {

    private static final String TEST_FILE_PATH = "data/members.txt";

    @BeforeEach
    public void clearDataFile() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();
    }

    @Test
    public void testAddFirstTimeMemberIncludesJournal() {
        MemberManager manager = new MemberManager();
        manager.addMember("Alice", "Standard");
        MembershipRecord record = manager.findCurrentRecordByName("Alice");
        assertNotNull(record);
        assertEquals(108.0, record.getFee(), 0.01);
        assertEquals("Standard", record.getGrade());
    }

    @Test
    public void testAddSameMemberTwiceJournalOnlyOnce() {
        MemberManager manager = new MemberManager();
        manager.addMember("Bob", "Standard");
        manager.addMember("Bob", "Premium");

        List<MembershipRecord> history = manager.getHistoryForMember("Bob");
        assertEquals(2, history.size());

        MembershipRecord current = manager.findCurrentRecordByName("Bob");
        assertEquals("Premium", current.getGrade());
        assertEquals(150.0, current.getFee(), 0.01); // no journal fee

        double total = history.stream().mapToDouble(MembershipRecord::getFee).sum();
        assertEquals(108.0 + 150.0, total, 0.01);
    }

    @Test
    public void testQueuePreservesStatus() {
        MemberManager manager = new MemberManager();
        manager.addMember("Cara", "VIP");
        manager.addMember("Cara", "Standard");

        List<MembershipRecord> history = manager.getHistoryForMember("Cara");
        assertEquals(2, history.size());

        boolean hasCurrent = history.stream().anyMatch(r -> r.getStatus().equalsIgnoreCase("current"));
        long pastCount = history.stream().filter(r -> r.getStatus().equalsIgnoreCase("past")).count();

        assertTrue(hasCurrent);
        assertEquals(1, pastCount);
    }

    @Test
    public void testTotalFeesCollected() {
        MemberManager manager = new MemberManager();
        manager.addMember("Dan", "Standard");
        manager.addMember("Eve", "Premium");
        manager.addMember("Dan", "VIP");

        double total = manager.getTotalFeesCollected();
        assertEquals(108 + 158 + 200, total, 0.01);
    }
}
