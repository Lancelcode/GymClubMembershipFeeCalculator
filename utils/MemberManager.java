package utils;

import models.MembershipRecord;
import models.MemberFeeStrategy;
import models.MemberFeeFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * The MemberManager is responsible for managing member data, including
 * the registration, retrieval, and history of members. It supports operations
 * such as adding a member, viewing all members, retrieving a member's membership
 * history, and calculating total fees collected.
 *
 * MemberManager handles persistence by saving and loading member records
 * to and from a file. It uses a `Map` where each member is identified by their
 * name (case insensitive), mapping to a queue of membership records. The most
 * recent "current" record is marked as such, while older records are marked as "past."
 */
public class MemberManager {

    private Map<String, Queue<MembershipRecord>> memberMap;
    private double totalFeesCollected;
    private final String MEMBER_FILE = "data/members.txt";

    public MemberManager() {
        this.memberMap = new HashMap<>();
        this.totalFeesCollected = 0.0;
        loadMembersFromFile();
    }

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

        for (MembershipRecord r : history) {
            r.setStatus("past");
        }

        history.add(newRecord);
        memberMap.put(key, history);
        totalFeesCollected += fee;
        saveMembersToFile();
    }

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

    public double getTotalFeesCollected() {
        return totalFeesCollected;
    }

    public MembershipRecord findCurrentRecordByName(String name) {
        Queue<MembershipRecord> history = memberMap.get(name.toLowerCase());
        if (history == null) return null;
        return getCurrentRecord(history);
    }

    public List<MembershipRecord> getHistoryForMember(String name) {
        Queue<MembershipRecord> history = memberMap.get(name.toLowerCase());
        if (history == null) return new ArrayList<>();
        return new ArrayList<>(history);
    }

    protected void saveMembersToFile() {
        try {
            new File("data").mkdirs();
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

    private MembershipRecord getCurrentRecord(Queue<MembershipRecord> history) {
        for (MembershipRecord r : history) {
            if (r.getStatus().equalsIgnoreCase("current")) {
                return r;
            }
        }
        return null;
    }
}
