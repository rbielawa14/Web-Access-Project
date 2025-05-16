import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL; 
import java.time.LocalDateTime;
import java.net.*;
import java.util.*;
import java.io.*;
import java.time.*;
import java.util.concurrent.TimeUnit;

/**
 * Simulates accessing a website at regular intervals with GET request over time, logs their timestamps into the weblog file
 */
public class WebsiteAccessLogger {
    /**
     * Simulates the website access and logs the timestamps (over duration)
     * @param websiteURL website to access
     * @param logFilename logs access timestamps
     * @param totalRequests
     * @param duration for requests
     */
    public void logAccesses(String websiteURL, String logFilename, int totalRequests, int duration) { //Duration is in minutes
        long totalDurationMS = TimeUnit.MINUTES.toMillis(duration); //Minutes to milliseconds
        long startTime = System.nanoTime(); 

        try (FileWriter writer = new FileWriter(logFilename)) {
            for (int i = 0; i < totalRequests; i++) {
                accessWebsite(websiteURL);
                logCurrentTimestamp(writer); //Logs current timestamp
                if (i < totalRequests - 1) {
                    long expectedNextTime = startTime + (i + 1) * totalDurationMS /totalRequests;
                    while (System.nanoTime() < expectedNextTime) {
                        Thread.sleep(10);
                    }
                }
            }
            System.out.println("Log file created: " + logFilename);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error during website access or logging: " + e.getMessage());
        }
    }
    /**
     * Sends the GET request, simulating real website access. 
     * @param urlString the link to access. 
     */
    private void accessWebsite(String urlString) {
        try {
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); //Sends a GET request to the specified website to simulate access
            int status = conn.getResponseCode();
            conn.getInputStream().close();
            System.out.println("Accessed " + urlString + " - Status: " + status);
        } catch (URISyntaxException e) {
            System.err.println("Invalid URL format: " + e.getMessage());
        }  catch (IOException e) {
            System.err.println("Failed to access " + urlString + ": " + e.getMessage());
        }
    }
    /**
     * Logs timestamp to file. 
     * @param writer used to write to log
     * @throws IOException
     */
    private void logCurrentTimestamp(FileWriter writer) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        writer.write(String.format("%04d %02d %02d %02d %02d%n", 
        now.getYear(), now.getMonthValue(), now.getDayOfMonth(),
        now.getHour(), now.getMinute())); 
    }    
}
