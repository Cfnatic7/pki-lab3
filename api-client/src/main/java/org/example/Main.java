package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Main {

    public static String apiBasePath = "http://localhost:8080/api";

    public static boolean clientRun = true;
    public static void main(String[] args) {
        BufferedReader inp = new BufferedReader (new InputStreamReader(System.in));

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            while(clientRun) {
                System.out.println("provide method or press 'q'");
                String method = inp.readLine();
                if (method.equalsIgnoreCase("get")) {
                    handleGet(inp, httpClient);
                }
                else if (method.equalsIgnoreCase("put")) {
                    handlePut(inp, httpClient);
                }
                else if (method.equalsIgnoreCase("post")) {
                    handlePost(inp);
                }
                else if (method.equalsIgnoreCase("q")) {
                    clientRun = false;
                }
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleGet(BufferedReader inp, CloseableHttpClient httpClient) throws IOException {
        String path = getFullRelativePath(inp);
        HttpGet request = new HttpGet(apiBasePath + path);
        readResponse(httpClient, request);
    }

    private static void handlePut(BufferedReader inp, CloseableHttpClient httpClient) throws IOException {
        String path = getFullRelativePath(inp);
        HttpPut request = new HttpPut(apiBasePath + path);
        readResponse(httpClient, request);
    }

    private static void handlePost(BufferedReader inp) throws IOException {
        String path = getFullRelativePath(inp);
        System.out.println("Provide json body");
        String json = inp.readLine();
        StringEntity entity = new StringEntity(json);
        HttpPost httpPost = new HttpPost(path);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Content-length", Integer.toString(json.length()));
    }

    private static void readResponse(CloseableHttpClient httpClient, HttpRequestBase request) throws IOException {
        HttpResponse response = httpClient.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
    }

    private static String getFullRelativePath(BufferedReader inp) throws IOException {
        System.out.println("provide relative path");
        String path = inp.readLine();
        System.out.println("provide query");
        path += inp.readLine();
        return path;
    }
}