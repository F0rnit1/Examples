import org.hsqldb.Server;

public class RunServer {
    public static void main(@SuppressWarnings("ALL") String[] args) {
        args = new String[]{"--database.0", "file:HSQLDB/testdb/test", "--dbname.0", "test"};
        Server.main(args);
    }
}
