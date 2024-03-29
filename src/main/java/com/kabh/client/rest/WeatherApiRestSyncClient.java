package com.kabh.client.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kabh.server.rest.model.EmptyResponse;
import com.kabh.server.rest.model.WeatherData;
import com.kabh.server.rest.model.WeatherParameter;
import com.kabh.utils.WeatherDataUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherApiRestSyncClient {

    private ObjectMapper objectMapper ;
    private ApiClient apiClient;

    private WeatherDataUtil weatherDataUtil;

    private final int postRecordSize = 1000;
    public WeatherApiRestSyncClient() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        apiClient = new ApiClient();
        weatherDataUtil = new WeatherDataUtil();
    }

    private WeatherDataUtil dataUtil = new WeatherDataUtil();

    public long getWeather() {
        long duration = 0;
        try {
            Request request = new Request.Builder().url(apiClient.getBasePath() + "/weather").build();
            long startTime = System.nanoTime();
            ResponseBody response = apiClient.invokeApi(request);
            WeatherData data = objectMapper.readValue(response.bytes(), WeatherData.class);
            duration = System.nanoTime() - startTime;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return duration;
    }

    public long postWeather() {
        long duration = 0;
        try {
            WeatherData data = dataUtil.getRandomWeatherData();
            RequestBody body = RequestBody.create( objectMapper.writeValueAsBytes(data), MediaType.parse("application/json"));
            Request request = new Request.Builder().url(apiClient.getBasePath() + "/weather").post(body).build();

            long startTime = System.nanoTime();
            ResponseBody response = apiClient.invokeApi(request);
            EmptyResponse responseData = objectMapper.readValue(response.bytes(), EmptyResponse.class);
            duration = System.nanoTime() - startTime;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return duration;
    }
    public long getWeatherAsList(int payLoadCount) {
        long duration = 0;
        try {
            WeatherParameter data = new WeatherParameter();
            data.setRowsCount(postRecordSize);
            RequestBody body = RequestBody.create(objectMapper.writeValueAsBytes(data),MediaType.parse("application/json"));
            Request request = new Request.Builder().url(apiClient.getBasePath()+"/weatherbulklist?rowcount="+payLoadCount).get().build();
            long startTime=System.nanoTime();
            ResponseBody response = apiClient.invokeApi(request);
            List<WeatherData> responseData = objectMapper.readValue(response.bytes(), List.class);
            duration = System.nanoTime() - startTime;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return duration;
    }

    public long postWeatherAsList() {
        long duration = 0;
        try {
            List<WeatherData> requestData = new ArrayList<WeatherData>() ;

            for (int i = 0 ; i < postRecordSize ; i++) {
                requestData.add(weatherDataUtil.getRandomWeatherData());
            }
            RequestBody body = RequestBody.create( objectMapper.writeValueAsBytes(requestData), MediaType.parse("application/json"));
            Request request = new Request.Builder().url(apiClient.getBasePath() + "/weatherbulklist").post(body).build();

            long startTime = System.nanoTime();
            ResponseBody response = apiClient.invokeApi(request);

            EmptyResponse responseData = objectMapper.readValue(response.bytes(), EmptyResponse.class);
            duration = System.nanoTime() - startTime;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return duration;
    }
    public long getWeatherBulk(int payLoadCount) {
        long duration = 0;
        try {
            WeatherParameter data = new WeatherParameter();
            data.setRowsCount(postRecordSize);
            RequestBody body = RequestBody.create(objectMapper.writeValueAsBytes(data),MediaType.parse("application/json"));
            Request request = new Request.Builder().url(apiClient.getBasePath()+"/weatherbulk?rowcount="+payLoadCount).get().build();
            long startTime=System.nanoTime();
            ResponseBody response = apiClient.invokeApi(request);
            WeatherData responseData = objectMapper.readValue(response.byteStream(), WeatherData.class);
            duration = System.nanoTime() - startTime;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return duration;
    }

}
