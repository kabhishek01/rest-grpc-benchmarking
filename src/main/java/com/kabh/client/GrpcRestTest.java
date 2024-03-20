package com.kabh.client;

import okhttp3.*;

import java.io.IOException;

public class GrpcRestTest {
    public static void main(String[] args) throws IOException {
        testGrpc();
        testRest();
    }

    private static void testGrpc() {
        long startTime = System.currentTimeMillis();

        // Make gRPC call
        // Implement your gRPC client call here

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("gRPC Time: " + duration + "ms");
    }

    private static void testRest() throws IOException {
        long startTime = System.currentTimeMillis();

        // Make REST call
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8081/your-rest-api-endpoint")
                .build();
        try (Response response = client.newCall(request).execute()) {
            // Process REST response
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Do something with response body if needed
            String responseBody = response.body().string();
            System.out.println("REST Response: " + responseBody);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("REST Time: " + duration + "ms");
    }
}
