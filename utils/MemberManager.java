package utils;

import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberManager {

    private List<Member> memberList;
    private double totalFeesCollected;

    private final String FILE_PATH = "data/members.txt";

    public MemberManager() {
        this.memberList = new ArrayList<>();
        this.totalFeesCollected = 0.0;
        loadMembersFromFile();
    }

    public void addMember(Member member) {
        if (member != null) {
            memberList.add(member);
            totalFeesCollected += member.calculateMembershipFee();
            saveMembersToFile();  // auto-save
        }
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(memberList);
    }

    public double getTotalFeesCollected() {
        return totalFeesCollected;
    }

    public Member findMemberByName(String name) {
        for (Member m : memberList) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public void clearAll() {
        memberList.clear();
        totalFeesCollected = 0.0;
        saveMembersToFile();
    }

    protected void saveMembersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Member m : memberList) {
                writer.printf("%s;%s\n", m.getName(), m.getMembershipGrade());
            }
        } catch (IOException e) {
            System.err.println("Error saving members: " + e.getMessage());
        }
    }

    private void loadMembersFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String grade = parts[1].trim();

                    Member m;
                    switch (grade.toLowerCase()) {
                        case "standard":
                            m = new StandardMember(name);
                            break;
                        case "premium":
                            m = new PremiumMember(name);
                            break;
                        case "vip":
                            m = new VIPMember(name);
                            break;
                        default:
                            m = null;
                            break;
                    }

                    if (m != null) {
                        memberList.add(m);
                        totalFeesCollected += m.calculateMembershipFee();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading members: " + e.getMessage());
        }
    }
}
