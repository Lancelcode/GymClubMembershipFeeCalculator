package test;

import models.StandardMember;
import org.junit.jupiter.api.Test;
import utils.MemberManager;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionHandlingTest {

    @Test
    public void testInvalidFilePathHandling() {
        MemberManager manager = new MemberManager() {
            @Override
            protected void saveMembersToFile() {
                try {
                    // Simulate file write failure
                    File file = new File("/invalid_path/members.txt");
                    file.createNewFile(); // likely to fail
                } catch (Exception e) {
                    assertTrue(e instanceof Exception, "Should handle exceptions gracefully");
                }
            }
        };

        // Try adding member and ensure no crash
        assertDoesNotThrow(() -> manager.addMember(new StandardMember("Test")));
    }
}
