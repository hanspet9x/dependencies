package io;

import java.io.DataInputStream;
import java.io.IOException;

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

    public static void delete(String ...commandLine) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String s : commandLine) {
            sb.append(s);
            sb.append(" ");
        }
        String data = "cmd /c del /f /q "+ sb.toString();
        execute(data);
    }

    public static void deleteFolder(String path) throws IOException {
        String data = "cmd /c rmdir /q /s "+path;
        execute(data);
    }

    public static void deleteFile(String filePath) throws IOException {
        execute("cmd /c del /f /q "+filePath);
    }
}
