package com.kabh.server;

import com.kabh.server.grpc.WeatherGrpcController;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class WeatherService {

    private final static int PORT = 8088;

    private static Logger logger = LoggerFactory.getLogger(WeatherService.class);
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(WeatherService.class, args);
        startGrpcServer(args);
    }

    public static void startGrpcServer(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(PORT).maxInboundMessageSize(Integer.MAX_VALUE)
                .addService(new WeatherGrpcController())
                .build();

        server.start();
        logger.info("GRPC server is accepting traffic at port {}",PORT);

        server.awaitTermination();
    }
}
