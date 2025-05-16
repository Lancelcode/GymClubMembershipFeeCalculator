package test;

import org.junit.jupiter.api.Test;
import utils.MemberManager;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Standalone boundary tests for MemberManager using a clean test file.
 */
public class BoundaryTest {

    private static final String TEST_FILE_PATH = "data/test_members.txt";

    /**
     * Creates a clean manager that uses test_members.txt without affecting the real data.
     */
    private MemberManager createTestManager() {
        // Delete test file BEFORE the manager loads it
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();

        MemberManager manager = new MemberManager();
        try {
            Field field = MemberManager.class.getDeclaredField("MEMBER_FILE");
            field.setAccessible(true);
            field.set(manager, TEST_FILE_PATH);
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed", e);
        }
        return manager;
    }

    @Test
    public void testEmptyNameShouldNotAddMember() {
        MemberManager manager = createTestManager();
        assertThrows(IllegalArgumentException.class, () -> manager.addMember("", "Standard"));
    }

    @Test
    public void testWhitespaceNameShouldNotAddMember() {
        MemberManager manager = createTestManager();
        assertThrows(IllegalArgumentException.class, () -> manager.addMember("   ", "Premium"));
    }



    @Test
    public void testUnknownMemberReturnsNull() {
        MemberManager manager = createTestManager();
        assertNull(manager.findCurrentRecordByName("Nonexistent"));
    }

    @Test
    public void testInvalidGradeThrowsException() {
        MemberManager manager = createTestManager();
        assertThrows(IllegalArgumentException.class, () -> manager.addMember("Jon", "Gold"));
    }
}
