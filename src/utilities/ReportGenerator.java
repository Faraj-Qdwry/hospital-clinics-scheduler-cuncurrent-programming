package utilities;

import java.io.*;

public class ReportGenerator {

    public static synchronized void addToReport(String text) throws IOException {

        try
        {
            String filename= "Report.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(text);//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }

    }

    public static synchronized void startReport() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("Report.txt");
        writer.print("");
        writer.close();
    }
}
