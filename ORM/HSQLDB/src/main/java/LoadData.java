import java.nio.file.*;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class LoadData {
    public static void main(String[] args) throws Exception {
        try (Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/test", "SA", "");
             Statement statement = con.createStatement()) {
            Path sampledataPath = Paths.get("C:\\hsqldb\\sample\\sampledata.sql");
            List<String> sampledata = Files.lines(sampledataPath).map(String::trim)
                                           .filter(e -> !e.isEmpty() && !e.startsWith("/") && !e.startsWith("*"))
                                           .collect(Collectors.toList());
            sampledata.forEach(e -> {
                try {
                    System.out.println(e);
                    statement.execute(e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }
}