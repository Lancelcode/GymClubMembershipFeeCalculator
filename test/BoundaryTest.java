package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MemberManager;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The BoundaryTest class contains unit tests for validating the boundary and edge cases
 * for the MemberManager class implementation, including scenarios focusing on proper input validation,
 * case sensitivity, exception handling, and correct state management of members.
 */
public class BoundaryTest {

    // Path to a test-specific file used to prevent real data from being overwritten during testing
    protected  String TEST_FILE_PATH = "data/test_members.txt.txt";

    /**
     * This method runs before each test and ensures the test data file is deleted,
     * creating a clean state for every individual test.
     */
    @BeforeEach
    public void clearFile() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();
    }

    /**
     * Test to ensure that adding a member with an empty name throws an IllegalArgumentException.
     */
    @Test
    public void testEmptyNameShouldNotAddMember() {
        MemberManager manager = new MemberManager();
        assertThrows(IllegalArgumentException.class, () -> manager.addMember("", "Standard"));
    }

    /**
     * Test to ensure that adding a member with only whitespace as a name throws an IllegalArgumentException.
     */
    @Test
    public void testWhitespaceNameShouldNotAddMember() {
        MemberManager manager = new MemberManager();
        assertThrows(IllegalArgumentException.class, () -> manager.addMember("   ", "Premium"));
    }

    /**
     * Test to confirm that the system treats names case-insensitively by merging history under a unified key.
     */
    @Test
    public void testCaseInsensitiveNameHandling() {
        MemberManager manager = new MemberManager();
        manager.addMember("LUCY", "VIP");
        manager.addMember("lucy", "Standard");
        List<?> history = manager.getHistoryForMember("Lucy");
        assertEquals(2, history.size()); // Both entries should be merged under the same name
    }

    /**
     * Test to ensure that querying a nonexistent member returns null instead of throwing an error.
     */
    @Test
    public void testUnknownMemberReturnsNull() {
        MemberManager manager = new MemberManager();
        assertNull(manager.findCurrentRecordByName("Nonexistent"));
    }

    /**
     * Test to ensure that trying to add a member with an invalid membership grade throws an IllegalArgumentException.
     */
    @Test
    public void testInvalidGradeThrowsException() {
        MemberManager manager = new MemberManager();
        assertThrows(IllegalArgumentException.class, () -> manager.addMember("Jon", "Gold")); // "Gold" is not a valid grade
    }
}
