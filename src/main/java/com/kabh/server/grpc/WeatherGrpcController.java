package com.kabh.server.grpc;


import com.kabh.utils.WeatherDataUtil;

import java.util.ArrayList;
import java.util.List;

public class WeatherGrpcController extends com.kabh.server.grpc.WeatherServiceGrpc.WeatherServiceImplBase {
    WeatherDataUtil weatherDataUtil = new WeatherDataUtil();

    public void getWeather(com.kabh.server.grpc.WeatherParameters request,
                           io.grpc.stub.StreamObserver<com.kabh.server.grpc.WeatherData> responseObserver) {

      com.kabh.server.grpc.WeatherData grpcData = com.kabh.server.grpc.WeatherData
              .newBuilder(weatherDataUtil.getRandomGrpcWeatherData())
              .build();

        responseObserver.onNext(grpcData);
        responseObserver.onCompleted();
    }


    /**
     *
     */
    public void getWeatherBulk(com.kabh.server.grpc.WeatherParameters request,
                               io.grpc.stub.StreamObserver<com.kabh.server.grpc.WeatherData> responseObserver) {
        for (int i = 0 ; i < request.getRowsCount() ; i++) {
            com.kabh.server.grpc.WeatherData grpcData = com.kabh.server.grpc.WeatherData
                    .newBuilder(weatherDataUtil.getRandomGrpcWeatherData())
                    .build();

            responseObserver.onNext(grpcData);
        }
        responseObserver.onCompleted();
    }

    /**
     *
     */
    public void getWeatherBulkList(com.kabh.server.grpc.WeatherParameters request,
                                   io.grpc.stub.StreamObserver<com.kabh.server.grpc.WeatherDataList> responseObserver) {
        List<com.kabh.server.grpc.WeatherData> wData = new ArrayList<>();
        for (int i = 0 ; i < request.getRowsCount() ; i++) {
            com.kabh.server.grpc.WeatherData grpcData = com.kabh.server.grpc.WeatherData
                    .newBuilder(weatherDataUtil.getRandomGrpcWeatherData())
                    .build();
            wData.add(grpcData);
        }
        com.kabh.server.grpc.WeatherDataList dataList = WeatherDataList
                .newBuilder().addAllWeatherData(wData).build();
        responseObserver.onNext(dataList);
        responseObserver.onCompleted();

    }

    /**
     *
     */
    public void postWeather(com.kabh.server.grpc.WeatherData request,
                            io.grpc.stub.StreamObserver<com.kabh.server.grpc.EmptyResponse> responseObserver) {

        com.kabh.server.grpc.EmptyResponse responseData = com.kabh.server.grpc.EmptyResponse
                .newBuilder()
                .setResponseCode(200)
                .setMessage("Weather Data received !!")
                .build();

        responseObserver.onNext(responseData);
        responseObserver.onCompleted();
    }

    /**
     *
     */
    @Override
    public io.grpc.stub.StreamObserver<com.kabh.server.grpc.WeatherData> postWeatherBulk(
            io.grpc.stub.StreamObserver<com.kabh.server.grpc.EmptyResponse> responseObserver) {

        return new io.grpc.stub.StreamObserver<com.kabh.server.grpc.WeatherData>() {
            List<WeatherData> listWData = new ArrayList<WeatherData>();
            @Override
            public void onNext(WeatherData weatherData) {
                listWData.add(weatherData);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                com.kabh.server.grpc.EmptyResponse responseData = com.kabh.server.grpc.EmptyResponse
                        .newBuilder()
                        .setResponseCode(200)
                        .setMessage("Weather Data received !!")
                        .build();

                responseObserver.onNext(responseData);
                responseObserver.onCompleted();
            }
        };
    }

    /**
     *
     */
    public void postWeatherBulkList(com.kabh.server.grpc.WeatherDataList request,
                                    io.grpc.stub.StreamObserver<com.kabh.server.grpc.EmptyResponse> responseObserver) {

        request.getWeatherDataList();
        com.kabh.server.grpc.EmptyResponse responseData = com.kabh.server.grpc.EmptyResponse
                .newBuilder()
                .setResponseCode(200)
                .setMessage("Weather Data received !!")
                .build();
        responseObserver.onNext(responseData);
        responseObserver.onCompleted();
    }




}


