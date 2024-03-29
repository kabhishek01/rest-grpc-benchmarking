package com.kabh.client.grpc;

import com.kabh.server.grpc.*;

import com.kabh.utils.WeatherDataUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeatherApiGrpcSyncClient {
    GrpcClient grpcClient = new GrpcClient();
    WeatherDataUtil dataUtil = new WeatherDataUtil();

    private final int postRecordSize = 1000;

    public long getWeather() {
        long duration = 0;
        try {


            WeatherServiceGrpc.WeatherServiceBlockingStub stub = grpcClient.getBlockingStub();
            WeatherParameters request = WeatherParameters
                    .newBuilder()
                    .build();
            long startTime = System.nanoTime();
            WeatherData response = stub.getWeather(request);
            duration = System.nanoTime() - startTime;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

    public long postWeather() {
        long duration = 0;
        try {

            WeatherServiceGrpc.WeatherServiceBlockingStub stub = grpcClient.getBlockingStub();
            WeatherData request = WeatherData
                    .newBuilder(dataUtil.getRandomGrpcWeatherData())
                    .build();
            long startTime = System.nanoTime();
            EmptyResponse response = stub.postWeather(request);
            duration = System.nanoTime() - startTime;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

    public long getWeatherAsList(int payLoadCount) {
        long duration = 0;
        try {
            WeatherServiceGrpc.WeatherServiceBlockingStub stub = grpcClient.getBlockingStub();
            WeatherParameters request = WeatherParameters
                    .newBuilder()
                    .setRowsCount(payLoadCount)
                    .build();

            long startTime = System.nanoTime();
            WeatherDataList response = stub.getWeatherBulkList(request);
            duration = System.nanoTime() - startTime;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

    public long postWeatherAsList() {
        long duration = 0;
        try {
            List<WeatherData> wData = new ArrayList<>();
            for (int i = 0 ; i < postRecordSize ; i++) {
                com.kabh.server.grpc.WeatherData grpcData = com.kabh.server.grpc.WeatherData
                        .newBuilder(dataUtil.getRandomGrpcWeatherData())
                        .build();
                wData.add(grpcData);
            }
            com.kabh.server.grpc.WeatherDataList dataList = WeatherDataList
                    .newBuilder().addAllWeatherData(wData).build();
            WeatherServiceGrpc.WeatherServiceBlockingStub stub = grpcClient.getBlockingStub();

            long startTime = System.nanoTime();
            EmptyResponse response = stub.postWeatherBulkList(dataList);
            duration = System.nanoTime() - startTime;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

    public long getWeatherBulk(int payLoadCount) {
        long duration = 0;
        try {
            WeatherServiceGrpc.WeatherServiceBlockingStub stub = grpcClient.getBlockingStub();
            WeatherParameters request = WeatherParameters
                    .newBuilder()
                    .setRowsCount(payLoadCount)
                    .build();

            long startTime = System.nanoTime();
            Iterator<WeatherData> response = stub.getWeatherBulk(request);
            response.forEachRemaining(data -> {
                    }

            );
            duration = System.nanoTime() - startTime;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;
    }

}
