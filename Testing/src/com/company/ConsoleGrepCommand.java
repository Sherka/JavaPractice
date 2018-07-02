package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleGrepCommand {

    private static String filePath;
    private static String stringToFind;

    private static List<String> loadStringsFromFile (String filePath) {

        List<String> inputStrings = new ArrayList<>();
        filePath = "resources\\" + filePath;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            inputStrings = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStrings;
    }

    private static List<String> findCurrentString (String stringToFind, List<String> loadedStrings) {

        List<String> foundedStrings = new ArrayList<>();

        List<String> arguments = separateString(stringToFind);

        for (String arg: arguments) {
            foundedStrings.add("Looking for " + arg);
            Iterator<String> iterator = loadedStrings.iterator();

            while (iterator.hasNext()) {
                String str = iterator.next();
                if(str.toLowerCase().contains(arg))
                    foundedStrings.add(str);
            }
        }

        return foundedStrings;
    }

    private static List<String> separateString (String stringToFind) {

        List<String> arguments = new ArrayList<>();

        stringToFind = stringToFind.trim();
        stringToFind = stringToFind.toLowerCase();

        if(stringToFind.contains(",")) {
            arguments.addAll(Arrays.stream(stringToFind.split(",")).collect(Collectors.toList()));
        } else {
            arguments.add(stringToFind);
        }

        return arguments;
    }

    private static void writeResultIntoFile (List<String> result) {

        File resultFile = new File("resources\\result.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(resultFile))) {

            for (String resultString : result) {
                bw.write(resultString);
                bw.newLine();
            }

            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Write file name to search in");
            filePath = br.readLine();
            System.out.println("Write string to find");
            stringToFind = br.readLine();

            writeResultIntoFile(findCurrentString(stringToFind, loadStringsFromFile(filePath)));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
