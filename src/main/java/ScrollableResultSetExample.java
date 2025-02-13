import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScrollableResultSetExample {

    public static void main(String[] args) {

        String jdbcUrl = "jdbc:mysql://localhost:3306/jdbc_test";
        String username = "root";
        String password = "";

        try (
             Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

             // Create scrollable, read-only statement
             Statement stmt = connection.createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             );

             ResultSet rs = stmt.executeQuery("SELECT * FROM employee")) {

            // Iterate through results (forward direction)
            System.out.println("Processing results forward:");
            while (rs.next()) {
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                // Add other columns as needed
                System.out.println("Name: " + name + ", Salary: " + salary);
            }

            // Demonstrate scrolling capability
            System.out.println("\nDemonstrating scroll features:");

            // Jump to first row
            if (rs.first()) {
                System.out.println("First row - Name: " + rs.getString("name"));
            }

            // Jump to last row
            if (rs.last()) {
                System.out.println("Last row - Name: " + rs.getString("name"));
            }

            // Jump to row 3 (if exists)
            if (rs.absolute(3)) {
                System.out.println("Row 3 - Name: " + rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
