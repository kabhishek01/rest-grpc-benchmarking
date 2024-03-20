package com.kabh.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class HelloGrpcServiceImpl {


    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new HelloServiceImpl())
                .build();

        server.start();
        System.out.println("Server started");

        server.awaitTermination();
    }


    static class HelloServiceImpl extends com.kabh.grpc.HelloServiceGrpc.HelloServiceImplBase {
        @Override
        public void unaryHello(com.kabh.grpc.HelloRequest request, StreamObserver<com.kabh.grpc.HelloResponse> responseObserver) {
            String name = request.getMessage();
            String greeting = "Hello, " + name + "!";
            com.kabh.grpc.HelloResponse response = com.kabh.grpc.HelloResponse.newBuilder()
                    .setMessage(greeting)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }


}


