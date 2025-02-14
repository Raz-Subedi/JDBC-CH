import java.sql.*;

public class CallableStatementExample {
    public static void main(String[] args) {
        try {
           // Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jdbc_test", "root", "");

            // Create CallableStatement
            CallableStatement cs = con.prepareCall("{call insertPerson(?, ?)}");

            cs.setString(1, "Messi");
            cs.setInt(2, 10);

            // Execute the stored procedure
            cs.execute();
            System.out.println("Person inserted successfully!");

            // Create another CallableStatement to retrieve data
            CallableStatement csSelect = con.prepareCall("{call getAllPeople()}");

            // Execute and get results
            ResultSet rs = csSelect.executeQuery();

            // Process results
            System.out.println("\nPeople in database:");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("name") +
                        ", Age: " + rs.getInt("age"));
            }

            // Close resources
            rs.close();
            cs.close();
            csSelect.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
