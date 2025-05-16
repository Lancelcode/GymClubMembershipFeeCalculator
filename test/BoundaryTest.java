package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.MemberManager;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoundaryTest {

    private static final String TEST_FILE_PATH = "data/test_members.txt";

    @BeforeEach
    public void clearFile() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();
    }

    @Test
    public void testEmptyNameShouldNotAddMember() {
        MemberManager manager = new MemberManager();
        assertThrows(IllegalArgumentException.class, () -> manager.addMember("", "Standard"));
    }

    @Test
    public void testWhitespaceNameShouldNotAddMember() {
        MemberManager manager = new MemberManager();
        assertThrows(IllegalArgumentException.class, () -> manager.addMember("   ", "Premium"));
    }

    @Test
    public void testCaseInsensitiveNameHandling() {
        MemberManager manager = new MemberManager();
        manager.addMember("LUCY", "VIP");
        manager.addMember("lucy", "Standard");
        List<?> history = manager.getHistoryForMember("Lucy");
        assertEquals(2, history.size());
    }

    @Test
    public void testUnknownMemberReturnsNull() {
        MemberManager manager = new MemberManager();
        assertNull(manager.findCurrentRecordByName("Nonexistent"));
    }

    @Test
    public void testInvalidGradeThrowsException() {
        MemberManager manager = new MemberManager();
        assertThrows(IllegalArgumentException.class, () -> manager.addMember("Jon", "Gold"));
    }
}
