import java.util.Scanner;
import java.time.*;

/**
 * Break up line from a web server log file into
 * its separate fields.
 * Currently, the log file is assumed to contain simply
 * integer date and time information.
 * 
 * @author David J. Barnes and Michael Kolling.
 * @version 2016.02.29
 */
public class LoglineTokenizer
{
    /**
     * Construct a LogLineAnalyzer
     */
    public LoglineTokenizer()
    {
    }

    /**
     * Tokenize a log line. Place the integer values from
     * it into an array. The number of tokens on the line
     * must be sufficient to fill the array.
     *
     * @param logline The line to be tokenized.
     * @param dataLine Where to store the values.
     */
    public void tokenize(String logline, int[] dataLine)
    {
        try {
            // Scan the logline for integers.
            Scanner tokenizer = new Scanner(logline);
            for(int i = 0; i < dataLine.length; i++) {
                if (!tokenizer.hasNextInt()) {
                    System.out.println("Error! Missing expected: " + i + " in: " + logline);
                    return;
                }
                dataLine[i] = tokenizer.nextInt();
            }
            //Confirms parsing
            LocalDateTime timestamp = LocalDateTime.of(dataLine[0], dataLine[1], dataLine[2], dataLine[3], dataLine[4]);
            System.out.println("Parsed timestamp: " + timestamp);
        }
        catch(java.util.NoSuchElementException e) {
            System.out.println("Insuffient data items on log line: " + logline);
            throw e;
        }
    }
}
