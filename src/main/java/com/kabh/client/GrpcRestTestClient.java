package com.kabh.client;

import com.kabh.grpc.HelloRequest;
import com.kabh.grpc.HelloResponse;
import com.kabh.grpc.HelloServiceGrpc;
import com.kabh.utils.FileUtils;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import okhttp3.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class GrpcRestTestClient {
    public static void main(String[] args) throws IOException {
        InputStream fileStream = FileUtils.getFileFromResourceAsStream("size_small2.json");
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (fileStream, StandardCharsets.UTF_8))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8088)
                .usePlaintext()
                .build();

        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);
        System.out.println("Running with json payload of length :" +textBuilder.toString().length());
        for (int i =0 ;i <=100; i++) {
            testGrpc(stub, textBuilder.toString());
            testRest(textBuilder.toString());
        }

        channel.shutdown();
    }

    private static void testGrpc(HelloServiceGrpc.HelloServiceBlockingStub stub, String message) {

        HelloRequest request = HelloRequest.newBuilder()
                .setMessage(message)
                .build();
        long startTime = System.currentTimeMillis();
        HelloResponse response = stub.unaryHello(request);

        //System.out.println(response.getMessage());


        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.print("gRPC Time: " + duration + "ms");
    }

    private static void testRest(String message) throws IOException {
        // Make REST call
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), message);
        Request request = new Request.Builder()
                .url("http://localhost:8080/hello").post(body)
                .build();
        long startTime = System.currentTimeMillis();
        try (Response response = client.newCall(request).execute()) {
            // Process REST response
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Do something with response body if needed
            String responseBody = response.body().string();
            //System.out.println("REST Response: " + responseBody);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println(" REST Time: " + duration + "ms");
    }



}
