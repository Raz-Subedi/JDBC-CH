import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class JdbcRowSetExample {
    public static void main(String[] args) throws Exception {
        RowSetFactory rsf = RowSetProvider.newFactory();
        JdbcRowSet rs = rsf.createJdbcRowSet();
        rs.setUrl("jdbc:mysql://localhost:3306/jdbc_test");
        rs.setUsername("root");
        rs.setPassword("");
        rs.setCommand("select * from employee");
        rs.execute();
        System.out.println("Employee Details In Forward Direction");
        System.out.println("EId\tEName\tESalary");
        System.out.println("--------------------------------------");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("salary"));
        }
        System.out.println("Employee Details In Backward Direction");
        System.out.println("EId\tEName\tESalary");
        System.out.println("--------------------------------------");

        while (rs.previous()) {
            System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("salary"));
        }
        System.out.println("Accessing Randomly ... ");
        rs.absolute(3);
        System.out.println(rs.getRow() + " --- >" + rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("salary"));
        rs.first();
        System.out.println(rs.getRow() + " --- >" + rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("salary"));
        rs.last();
        System.out.println(rs.getRow() + " --- >" + rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("salary"));
        rs.close();
    }
}

