import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException, InterruptedException {
        args = "java -jar build\\libs\\JMH-1.0-jmh.jar -bm ss -f 1 -bs 10000 -i 2 -tu ms -wi 2 -v extra".split("\\s");

        ProcessBuilder pb = new ProcessBuilder(args);
        pb.inheritIO();
        Process process = pb.start();

        process.waitFor();
    }
}
