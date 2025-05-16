package test;

import models.MembershipRecord;
import org.junit.jupiter.api.Test;
import utils.MemberManager;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ExceptionHandlingTest contains unit tests to validate that the MemberManager
 * class handles exceptions and edge cases gracefully without causing system crashes.
 * The class ensures the robustness of the MemberManager class under various faulty
 * or unexpected conditions during file handling and data management operations.
 *
 * Tests included:
 * - Ensures that missing files during initialization are handled without crashing.
 * - Validates that IOException during file save operations is handled internally.
 * - Checks that corrupted input lines in the membership file are skipped gracefully.
 *
 * Each test guarantees that specific exception scenarios are managed effectively,
 * without propagating unhandled errors to the user or the system.
 */
public class ExceptionHandlingTest {

    @Test
    public void testLoadFromMissingFileDoesNotCrash() {
        File file = new File("data/members.txt");
        if (file.exists()) file.delete();

        assertDoesNotThrow(MemberManager::new);
    }

    @Test
    public void testSaveMembersIOExceptionHandledGracefully() throws Exception {
        MemberManager manager = new MemberManager();
        manager.addMember("Faulty", "VIP");

        // Use reflection to break the MEMBER_FILE path
        Field field = MemberManager.class.getDeclaredField("MEMBER_FILE");
        field.setAccessible(true);
        field.set(manager, "/invalid/path/members.txt");

        // Should handle the error internally without crashing
        assertDoesNotThrow(() -> {
            Method saveMethod = MemberManager.class.getDeclaredMethod("saveMembersToFile");
            saveMethod.setAccessible(true);
            ((java.lang.reflect.Method) saveMethod).invoke(manager);
        });
    }

    @Test
    public void testCorruptedLineInFileSkipsGracefully() throws Exception {
        File file = new File("data/members.txt");
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("Corrupted Line Without Proper Format");
        }

        assertDoesNotThrow(MemberManager::new);
    }
}
