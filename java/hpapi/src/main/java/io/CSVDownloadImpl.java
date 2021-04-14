package io;

import java.io.IOException;
import java.util.Map;

public interface CSVDownloadImpl {

    void addHeaders(String ...headers);

    void addHeader(String header);

    void addRow(String row);

    void addRows(String ...rows);

    void addColumn(String column);

    void addColumns(String ...columns);

    void addHeaderValue(String header, String value);

    void addHeadersValues(Map<String, String> headersValues);

    void nextLine();

    String separate();

    void download() throws IOException;
}
