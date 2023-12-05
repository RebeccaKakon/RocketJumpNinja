package automation.pages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
//import org.example.modules.Joke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RestClient {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final Map<String, String> headers;


    public RestClient() {
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClientBuilder.create().build();
        this.headers = new HashMap<>();

    }

    public int get(String apiUrl) throws IOException {
        HttpGet request = new HttpGet(apiUrl);
        addHeadersToRequest(request);

        // Execute the GET request
        HttpResponse response = httpClient.execute(request);

        // Check the status code
        return response.getStatusLine().getStatusCode();
//        if (statusCode == 200) {
//            // Read and return the response content
//            return processResponse(response);
//        } else {
//            throw new IOException("Unexpected status code: " + statusCode);
//        }
    }

    public void addHeadersToRequest(HttpRequestBase request) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }
    }
}
