package hp.net;

import com.google.gson.Gson;
import hp.io.Console;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@SuppressWarnings("rawtypes")
public class HttpFactory {

    //TODO safely delete GET repetition....

    private static Path RESPONSE_PATH;

    public static HttpResponses Response = HttpResponses.TEXT;

    private static Class typeClass;

    private static Consumer callback = null;

    public static void postForm(String url, Iterable<byte[]> data, HttpResponses response) {

        Response = response;
        HttpClient client = getClient();
        HttpRequest req = HttpRequest.newBuilder()
                .header("Content-Type", "multipart/form-data;boundary=hanspethttpbound")
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofByteArrays(data))
                .build();
        setResponseType(client, req);
    }

    public static void postBytes(String url, Iterable<byte[]> data) {

        HttpClient client = getClient();
        HttpRequest req = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofByteArrays(data))
                .build();
        setResponseType(client, req);
    }

    public static void delete(String url) {

        HttpClient client = getClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();
        setResponseType(client, req);
    }

    public static void delete(String url, String data) {

        HttpClient client = getClient();
        HttpRequest req = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .method("DELETE", HttpRequest.BodyPublishers.ofString(data))
                .build();
        setResponseType(client, req);
    }


    public static void postObject(String url, Object bean) {
        postSetUp(url, new JSONObject(bean).toString());
    }

    public static void postObject(String url, JSONObject obj) {
        postSetUp(url, obj.toString());
    }

    public static void postObject(String url, JSONArray obj) {
        postSetUp(url, obj.toString());
    }

    public static <T> void postObject(String url, Object obj, Class<T> tClass, Consumer<T> callback) {
        typeClass = tClass;
        Response = HttpResponses.CLASS;
        HttpFactory.callback = callback;
        postSetUp(url, new Gson().toJson(obj));
    }

    public static void postJSON(String url, String json) {
        postSetUp(url, json);
    }

    public static <T> void postJSON(String url, String json, Class<T> tClass) {
        typeClass = tClass;
        postSetUp(url, json);
    }

    public static void postObject(String url, Object bean, Header header) {
        postSetUp2(url, new JSONObject(bean).toString(), header);
    }

    public static void postObject(String url, JSONObject obj, Header header) {
        postSetUp2(url, obj.toString(), header);
    }

    public static void postObject(String url, JSONArray obj, Header header) {
        postSetUp2(url, obj.toString(), header);
    }

    public static void postJSON(String url, String json, Header header) {
        postSetUp2(url, json, header);
    }


    /*
    PUT
     */
    public static void putObject(String url, Object bean) {
        putSetUp(url, new JSONObject(bean).toString());
    }

    public static void putObject(String url, JSONObject obj) {
        putSetUp(url, obj.toString());
    }

    public static void putObject(String url, JSONArray obj) {
        putSetUp(url, obj.toString());
    }

    public static void putJSON(String url, String json) {
        putSetUp(url, json);
    }

    public static void putObject(String url, Object bean, Header header) {
        putSetUp2(url, new JSONObject(bean).toString(), header);
    }

    public static void putObject(String url, JSONObject obj, Header header) {
        putSetUp2(url, obj.toString(), header);
    }

    public static void putObject(String url, JSONArray obj, Header header) {
        putSetUp2(url, obj.toString(), header);
    }

    public static void putJSON(String url, String json, Header header) {
        putSetUp2(url, json, header);
    }

    /*
    GET
     */
    public static void getJSON(String url) {
        Response = HttpResponses.JSON;
        getSetUp(url);
    }

    public static void getText(String url) {
        Response = HttpResponses.TEXT;
        getSetUp(url);
    }

    public static void getArray(String url) {
        Response = HttpResponses.ARRAY;
        getSetUp(url);
    }

    public static void getStream(String url) {
        Response = HttpResponses.STREAM;
        getSetUp(url);
    }

    public static void getStream(String url, Header header) {
        Response = HttpResponses.STREAM;
        getSetUp(url, header);
    }

    public static void getJSON(String url, Header header) {
        Response = HttpResponses.JSON;
        getSetUp(url, header);
    }

    public static void getText(String url, Header header) {
        Response = HttpResponses.TEXT;
        getSetUp(url, header);
    }

    public static void getArray(String url, Header header) {
        Response = HttpResponses.ARRAY;
        getSetUp(url, header);
    }

    public static void getPath(String url, Path downloadPath) {
        Response = HttpResponses.PATH;
        RESPONSE_PATH = downloadPath;
        getSetUp(url);
    }

    public static void getPath(String url, Header header, Path downloadPath) {
        Response = HttpResponses.PATH;
        RESPONSE_PATH = downloadPath;
        getSetUp(url, header);
    }

    public static void getBytes(String url) {
        Response = HttpResponses.BYTE_ARRAY;
        getSetUp(url, new Header("Content-Type", "application/octet-stream"));
    }

    public static void getBytes(String url, Header header) {
        Response = HttpResponses.BYTE_ARRAY;
        getSetUp(url, header);
    }


    private static HttpRequest getRequest(String url, String data) {
        return HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();
    }

    private static HttpRequest getRequest2(String url, String data, Header header) {
        return HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header(header.key, header.value)
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();
    }

    private static HttpRequest getPutRequest(String url, String data) {
        return HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(data))
                .build();
    }

    private static HttpRequest getPutRequest2(String url, String data, Header header) {
        return HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header(header.key, header.value)
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(data))
                .build();
    }

    private static HttpClient getClient() {
        ExecutorService e = Executors.newCachedThreadPool();
        return HttpClient.newBuilder().
                version(HttpClient.Version.HTTP_1_1)
                .executor(e)
                .build();
    }

    private static void postSetUp(String url, String data) {
        HttpRequest request = getRequest(url, data);
        putPostSetUp(request);
    }

    private static void postSetUp2(String url, String data, Header header) {
        HttpRequest request = getRequest2(url, data, header);
        putPostSetUp(request);
    }

    private static void putSetUp(String url, String data) {
        HttpRequest request = getPutRequest(url, data);
        putPostSetUp(request);
    }

    private static void putSetUp2(String url, String data, Header header) {
        HttpRequest request = getPutRequest2(url, data, header);
        putPostSetUp(request);
    }

    private static void putPostSetUp(HttpRequest request) {
        HttpClient client = getClient();
        setResponseType(client, request);
    }


    private static void getSetUp(String url) {
        HttpClient client = getClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        setResponseType(client, request);

    }

    private static void getSetUp(String url, Header header) {
        HttpClient client = getClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header(header.key, header.value)
                .build();
        setResponseType(client, request);

    }

    /*
    Response type settings
     */
    private static void setResponseType(HttpClient client, HttpRequest request) {

        switch (Response) {
            case JSON, TEXT, ARRAY, CLASS -> sendGetString(client, request);
            case STREAM -> sendGetStream(client, request);
            case PATH -> sendGetPath(client, request);
            case BYTE_ARRAY -> sendGetByteArray(client, request);
        }
    }

    private static void sendGetString(HttpClient client, HttpRequest request) {
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept((d) -> {

                    try {
                        switch (Response) {
                            case JSON -> onServerResponse.response(new JSONObject(d));
                            case TEXT -> onServerResponse.response(d);
                            case ARRAY -> onServerResponse.response(new JSONArray(d));
                            case CLASS -> {
                                if (callback != null) {
                                    Console.log("response", d);
                                    callback.accept(new Gson().fromJson(d, typeClass));

                                } else {
                                    onServerResponse.response(new Gson().fromJson(d, typeClass));
                                }
                            }
                        }
                    } catch (Exception exception) {
                        onServerResponse.error(d);
                    }
                });
    }


    private static void sendGetStream(HttpClient client, HttpRequest request) {

        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                    .thenApply(HttpResponse::body)
                    .thenAccept(inputStream -> onServerResponse.response(inputStream));
            Response = HttpResponses.TEXT;
        } catch (Exception e) {
            onServerResponse.error(e.getMessage());
        }
    }

    private static void sendGetByteArray(HttpClient client, HttpRequest request) {

        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                    .thenApply(HttpResponse::body)
                    .thenAccept(bytearray -> onServerResponse.response(bytearray));
            Response = HttpResponses.TEXT;
        } catch (Exception e) {
            onServerResponse.error(e.getMessage());
        }
    }

    private static void sendGetPath(HttpClient client, HttpRequest request) {
        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofFile(RESPONSE_PATH))
                    .thenApply(HttpResponse::body)
                    .thenAccept(res -> onServerResponse.response(res));
            Response = HttpResponses.TEXT;
        } catch (Exception e) {
            onServerResponse.error(e.getMessage());
        }

    }

    public static boolean isInternetConnectionAvailable() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("ping www.google.com");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String read = null;
        while ((read = (reader.readLine())) != null) {
            if (read.contains("Received = 4")) return true;
        }
        return false;
    }

    public static boolean isInternetConnectionAvailable(String url) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        url = url.substring(url.indexOf("www"));
        Process process = runtime.exec("ping " + url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String read = null;
        while ((read = (reader.readLine())) != null) {
            if (read.contains("Received = 4")) return true;
        }
        return false;
    }

    public abstract static class OnServerResponse<T> {

        public void response(String data) {
        }

        ;

        public void response(JSONObject data) throws IOException {
        }

        ;

        public void response(JSONArray data) {
        }

        ;

        public void response(Path data) {
        }

        ;

        public void response(InputStream data) {
        }

        ;

        public void response(byte[] data) {
        }

        ;

        public void response(T data) {
        }

        ;

        public void error(String error) {
        }

        ;
    }

    private static OnServerResponse onServerResponse;

    public static void setOnServerResponse(OnServerResponse onServerResponse) {
        HttpFactory.onServerResponse = onServerResponse;
    }

    public static void setOnServerResponse(OnServerResponse onServerResponse, HttpResponses responseType) {
        HttpFactory.onServerResponse = onServerResponse;
        Response = responseType;
    }

    public static class Header {
        private String key;
        private String value;

        public Header(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public Header() {
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum HttpResponses {
        JSON, TEXT, ARRAY, PATH, STREAM, BYTE_ARRAY, CLASS
    }

    public static class Form {

        private final String bound = "--";
        private final String lineFeed = "\r\n";
        private final String boundary = "hanspethttpbound";

        private final ArrayList<byte[]> form;

        public Form() {
            form = new ArrayList<>();
            openPart();
        }

        public Form add(String name, Object value) {
            continuePart(name, value);
            return this;
        }

        public Form add(String name, JSONObject value) {
            continuePart(name, value.toString());
            return this;
        }

        public Form add(String name, JSONArray value) {
            continuePart(name, value.toString());
            return this;
        }

        public Form addFile(String name, String path, String fileName) throws IOException {
            continuePart(name, getBuffer(path).array(), fileName);
            return this;
        }

        public Form addFile(String name, String path) throws IOException {

            continuePart(name, getBuffer(path).array(), Paths.get(path).getFileName().toString());
            return this;
        }

        public Form addFile(String name, Path path) throws IOException {

            continuePart(name, getBuffer(path.toString()).array(), path.getFileName().toString());
            return this;
        }

        private ByteBuffer getBuffer(String path) throws IOException {
            FileInputStream fis = new FileInputStream(path);
            FileChannel fc = fis.getChannel();
            ByteBuffer buf = ByteBuffer.allocate((int) fc.size());
            fc.read(buf);
            return buf;
        }

        public Iterable<byte[]> build() {
            closePart();
            return form;
        }

        private void openPart() {

            form.add((lineFeed + lineFeed).getBytes());
        }


        private void continuePart(String name, Object value) {

            String sb = bound +
                    boundary +
                    lineFeed +
                    "Content-Disposition: form-data; name=\"" + name + "\"" +
                    lineFeed +
                    lineFeed +
                    value + lineFeed;

            form.add(sb.getBytes());
        }

        private void closePart() {
            String s = bound +
                    boundary +
                    bound;
            form.add(s.getBytes());
        }

        private void continuePart(String name, byte[] value, String fileName) {

            String sb = bound +
                    boundary +
                    lineFeed +
                    "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"" +
                    lineFeed +
                    lineFeed;

            form.add(sb.getBytes());
            form.add(value);
            form.add(lineFeed.getBytes());
        }

    }

    public static class UrlBuilder {

        private final StringBuilder url;

        public UrlBuilder() {
            this.url = new StringBuilder();
        }

        public UrlBuilder(String url) {
            this.url = new StringBuilder(url);
            this.url.append("?");
        }

        public UrlBuilder append(String key, String value) {
            url.append(key);
            url.append("=");
            url.append(value);
            url.append("&");
            return this;
        }

        public UrlBuilder append(String key, int value) {
            return append(key, String.valueOf(value));
        }

        public String build() {
            return url.deleteCharAt(url.length() - 1).toString();
        }
    }

}
