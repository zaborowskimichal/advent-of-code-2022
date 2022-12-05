package pl.mz.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileProcessor {

    public static List<String> readFileToList(String filePath) {
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

    public static String readFileToString(String filePath) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(Paths.get(filePath), StandardCharsets.UTF_8);
            return scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (scanner != null)
                scanner.close();
        }
    }

    public static ArrayList<String> splitLines(String text, String split) {
        return (ArrayList<String>) Arrays.stream(text.split(split)).collect(Collectors.toList());
    }


}


