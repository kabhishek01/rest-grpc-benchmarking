package com.kabh.server.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kabh.server.rest.model.EmptyResponse;
import com.kabh.server.rest.model.WeatherData;
import com.kabh.server.rest.model.WeatherParameter;
import com.kabh.utils.WeatherDataUtil;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WeatherRestController {

    WeatherDataUtil weatherDataUtil = new WeatherDataUtil();
    private ObjectMapper objectMapper  = new ObjectMapper();

    private final int defaultRowCount = 1000;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/weather",
            produces = { "application/json" }
    )
    public ResponseEntity<WeatherData> getWeather() {
        WeatherData data = new WeatherDataUtil().getRandomWeatherData();
        return ResponseEntity.accepted().body(data);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/weather",
            produces = { "application/json" }
    )
    public ResponseEntity<EmptyResponse> postWeather( @RequestBody(required = false) WeatherData weatherData) {
        EmptyResponse response = new EmptyResponse();
        response.setCode(200);
        response.setMessage("Message received !!");
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/weatherbulklist",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<List<WeatherData>> getWeatherBulkList(@RequestParam("rowcount") int pathRowCount, @RequestBody(required = false) WeatherParameter parameter) {
        List<WeatherData> response = new ArrayList<WeatherData>() ;
            int rowCount = parameter != null ? pathRowCount : defaultRowCount;
            for (int i = 0 ; i < rowCount ; i++) {
                response.add(weatherDataUtil.getRandomWeatherData());
        }
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/weatherbulklist",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<EmptyResponse> postWeatherBulkList(@RequestBody(required = false)
                                                                 List<@Valid WeatherData> weatherData) {
        EmptyResponse response = new EmptyResponse();
        response.setCode(200);
        response.setMessage("Message received !!");
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/weatherbulk",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )

    public ResponseEntity<StreamingResponseBody> getWeatherBulk(@RequestParam("rowcount") int pathRowCount, @RequestBody(required = false) WeatherParameter parameter) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        StreamingResponseBody responseBody = out -> {
            int rowCount = parameter != null ? pathRowCount : defaultRowCount;
            for (int i = 0 ; i < rowCount ; i++) {
                out.write(objectMapper.writeValueAsBytes(weatherDataUtil.getRandomWeatherData()));
                out.flush();
            }

        };
        return ResponseEntity.accepted().body(responseBody);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/weatherbulk",
            produces = { "application/json" },
            consumes = { "application/json" }
    )

    public ResponseEntity<EmptyResponse> postWeatherBulk(@RequestBody(required = false) WeatherData weatherData) {

        EmptyResponse response = new EmptyResponse();
        response.setCode(200);
        response.setMessage("Message received !!");
        return ResponseEntity.accepted().body(response);
    }
}
