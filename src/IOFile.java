import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class IOFile{
    public static ArrayList<String>  readFile(String path) throws FileNotFoundException, IOException{
        ArrayList<String> outputList=new ArrayList<String>();
        File file = new File(path);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("File not found: " + path);
        }
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                outputList.add(line);
            }}
            return outputList;
    }
    public static void writeFile(String in ,String outPath) throws IOException{
        File file = new File(outPath);

        // Use try-with-resources to automatically close resources (such as FileWriter and BufferedWriter)
            try ( FileWriter fileWriter = new FileWriter(file,true);
              BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                bufferedWriter.write(in);
                bufferedWriter.newLine();  // Add a newline for the next line
            }
            
        }
}
