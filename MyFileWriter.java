import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MyFileWriter {
    public static void writeFile(String in ,String outPath) throws IOException{
        File file = new File(outPath);

        // Use try-with-resources to automatically close resources (such as FileWriter and BufferedWriter)
        try (FileWriter fileWriter = new FileWriter(file,true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            
            bufferedWriter.write(in);
            bufferedWriter.newLine();  // Add a newline for the next line

        }catch (IOException e) {
            // Handle IO exceptions
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        String inPath = "in.txt";
        ArrayList<String> testArray =MyFileReader.readfile(inPath);
        System.out.println(testArray);
        // Specify the path to your file
        String outPath = "out.txt";
        for(int i=0;i<testArray.size();i++){
            writeFile(testArray.get(i),outPath);
        }
    }
}

