import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class RemnantManager {
    private ArrayList<Remnant> remnantList;
    private Scanner scanner;

    public RemnantManager() {
        remnantList = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addRemnant() {
    System.out.print("Enter name: ");
    String name = scanner.nextLine();

    System.out.print("Enter thickness: ");
    double thickness = Double.parseDouble(scanner.nextLine());

    System.out.print("Enter material: ");
    String material = scanner.nextLine();

    System.out.print("Enter weight: ");
    double weight = Double.parseDouble(scanner.nextLine());

    System.out.print("Enter heat number: ");
    String heatNumber = scanner.nextLine();

    String sql = "INSERT INTO remnants (name, thickness, material, weight, heatNumber) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseHelper.connect();
         java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, name);
        pstmt.setDouble(2, thickness);
        pstmt.setString(3, material);
        pstmt.setDouble(4, weight);
        pstmt.setString(5, heatNumber);
        pstmt.executeUpdate();
        System.out.println("Remnant added!\n");
    } catch (SQLException e) {
        System.out.println("Error adding remnant: " + e.getMessage());
    }
    }

    public void displayAll() {
    String sql = "SELECT * FROM remnants";

    try (Connection conn = DatabaseHelper.connect();
         java.sql.Statement stmt = conn.createStatement();
         java.sql.ResultSet rs = stmt.executeQuery(sql)) {

        boolean foundAny = false;
        while (rs.next()) {
            foundAny = true;
            String name = rs.getString("name");
            double thickness = rs.getDouble("thickness");
            String material = rs.getString("material");
            double weight = rs.getDouble("weight");
            String heatNumber = rs.getString("heatNumber");

            System.out.println(name + " - " + material + " (" + thickness + " thick, " + weight + " lbs, Heat #" + heatNumber + ")");
        }

        if (!foundAny) {
            System.out.println("No remnants found.\n");
        } else {
            System.out.println();
        }

    } catch (SQLException e) {
        System.out.println("Error retrieving remnants: " + e.getMessage());
    }
    }   

    // Later: add methods for remove, search, sort, save/load, etc.
}
