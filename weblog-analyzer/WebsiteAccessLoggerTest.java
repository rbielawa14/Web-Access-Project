/**
 * Test class to run the code:
 * Simulate timed website access, log timestamps, display file info, passes log to dataAnalyzer for hr: count
 */
public class WebsiteAccessLoggerTest {
    public static void main(String[] args) {
        String website = "https://open.spotify.com";
        String logFile = "weblog.txt";
        int requestCount = 20; //accesses the website 20 times
        int duration = 10; //spreads the accesses by 10 minutes

        //Website access and timestamps
        WebsiteAccessLogger logger = new WebsiteAccessLogger();
        logger.logAccesses(website, logFile, requestCount, duration);

        //File object to make sure the logfile exists and is correct 
        java.io.File file = new java.io.File(logFile);
        System.out.println("\nLog file path: " + file.getAbsolutePath());
        System.out.println("Log file exists: " + file.exists());
        System.out.println("Log file size: " + file.length() + "bytes");

        //Contents of logfile (prints)
        try {
            System.out.println("\nLog file contents:");
            java.util.Scanner scanner = new java.util.Scanner(file);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        //Analyzes log data from the generated logFile (accesses per hour and terminal display)
        DataAnalyzer analyzer = new DataAnalyzer(logFile);
        analyzer.analyzeHourlyData();
        analyzer.printHourlyCounts();

    } 
}
