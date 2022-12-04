package pl.mz.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileProcessor {

    public static List<String> readFile(String filePath) {
        List<String> output = new LinkedList<>();
        String currentLine;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new java.io.FileReader(filePath));

            try {
                while ((currentLine = bufferedReader.readLine()) != null) {
                    output.add(currentLine);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return output;
    }


}


