import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyFileReader{
    public static ArrayList<String>  readfile(String path) throws FileNotFoundException, IOException{
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
    /*public static void main(String[] args) throws FileNotFoundException, IOException {
        String filePath = "in.txt";
        ArrayList<String> testArray =readfile(filePath);
        System.out.println(testArray);
    }*/
}
