package com.kabh.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabh.server.rest.model.WeatherData;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class WeatherDataUtil {

    private WeatherData[] wData ;
    private List<com.kabh.server.grpc.WeatherData> wDataGrpc = new ArrayList<>();

    private Random rand = new Random();
    ObjectMapper objectMapper = new ObjectMapper();
    public WeatherDataUtil(){
        try {
            InputStream fileStream = FileUtils.getFileFromResourceAsStream("rdu-weather-history.json");

            objectMapper.registerModule(new JavaTimeModule());
            wData = objectMapper.readValue(fileStream, WeatherData[].class);
            for (WeatherData data : wData) {
                if(data.getAwnd() == null) { data.setAwnd(BigDecimal.valueOf(0));}
                if(data.getSnwd() == null) { data.setSnwd(BigDecimal.valueOf(0));}
                com.kabh.server.grpc.WeatherData gData = convertJsonToGrpc(data);
                wDataGrpc.add(gData);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public WeatherData[] getAllRestWeatherData() {
        return wData;
    }

    public List getAllGrpcWeatherData() {
        return wDataGrpc;
    }

    /**
     *
     * @return random Weather Data from the Array
     */
    public WeatherData getRandomWeatherData() {
        Random rand = new Random();
        return wData[rand.nextInt(wData.length)];
    }

    public com.kabh.server.grpc.WeatherData getRandomGrpcWeatherData() {
        Random rand = new Random();
        return wDataGrpc.get(rand.nextInt(wDataGrpc.size()));
    }

    public static com.kabh.server.grpc.WeatherData convertJsonToGrpc( WeatherData data) {
        com.kabh.server.grpc.WeatherData grpcData = com.kabh.server.grpc.WeatherData.newBuilder()
                .setPrcp(data.getPrcp().doubleValue())
                .setSnow(data.getSnow().doubleValue())
                .setAwnd(data.getAwnd().doubleValue())
                .setTmax(data.getTmax().intValue())
                .setTmin(data.getTmin().intValue())
                .setSnwd(data.getSnwd().doubleValue()).build();
                //.setDate(com.google.protobuf.Timestamp.data.getDate().)
    return grpcData;
    }
}
