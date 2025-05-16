package utils;

import models.MembershipRecord;
import models.MemberFeeStrategy;
import models.MemberFeeFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * The MemberManager is responsible for managing member data, including
 * registration, fee calculation, and membership history tracking.
 *
 * Members are stored using a Map where each name maps to a queue of membership records.
 * - Only one record is marked as "current" at a time.
 * - Older records are marked as "past".
 *
 * Data persistence is handled by reading from and writing to a file on disk.
 */
public class MemberManager {

    // Maps lowercase member names to their membership history
    private Map<String, Queue<MembershipRecord>> memberMap;

    // Tracks the total membership fees collected
    private double totalFeesCollected;

    // File used for saving and loading member records
    private final String MEMBER_FILE = "data/members.txt";

    /**
     * Constructor initializes data structures and loads members from file.
     */
    public MemberManager() {
        this.memberMap = new HashMap<>();
        this.totalFeesCollected = 0.0;
        loadMembersFromFile();
    }

    /**
     * Adds a new membership record for a user.
     * Handles new and returning members, calculates the correct fee,
     * updates record statuses, and saves the data to file.
     *
     * @param name  The member's full name
     * @param grade The membership grade (Standard, Premium, VIP)
     */
    public void addMember(String name, String grade) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null, empty, or whitespace.");
        }

        String key = name.toLowerCase();
        Queue<MembershipRecord> history = memberMap.getOrDefault(key, new LinkedList<>());

        boolean isFirstTime = history.isEmpty();

        MemberFeeStrategy strategy = MemberFeeFactory.getStrategy(grade);
        double fee = strategy.calculateFee(isFirstTime);

        MembershipRecord newRecord = new MembershipRecord(
                name,
                strategy.getGrade(),
                fee,
                LocalDate.now(),
                "current"
        );

        // Mark all existing records as past
        for (MembershipRecord r : history) {
            r.setStatus("past");
        }

        history.add(newRecord);
        memberMap.put(key, history);
        totalFeesCollected += fee;
        saveMembersToFile();
    }

    /**
     * Retrieves a list of all current members and their grades.
     *
     * @return List of "Name;Grade" formatted strings
     */
    public List<String> getAllMembers() {
        List<String> members = new ArrayList<>();
        for (Map.Entry<String, Queue<MembershipRecord>> entry : memberMap.entrySet()) {
            MembershipRecord latest = getCurrentRecord(entry.getValue());
            if (latest != null) {
                members.add(latest.getName() + ";" + latest.getGrade());
            }
        }
        return members;
    }

    /**
     * Returns the total amount of membership fees collected so far.
     */
    public double getTotalFeesCollected() {
        return totalFeesCollected;
    }

    /**
     * Finds and returns the current membership record for the given member name.
     *
     * @param name The member’s name
     * @return The latest active MembershipRecord or null if not found
     */
    public MembershipRecord findCurrentRecordByName(String name) {
        Queue<MembershipRecord> history = memberMap.get(name.toLowerCase());
        if (history == null) return null;
        return getCurrentRecord(history);
    }

    /**
     * Retrieves the full membership history for the given member name.
     *
     * @param name The member's name
     * @return List of MembershipRecord objects
     */
    public List<MembershipRecord> getHistoryForMember(String name) {
        Queue<MembershipRecord> history = memberMap.get(name.toLowerCase());
        if (history == null) return new ArrayList<>();
        return new ArrayList<>(history);
    }

    /**
     * Saves the entire memberMap to a file for persistence.
     * Each MembershipRecord is written on a new line.
     */
    protected void saveMembersToFile() {
        try {
            new File("data").mkdirs();  // Ensure directory exists
            System.out.println("Saving members to file...");

            PrintWriter writer = new PrintWriter(new FileWriter(MEMBER_FILE));
            for (Queue<MembershipRecord> records : memberMap.values()) {
                for (MembershipRecord r : records) {
                    writer.println(r.toFileString());
                }
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving members: " + e.getMessage());
        }
    }

    /**
     * Loads members from the storage file if it exists.
     * Skips any malformed or invalid lines.
     */
    protected void loadMembersFromFile() {
        File file = new File(MEMBER_FILE);
        if (!file.exists()) {
            System.out.println("No existing member file. Starting with empty state.");
            return;
        }

        System.out.println("Loading members from file: " + MEMBER_FILE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                System.out.println("Read line: " + line);

                MembershipRecord record = MembershipRecord.fromFileString(line);
                if (record != null) {
                    String key = record.getName().toLowerCase();
                    Queue<MembershipRecord> history = memberMap.getOrDefault(key, new LinkedList<>());
                    history.add(record);
                    memberMap.put(key, history);
                    totalFeesCollected += record.getFee();
                    count++;
                } else {
                    System.out.println(" Skipped malformed line.");
                }
            }

            System.out.println(" Finished loading " + count + " membership records.");
        } catch (IOException e) {
            System.err.println("Error loading members: " + e.getMessage());
        }
    }

    /**
     * Helper method to retrieve the current active record from the history.
     *
     * @param history A queue of membership records
     * @return The active ("current") record, or null if none is active
     */
    private MembershipRecord getCurrentRecord(Queue<MembershipRecord> history) {
        for (MembershipRecord r : history) {
            if (r.getStatus().equalsIgnoreCase("current")) {
                return r;
            }
        }
        return null;
    }
}
