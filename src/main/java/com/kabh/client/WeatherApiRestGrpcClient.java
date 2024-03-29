package com.kabh.client;

import com.kabh.client.grpc.WeatherApiGrpcSyncClient;
import com.kabh.client.rest.WeatherApiRestSyncClient;
import com.kabh.client.utils.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

public class WeatherApiRestGrpcClient {
    private static HashMap<String, Measurement> metricsMap = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(WeatherApiRestGrpcClient.class);



    public static void main(String[] args) throws IOException {
        logger.info("OS Name : {}", System.getProperty("os.name"));
        logger.info("OS Version : {}",System.getProperty("os.version"));
        logger.info("OS Arch : {}", System.getProperty("os.arch"));
        logger.info("java Version : {}" , System.getProperty("java.version"));

        logger.info("Test Started ..");
        logger.info("running 500 iteration ..");
        invokeMethods(500);

        logger.info("running 1000 iteration ..");
        invokeMethods(1000);

        logger.info("running 2000 iteration ..");
        invokeMethods(2000);
        logger.info("Test Finished ..");
        printHeaders();
        calculateAverages();
    }

    private static void invokeMethods(int iterationCount) {
        WeatherApiGrpcSyncClient grpcSyncClient = new WeatherApiGrpcSyncClient();
        WeatherApiRestSyncClient restSyncClient = new WeatherApiRestSyncClient();

        int payLoadCount = 1;
        for (int i = 0 ; i <iterationCount ; i++) {
            setMetricesDetails("GRPC-SYNC-GET" , grpcSyncClient.getWeather(), 1, iterationCount);
            setMetricesDetails("REST-SYNC-GET",restSyncClient.getWeather(), 1, iterationCount);
            setMetricesDetails("GRPC-SYNC-POST" , grpcSyncClient.postWeather(), 1, iterationCount);
            setMetricesDetails("REST-SYNC-POST",restSyncClient.postWeather(), 1, iterationCount);

            callBulkMethods(100, iterationCount, grpcSyncClient, restSyncClient);
            callBulkMethods(200, iterationCount, grpcSyncClient, restSyncClient);
            callBulkMethods(500, iterationCount, grpcSyncClient, restSyncClient);
            callBulkMethods(1000, iterationCount, grpcSyncClient, restSyncClient);
            callBulkMethods(2000, iterationCount, grpcSyncClient, restSyncClient);

        }
    }

    private static void callBulkMethods(int payLoadCount, int iterationCount, WeatherApiGrpcSyncClient grpcSyncClient, WeatherApiRestSyncClient restSyncClient ) {
        setMetricesDetails("GRPC-SYNC-GET-AS-LIST-" + payLoadCount, grpcSyncClient.getWeatherAsList(payLoadCount),payLoadCount, iterationCount);
        setMetricesDetails("REST-SYNC-GET-AS-LIST-"+ payLoadCount, restSyncClient.getWeatherAsList(payLoadCount), payLoadCount, iterationCount);
        setMetricesDetails("GRPC-SYNC-POST-AS-LIST-"+ payLoadCount, grpcSyncClient.postWeatherAsList(), payLoadCount, iterationCount);
        setMetricesDetails("REST-SYNC-POST-AS-LIST-"+ payLoadCount, restSyncClient.postWeatherAsList(), payLoadCount, iterationCount);
        setMetricesDetails("REST-SYNC-GET-BULK-"+ payLoadCount, restSyncClient.getWeatherBulk(payLoadCount), payLoadCount, iterationCount);
        setMetricesDetails("GRPC-SYNC-GET-BULK-"+ payLoadCount, grpcSyncClient.getWeatherBulk(payLoadCount), payLoadCount, iterationCount);
    }
    private static void setMetricesDetails (String methodName, long duration, int payloadCount , int iterationCount) {
        if(metricsMap.get(methodName) != null) {
            Measurement measure = metricsMap.get(methodName);
            measure.setSumResponseTime(measure.getSumResponseTime() + duration);
        } else {
            Measurement measure = new Measurement();
            measure.setMetricsName(methodName);
            measure.setIterationCount(iterationCount);
            measure.setPayLoadCount(payloadCount);
            measure.setSumResponseTime(measure.getSumResponseTime() + duration);
            metricsMap.put(methodName, measure);
        }
    }
    private static void printHeaders()
    {

        //logger.info("|      METHOD             |    Run Count     |    Response Time (NanoSecond)   |    Response Time (Millis)  ");
        logger.info("| {}  | {} | {} | {}","METHOD", "Run Count", "Mean (ns)", "Mean (ms)");

    }

    private static void calculateAverages() {
        metricsMap.forEach((key, value) -> {
            long mean =  value.getSumResponseTime()/ value.getIterationCount();
            logger.info("| {}  | {} | {} | {}",key, value.getIterationCount(), mean, mean/1000000);
        });
    }
}
