import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertQueryExample {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/jdbc_test", "root", "");

            // Using PreparedStatement for insert
            String sql = "INSERT INTO employee (name, salary) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, "Ram Bahadur");
            pstmt.setDouble(2, 50000.00);

//            pstmt.setString(1,"Hari");
//            pstmt.setDouble(2,50470.00);

//            pstmt.setString(1,"Shyam");
//            pstmt.setDouble(2,78452.00);

//            pstmt.setString(1,"Raz");
//            pstmt.setDouble(2,20000.00);

//            pstmt.setString(1,"kumar");
//            pstmt.setDouble(2,62300.00);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted");

            pstmt.close();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

