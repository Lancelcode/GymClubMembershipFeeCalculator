import ui.GymClubGUI;

/**
 * Entry point for the Gym Club Membership Fee Calculator application.
 * This class initializes the GUI for managing memberships and calculating fees.
 *
 * The GUI interface includes options to:
 * - Add a new member with a specific membership grade.
 * - View a list of all active members.
 * - Display a detailed receipt history for a specific member's subscriptions.
 *
 * The application automates membership management, including tracking history
 * and ensuring user-friendly interaction via the Java Swing framework.
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(GymClubGUI::new);
    }
}
