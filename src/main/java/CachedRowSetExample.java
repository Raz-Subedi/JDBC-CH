import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CachedRowSetExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc_test";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Create a CachedRowSet
            CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();

            // Set connection properties
            rowSet.setUrl(url);
            rowSet.setUsername(username);
            rowSet.setPassword(password);

            // Set the query to fetch data
            rowSet.setCommand("SELECT id, name, salary FROM employee");

            // Execute the query and fetch data into the CachedRowSet
            rowSet.execute();

            // Disconnect from the database
            System.out.println("Data fetched. Now working offline...");

            // Iterate through the data
            while (rowSet.next()) {
                System.out.println("ID: " + rowSet.getInt("id") +
                        ", Name: " + rowSet.getString("name") +
                        ", Salary: " + rowSet.getDouble("salary"));
            }

            // Update a row offline
            rowSet.absolute(2); // Move to the 2nd row
            rowSet.updateDouble("salary", rowSet.getDouble("salary") + 700);
            rowSet.updateRow();

            // Insert a new row offline
            rowSet.moveToInsertRow();
            rowSet.updateInt("id", 66);
            rowSet.updateString("name", "Alice");
            rowSet.updateDouble("salary", 2500);
            rowSet.insertRow();
//            rowSet.moveToCurrentRow();

            // Delete a row offline
            rowSet.absolute(2);
            rowSet.deleteRow();
            System.out.println("Deleted 3rd row");

            // **Reconnect to the database and sync changes**
            try (Connection syncConnection = DriverManager.getConnection(url, username, password)) {
                System.out.println("Reconnecting to the database to sync changes...");
                rowSet.acceptChanges(syncConnection); // Pass the active connection
                System.out.println("Changes synchronized successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
