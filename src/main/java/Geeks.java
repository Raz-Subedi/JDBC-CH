// Java Program illustrating
// Callable Statement in JDBC
import java.sql.*;

public class Geeks {

    public static void main(String[] args) {

        // Try block to check if any exceptions occur
        try {

            // Load and register the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            Connection con = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/jdbc_test", "root", "");

            // Create a CallableStatement
            CallableStatement cs =
                    con.prepareCall("{call GetPeopleInfo()}");

            // Execute the stored procedure
            ResultSet res = cs.executeQuery();

            // Process the results
            while (res.next()) {

                // Print and display elements (Name and Age)
                System.out.println("Name : " + res.getString("name"));
                System.out.println("Age : " + res.getInt("age"));
            }

            // Close resources
            res.close();
            cs.close();
            con.close();
        }
        // Catch block for SQL exceptions
        catch (SQLException e) {
            e.printStackTrace();
        }
        // Catch block for ClassNotFoundException
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
