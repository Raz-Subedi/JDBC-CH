
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class CachedRowSetExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc_test";
        String user = "root";
        String password = "";

        try {
            // Create a CachedRowSet
            RowSetFactory rsf = RowSetProvider.newFactory();
            CachedRowSet rowSet = rsf.createCachedRowSet();
            // Set connection details
            rowSet.setUrl(url);
            rowSet.setUsername(user);
            rowSet.setPassword(password);

            // Set the SQL query
            rowSet.setCommand("SELECT id, name, salary FROM employee");

            // Execute the query and populate the RowSet
            rowSet.execute();

            // Print initial data
            System.out.println("Initial Data:");
            printEmployeeData(rowSet);

            // Add a new employee (offline)
            rowSet.moveToInsertRow(); // Prepare to insert a new row
            rowSet.updateInt("id", 101); // Set ID (primary key)
            rowSet.updateString("name", "Alice"); // Set name
            rowSet.updateDouble("salary", 75000.0); // Set salary
            rowSet.insertRow(); // Insert the new row
            rowSet.moveToCurrentRow(); // Return to the current row

            // Update an employee's salary (offline)
            rowSet.absolute(1); // Move to the first row
            rowSet.updateDouble("salary", 85000.0); // Update salary
            rowSet.updateRow(); // Commit the update

            // Delete an employee (offline)
            rowSet.absolute(2); // Move to the second row
            rowSet.deleteRow(); // Delete the row

            // Sync changes back to the database
            rowSet.acceptChanges();

            // Print updated data
            System.out.println("\nUpdated Data:");
            printEmployeeData(rowSet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to print employee data
    private static void printEmployeeData(CachedRowSet rowSet) throws Exception {
        rowSet.beforeFirst(); // Move cursor before the first row
        while (rowSet.next()) {
            int id = rowSet.getInt("id");
            String name = rowSet.getString("name");
            double salary = rowSet.getDouble("salary");
            System.out.println("ID: " + id + ", Name: " + name + ", Salary: " + salary);
        }
    }
}
