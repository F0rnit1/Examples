import java.sql.*;

public class StopServer {
    public static void main(String[] args) throws Exception {
        try (Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/test", "SA", "");
             Statement statement = con.createStatement()) {
            boolean execute = statement.execute("shutdown;");
            System.out.println(execute);
        }
    }
}
