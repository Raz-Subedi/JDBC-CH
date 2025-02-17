import java.sql.*;

public class ScrollableUpdatableResultSetDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc_test";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = stmt.executeQuery("SELECT id, name, salary FROM employee")) {

            // Displaying initial records
            System.out.println("Initial Records:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " + rs.getString("name") + " - " + rs.getDouble("salary"));
            }

            // Move back to first row and update salary
            if (rs.first()) {
                double newSalary = rs.getDouble("salary") + 1000;
                rs.updateDouble("salary", newSalary);
                rs.updateRow(); // Save the update
                System.out.println("\nUpdated first row salary to: " + newSalary);
            }

            // Insert a new row
            rs.moveToInsertRow();
            rs.updateInt("id", 110); // Set ID (ensure it's unique)
            rs.updateString("name", "New Employee");
            rs.updateDouble("salary", 5000);
            rs.insertRow();
            System.out.println("\nInserted a new employee.");

            // Move to last row and display data
            if (rs.last()) {
                System.out.println("\nLast Record After Insertion:");
                System.out.println(rs.getInt("id") + " - " + rs.getString("name") + " - " + rs.getDouble("salary"));
            }

            // Iterate in reverse order
            System.out.println("\nRecords in Reverse Order:");
            rs.afterLast(); // Move to after last row
            while (rs.previous()) {
                System.out.println(rs.getInt("id") + " - " + rs.getString("name") + " - " + rs.getDouble("salary"));
            }

            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " + rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

