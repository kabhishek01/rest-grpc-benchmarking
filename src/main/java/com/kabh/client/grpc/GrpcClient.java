package com.kabh.client.grpc;

import com.kabh.server.grpc.WeatherServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {


    private final String HOST_NAME = "localhost";
    private final int PORT = 8088;

    private ManagedChannel channel;

    private WeatherServiceGrpc.WeatherServiceBlockingStub blockingStub;

    private WeatherServiceGrpc.WeatherServiceFutureStub futureStub;



    public GrpcClient() {
        createChannel();
        createNonBlockingStub();
        createBlockingStub();
    }

    private void createBlockingStub() {
        blockingStub = WeatherServiceGrpc.newBlockingStub(channel);
    }

    private void createNonBlockingStub() {
        futureStub = WeatherServiceGrpc.newFutureStub(channel);
    }

    private void createChannel() {
        channel = ManagedChannelBuilder.forAddress(HOST_NAME, PORT).maxInboundMessageSize(Integer.MAX_VALUE)
                .usePlaintext()
                .build();
    }
    /**
     *
     */
    public void stopChannel() {
        if(channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }

    /**
     *
     * @return - May return null stub
     */
    public WeatherServiceGrpc.WeatherServiceBlockingStub getBlockingStub() {
        return blockingStub;
    }

    public WeatherServiceGrpc.WeatherServiceFutureStub getNonBlockingStub() {
        return futureStub;
    }
}
