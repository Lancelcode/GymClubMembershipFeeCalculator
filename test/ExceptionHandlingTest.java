package test;

import models.MembershipRecord;
import org.junit.jupiter.api.Test;
import utils.MemberManager;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

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
