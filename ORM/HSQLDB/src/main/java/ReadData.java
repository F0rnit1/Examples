import java.sql.*;

public class ReadData {
    public static void main(String[] args) throws Exception {
        try (Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/test", "SA", "");
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery("select count(*) from product")) {

            resultSet.next();
            System.out.println(resultSet.getObject(1));
        }
    }
}
