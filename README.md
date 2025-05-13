
Gym Club Membership Fee Calculator
==================================

Overview:
---------
This Java application automates the calculation and management of gym club membership fees. It supports multiple membership grades, a one-time journal fee, upgrade tracking, and a menu-driven GUI interface.

Features:
---------
- Add a new member with selected membership grade: Standard, Premium, or VIP
- Apply a one-time journal subscription fee (£8) only when a member is first registered
- Support for upgrading membership (previous membership marked as past)
- Queue-like tracking of membership history for each user
- Total fees collected are tracked
- Receipts show current and past memberships clearly
- Persistent storage of data in /data/members.txt
- Menu-driven GUI built using Java Swing
- Thoroughly tested using JUnit (unit tests, exception handling, integration)

GUI Menu Options:
-----------------
1. Add a New Member
    - Prompts for name and grade
    - Applies journal fee only if first-time member

2. Display List of Members
    - Shows all current active members and their membership grades

3. Display Member’s Receipt
    - Shows the current membership receipt
    - Optionally shows previous receipts
    - Includes formatted output and total price

4. Exit
    - Automatically saves all data to file on exit

Setup Instructions:
-------------------
Requirements:
- Java 11 or higher (tested with OpenJDK 17+)
- IntelliJ IDEA or any compatible Java IDE
- JUnit 5 (included via Maven or manual .jar setup)

Steps:
1. Clone or download the project folder
2. Open in IntelliJ IDEA or your preferred Java IDE
3. Run the 'Main.java' class to start the GUI

Testing:
--------
JUnit tests are located in the 'test/' package:
- Unit tests for fee calculations and receipt formatting
- Exception tests for file handling errors
- Boundary and edge-case tests
- Integration test simulating full membership flow:
  (Add → Upgrade → Reload → Validate receipts and total fees)

Data Persistence:
-----------------
All member data is saved to:
/data/members.txt

Each line contains:
Name;Grade;Fee;Date;Status (current or past)

This data is automatically loaded when the app starts and saved again on exit.

License:
--------
This application was developed for educational and assessment purposes only. All dependencies are open-source.
