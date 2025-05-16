import java.util.Arrays;
/**
 * Read and analyse website
 * hourly access patterns.
 */
public class DataAnalyzer
{
    // Where to store the hourly access counts.
    private int[] hourCounts;
    // Reads and processes entries.
    private StreamingScraper scraper;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public DataAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader for the data.
        scraper = new StreamingScraper(filename);
    }

    /**
     * Analyze the hourly access data from the log file. Handles invalid hour values and such.
     */
    public void analyzeHourlyData()
    {
        Arrays.fill(hourCounts, 0); //Resets hour counters
        int entryCount = 0; //Tracks total number of entries
        while(scraper.hasNext()) {
            StreamingEntry entry = scraper.next(); 
            entryCount++;
            System.out.println("Processing entry: " + entryCount + ": " + entry.toString());
            int hour = entry.getHour();
            System.out.println(" Hour: " + hour);

            if (hour >= 0 && hour < 24) {
                hourCounts[hour]++;
                System.out.println(" Updated count for hour " + hour + ": " + hourCounts[hour]);
            } else {
                System.out.println( " Warning: Hour out of range: " + hour);
            }
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        //Each hour and access count
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        scraper.printData();
    }
}
