import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Statement;

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

    public void removeRemnant() {
        System.out.print("\nEnter the name of the remnant to remove: ");
        String nameToRemove = scanner.nextLine().trim();

        String checkSql = "SELECT COUNT(*) FROM remnants WHERE name = ?";

        try (Connection conn = DatabaseHelper.connect();
            PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, nameToRemove);
            ResultSet rs = checkStmt.executeQuery();

            int count = rs.getInt(1);

            if (count == 0) {
                System.out.println("No remnant found with that name.\n");
                return;
            }

            String deleteSql = "DELETE FROM remnants WHERE name = ?";

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setString(1, nameToRemove);
                int rowsAffected = deleteStmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Remnant '" + nameToRemove + "' removed successfully.\n");
                } else {
                    System.out.println("Failed to remove remnant.\n");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error removing remnant: " + e.getMessage());
        }
    }

    public void removeRemnantById() {
        String selectSql = "SELECT id, name, material, thickness, weight, heatNumber FROM remnants";

        try (Connection conn = DatabaseHelper.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectSql)) {

            ArrayList<Integer> ids = new ArrayList<>();

            System.out.println("\nRemnants:");
            while (rs.next()) {
                int id = rs.getInt("id");
                ids.add(id);

                String name = rs.getString("name");
                String material = rs.getString("material");
                double thickness = rs.getDouble("thickness");
                double weight = rs.getDouble("weight");
                String heatNumber = rs.getString("heatNumber");

                System.out.println(id + ": " + name + " - " + material + " (" + thickness + " thick, " + weight + " lbs, Heat #" + heatNumber + ")");
            }

            if (ids.isEmpty()) {
                System.out.println("No remnants to delete.\n");
                return;
            }

            System.out.print("\nEnter the ID of the remnant to remove: ");
            int idToRemove = Integer.parseInt(scanner.nextLine());

            if (!ids.contains(idToRemove)) {
                System.out.println("Invalid ID. No remnant removed.\n");
                return;
            }

            String deleteSql = "DELETE FROM remnants WHERE id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, idToRemove);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Remnant removed successfully.\n");
                } else {
                    System.out.println("Failed to remove remnant.\n");
                }
            }

        } catch (Exception e) {
            System.out.println("Error removing remnant: " + e.getMessage());
        }
    }


}
