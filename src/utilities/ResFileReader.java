package utilities;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ResFileReader {


    public void readFile() {
        try{
            try (Scanner inputStream = new Scanner(new FileInputStream("input.txt"))) {
                inputStream.useDelimiter(",");

                while(inputStream.hasNextLine()){


                    String pass = inputStream.next();
                    ArrayList<String> upasses = new ArrayList<>();
                    upasses.add(pass);


                    String memail = inputStream.next();
                    ArrayList<String> memails = new ArrayList<>();
                    memails.add(memail);

                    System.out.println(pass);
                    System.out.println(memail);

                    inputStream.nextLine();

                    }
                }

        }

        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
