package com.testvagrant.booknamechallenge.findthatbook.utils;


import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FileHelper {

    public void readFile(String fileLocation) {
        try {
            FileReader fileReader = new FileReader(fileLocation);
            while (fileReader.read() != -1) {
                System.out.println((char) fileReader.read());
            }
            fileReader.close();
        }catch (IOException exception) {

        }
    }

    public void writeFile(String fileLocation, String content) {
        try {
            FileWriter fileWriter = new FileWriter(fileLocation);
            fileWriter.flush();
            fileWriter.write(content);
            fileWriter.close();

        }catch (IOException exception) {

        }

    }
}
