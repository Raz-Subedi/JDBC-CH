import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScrollSensitiveUpdatableExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc_test";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Create a scroll-sensitive and updatable ResultSet
            Statement stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            // Execute a query
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee");
            while (rs.next()) {
                System.out.println("Id: "+rs.getInt("id") + ", Name: " + rs.getString("name") + ", Salary: " + rs.getInt("salary"));
            }

            // Move to the first row
            rs.first();
            System.out.println("First row - Id: "+rs.getInt("id") + ", Name: " + rs.getString("name") + ", Salary: " + rs.getInt("salary"));

            // Move to the last row
            rs.last();
            System.out.println("Last row - Id: "+rs.getInt("id") +", Name: " + rs.getString("name") + ", Salary: " + rs.getInt("salary"));

            // Move to the 3rd row (if it exists)
            if (rs.absolute(3)) {
                System.out.println("3rd row - Id: "+rs.getInt("id") +", Name: " + rs.getString("name") + ", Salary: " + rs.getInt("salary"));

                // Update the current row
                rs.updateInt("salary", rs.getInt("salary") + 5); // Increment salary by 5
                rs.updateRow(); // Commit the update to the database
                System.out.println("Updated 3rd row - New Salary: " + rs.getInt("salary"));
            }

            // Insert a new row
            rs.moveToInsertRow(); // Move to the insert row
            rs.updateInt("id",78);
            rs.updateString("name", "Alice");
            rs.updateInt("salary", 25000);
            rs.insertRow(); // Commit the insert to the database
            System.out.println("Inserted a new row for Alice.");

            // Move back to the current row
         //   rs.moveToCurrentRow();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int salary = rs.getInt("salary");
                System.out.println("Id: "+id+", Name: "+name+", Salary: "+salary);
            }

            // Delete the 5th row (if it exists)
            if (rs.absolute(5)) {
                rs.deleteRow(); // Delete the current row
                System.out.println("Deleted the 5th row.");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}