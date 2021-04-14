package io;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Terminal {

    public static String getExecuted(String commandLine) throws IOException {

        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(commandLine);
        DataInputStream stream = new DataInputStream(process.getInputStream());
        String data = new String(stream.readAllBytes());
        stream.close();
        process.destroy();
        return data;

    }

    public static void execute(String commandLine) throws IOException {
        Runtime.getRuntime().exec(commandLine);
    }

    public static String ping(String hostName) throws IOException {
        return getExecuted("ping "+ hostName);
    }

    public static void deleteFile(Path filePath) throws IOException {
        execute("del /f "+filePath.toString());
    }
}
