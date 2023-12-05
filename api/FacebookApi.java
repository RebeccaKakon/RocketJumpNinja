package automation.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FacebookApi {

    private static final String BASE_URL = "https://graph.facebook.com/v12.0"; // Replace with the actual API base URL

    // You may need to obtain an access token for Facebook API authentication
    private static final String ACCESS_TOKEN = "your_access_token";

    public static void login(String username, String password) {
        try {
            // Define the login endpoint URL
            URL loginEndpoint = new URL(BASE_URL + "/login");

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) loginEndpoint.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Create request parameters
            String requestBody = "username=" + username + "&password=" + password + "&access_token=" + ACCESS_TOKEN;

            // Write the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Handle the API response as needed
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Login successful!");
                // Handle the response data if required
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                        // Handle the response data
                    }
                }
            } else {
                System.err.println("Login failed. Response code: " + responseCode);
                // Handle the failure
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createUser(String username) {
        try {
            // Define the user creation endpoint URL
            URL createUserEndpoint = new URL(BASE_URL + "/users");

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) createUserEndpoint.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Create request parameters
            String requestBody = "username=" + username + "&access_token=" + ACCESS_TOKEN;

            // Write the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Handle the API response as needed
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("User creation successful!");
                // Handle the response data if required
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                        // Handle the response data
                    }
                }
            } else {
                System.err.println("User creation failed. Response code: " + responseCode);
                // Handle the failure
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
