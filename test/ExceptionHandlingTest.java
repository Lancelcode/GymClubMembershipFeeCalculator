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
 */
public class ExceptionHandlingTest {

    /**
     * Verifies that initializing MemberManager when the members.txt file does not exist
     * does not crash the application.
     */
    @Test
    public void testLoadFromMissingFileDoesNotCrash() {
        File file = new File("data/members.txt");
        if (file.exists()) file.delete();  // Simulate missing file scenario

        assertDoesNotThrow(MemberManager::new);  // Initialization should not throw an error
    }

    /**
     * Verifies that an IOException during saveMembersToFile() is handled internally.
     * This test uses Java Reflection to forcefully break the MEMBER_FILE path and ensure
     * that the exception does not propagate.
     */
    @Test
    public void testSaveMembersIOExceptionHandledGracefully() throws Exception {
        MemberManager manager = new MemberManager();
        manager.addMember("Faulty", "VIP");

        // Use reflection to simulate an invalid file path scenario
        Field field = MemberManager.class.getDeclaredField("MEMBER_FILE");
        field.setAccessible(true);
        field.set(manager, "/invalid/path/members.txt");

        // Ensure no exception is thrown even if the file path is invalid
        assertDoesNotThrow(() -> {
            Method saveMethod = MemberManager.class.getDeclaredMethod("saveMembersToFile");
            saveMethod.setAccessible(true);
            saveMethod.invoke(manager);
        });
    }

    /**
     * Verifies that when the members.txt file contains corrupted or malformed data,
     * the application does not crash and skips over the invalid lines gracefully.
     */
    @Test
    public void testCorruptedLineInFileSkipsGracefully() throws Exception {
        File file = new File("data/members.txt");

        // Write a corrupted line to simulate bad input format
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("Corrupted Line Without Proper Format");
        }

        // Ensure the MemberManager does not crash when loading a corrupted line
        assertDoesNotThrow(MemberManager::new);
    }
}
