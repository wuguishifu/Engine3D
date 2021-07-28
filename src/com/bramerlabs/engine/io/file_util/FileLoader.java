package com.bramerlabs.engine.io.file_util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileLoader {

    public static String load(String path) {
        StringBuilder file = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(FileLoader.class.getModule().getResourceAsStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                file.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not load file at " + path);
        }
        return file.toString();
    }

}
