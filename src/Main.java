import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize the database and table
        DatabaseHelper.initializeDatabase();
        RemnantManager manager = new RemnantManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu
            System.out.println("Steel Remnant Tracker");
            System.out.println("======================");
            System.out.println("1 - Add Remnant");
            System.out.println("2 - View All Remnants");
            System.out.println("3 - Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();
            System.out.println();

            // Handle user input
            switch (input) {
                case "1":
                    manager.addRemnant();
                    break;
                case "2":
                    manager.displayAll();
                    break;
                case "3":
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return; // Ends the program
                default:
                    System.out.println("Invalid option. Try again.\n");
                    break;
            }
        }
    }
}
