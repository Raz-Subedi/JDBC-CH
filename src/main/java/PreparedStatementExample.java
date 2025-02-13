import java.sql.*;
import java.util.Scanner;

public class PreparedStatementExample {
    public static void main(String[] args) {
        try {
            // Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jdbc_test", "root", "");

            // Create PreparedStatement with parameterized query
            String sql = "INSERT INTO people (name, age) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);

            // Create Scanner for user input
            Scanner scanner = new Scanner(System.in);

                System.out.println("\nEnter person details:");
                System.out.print("Enter name: ");
                String name = scanner.nextLine();

                System.out.print("Enter age: ");
                int age = scanner.nextInt();
                scanner.nextLine();

                // Set parameters
                pst.setString(1, name);
                pst.setInt(2, age);

                // Execute the insert
                int rowsAffected = pst.executeUpdate();
                System.out.println(rowsAffected + " person added successfully!");

            // Close resources
            scanner.close();
            pst.close();
            con.close();
            System.out.println("Database connection closed.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
