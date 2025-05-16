import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * A class to read information from a file of web server accesses.
 * Currently, the log file is assumed to contain simply
 * date and time information in the format:
 *
 *    year month day hour minute
 * Log entries are sorted into ascending order of date. 
 */
public class StreamingScraper implements Iterator<StreamingEntry>
{
    // The data format in the log file.
    private String format;
    // Where the file's contents are stored in the form
    // of LogEntry objects.
    private ArrayList<StreamingEntry> entries;
    // An iterator over entries.
    private Iterator<StreamingEntry> dataIterator;
    
    /**
     * Create a LogfileReader to supply data from a default file.
     */
    public StreamingScraper()
    {
        this("weblog.txt");
    }
    
    /**
     * Create a LogfileReader that will supply data
     * from a particular log file. 
     * @param filename The file of log data.
     */
    public StreamingScraper(String filename)
    {
        // The format for the data.
        format = "Year Month(1-12) Day Hour Minute";       
        // Where to store the data.
        entries = new ArrayList<>();

    boolean dataRead = false; 
        
        // First try to read as a local file
        try {
            File logFile = new File(filename);
            System.out.println("Attempting to read from: " + logFile.getAbsolutePath());
            
            if(logFile.exists()) {
                Scanner logfileScanner = new Scanner(logFile);
                // Read all data lines.
                while(logfileScanner.hasNextLine()) {
                    String logline = logfileScanner.nextLine().trim();
                    System.out.println("Read line: " + logline);
                    
                    //Formatting 
                    if (!logline.matches("\\d{4} \\d{2} \\d{2} \\d{2} \\d{2}")) {
                        System.err.println("Skipping incorrectly formatted logline: " + logline);
                        continue;
                    }
                    // New entry for collection.
                    StreamingEntry entry = new StreamingEntry(logline);
                    entries.add(entry);
                }
                logfileScanner.close();
                dataRead = true; //Success!
                System.out.println("Successfully read " + entries.size() + " entries from file.");
            } else {
                System.out.println("File not found as local file: " + filename);
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("FileNotFoundException: " + e.getMessage());
        }
        
        // If local file didn't work, try as a resource (classpath)
        if(!dataRead) {
            try {
                // Try to locate the file
                URL fileURL = getClass().getClassLoader().getResource(filename);
                if(fileURL == null) {
                    throw new FileNotFoundException(filename);
                }
                
                System.out.println("Reading from resource: " + fileURL);
                Scanner logfile = new Scanner(new File(fileURL.toURI()));
                
                // Read all data lines.
                while(logfile.hasNextLine()) {
                    String logline = logfile.nextLine().trim();
                    System.out.println("Read line: " + logline);
                    
                    //Formatting again
                    if (!logline.matches("\\d{4} \\d{2} \\d{2} \\d{2} \\d{2}")) {
                        System.err.println("Skipping incorrectly formatted logline: " + logline);
                        continue;
                    }
                    // New collection entry again.
                    StreamingEntry entry = new StreamingEntry(logline);
                    entries.add(entry);
                }
                logfile.close();
                dataRead = true; //Success!
                System.out.println("Successfully read " + entries.size() + " entries from resource.");
            }
            catch(FileNotFoundException | URISyntaxException e) {
                System.out.println("Problem encountered: " + e);
            }
        }
        
        // If still doesn't work, generate simulated data.
        if(!dataRead) {
            System.out.println("Failed to read the data file: " + filename);
            System.out.println("Using simulated data instead.");
            createSimulatedData(entries);
        }
        
        // Sort the entries into ascending order.
        Collections.sort(entries);
        reset();
    }
    
    /**
     * Does the reader have more data to supply?
     * @return true if there is more data available,
     *         false otherwise.
     */
    public boolean hasNext()
    {
        return dataIterator.hasNext();
    }
    
    /**
     * Analyze the next line from the log file and
     * make it available via a LogEntry object.
     * 
     * @return A LogEntry containing the data from the
     *         next log line.
     */
    public StreamingEntry next()
    {
        return dataIterator.next();
    }
    
    /**
     * Remove an entry.
     * This operation is not permitted.
     */
    public void remove()
    {
        System.err.println("It is not permitted to remove entries.");
    }
    
    /**
     * @return A string explaining the format of the data
     *         in the log file.
     */
    public String getFormat()
    {
        return format;
    }
    
    /**
     * Set up a fresh iterator to provide access to the data.
     * This allows a single file of data to be processed
     * more than once.
     */
    public void reset()
    {
        dataIterator = entries.iterator();
    }

    /**
     * Print the data.
     */    
    public void printData()
    {
        for(StreamingEntry entry : entries) {
            System.out.println(entry);
        }
    }

    /**
     * Provide a sample of simulated data.
     * NB: To simplify the creation of this data, no
     * days after the 28th of a month are ever generated.
     * @param data Where to store the simulated LogEntry objects.
     */
    private void createSimulatedData(ArrayList<StreamingEntry> data)
    {
        LogfileCreator creator = new LogfileCreator();
       // How many simulated entries we want.
        int numEntries = 100;
        for(int i = 0; i < numEntries; i++) {
            data.add(creator.createEntry());
        }
    }
}
