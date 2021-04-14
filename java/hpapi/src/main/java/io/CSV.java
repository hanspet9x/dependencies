package io;

import utils.HPArrays;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@SuppressWarnings("rawtypes")
public class CSV<T> implements CSVDownloadImpl {


    private static StringBuilder builder;
    private static Path filePath;
    private static CSV csv = null;
    private static List<String> headers;
    private static Map<Integer, String> headersValues;

    public static CSV create(Path downloadPath){

        builder = new StringBuilder();
        filePath = downloadPath;
        headers = new ArrayList<>();
        headersValues = new HashMap<>();
        return getInstance();
    }


    @Override
    public void addHeaders(String... headers) {
        CSV.headers.addAll(Arrays.asList(headers));
        addRows(HPArrays.toString(headers));
    }

    @Override
    public void addHeader(String header) {
        if(!headers.contains(header))headers.add(header);
    }

    @Override
    public void addRow(String row) {
        builder.append(row);
        nextLine();
    }

    @Override
    public void addRows(String... rows) {

        for (String row:
             rows) {
            addRow(row);
        }
    }

    @Override
    public void addColumn(String column) {
        builder.append(column);
        builder.append(separate());
    }

    @Override
    public void addColumns(String... columns) {
        for (String column:
                columns) {
            addColumn(column);
        }
    }

    @Override
    public void addHeaderValue(String header, String value) {
        if(headers.size() > 0 && headers.contains(header)){
            int index = headers.indexOf(header);
            headersValues.put(index, value);

        }else{
            throw new IndexOutOfBoundsException("Specified header not found.");
        }
    }

    @Override
    public void addHeadersValues(Map<String, String> headersValues) {

    }

    private void processHeadersValues(){
        if(headersValues.size() > 0){
            for (int i = 0; i < headers.size(); i++) {
                addColumn(headersValues.getOrDefault(i, ""));
            }
            headersValues = new HashMap<>();
        }
    }
    @Override
    public void nextLine() {

        processHeadersValues();

        char c = builder.charAt(builder.length()-1);
        if(Character.toString(c).equals(",")){
            builder.deleteCharAt(builder.length()-1);
        }

        builder.append("\n");
    }

    @Override
    public String separate() {
        return " ,";
    }

    @Override
    public void download() throws IOException {
        if(!filePath.toFile().exists()){
            Files.createFile(filePath);
        }
        FileWriter writer = new FileWriter(filePath.toFile());
        writer.write(builder.toString());
        writer.flush();
        writer.close();
    }

    private static CSV getInstance(){
        if(csv == null){
            csv = new CSV();
        }
        return csv;
    }



    /*UPLOAD*/

    public void open(File csvFile, T t){


    }
}
