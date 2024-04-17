# Overview

This program compares the network response time between 2 popular protocol/architecture styles  of API programming. 
More information about gRPC and REST can be can be found at https://en.wikipedia.org/wiki/GRPC & https://en.wikipedia.org/wiki/REST wiki pages. 


# Project Structure

Server and Client side code are built into a single maven project structure. 
Java packages com.kbh.server include  server side code
Java Packages com.kbh.client include client side code
package proto contains .proto file
package swagger contains the swagger file.

Generates protobuf files using mavan plugin, all the generate files are generated under the directory target/generated-sources/protobuf
Generates rest model files using mavan plugin, all the generate files are generated under the directory target/generated-sources/openapi

# Project Working

This project written to quick draw comparision between 2 popular protocol and highlights the area's where they each shine. This is not a comprehensive benchmarking and anyone using these data should be doing their own due deligence.

Project uses the weather history data to pass between rest/grpc client and rest/grpc server to measure the response time. 
Response are measured from the point client makes the call and receives and process the response. To be consistent between both the protocol , request prep time is not measured in the response time and not bench marked. 

Program uses JDK17 and above to run. 

As future I will add .bat/.sh script to run the program but for now, this need to be run any of the ide which can support Java.

To run the program 
1. Maven build the project and make sure build is successful.
2. Go to com.kabh.server.WeatherService and run it, this should start the REST and grpc servers.
   Note that grpc server listens at 8088 port and rest server listens at 8080 , make sure these 2 ports are available on your machine.

3. Go to com.kabh.client.WeatherApiRestGrpcClient and run, this should start the client.

Note that to avoid any boiler plate overheads , I wrote a very bare bone code, anyone interested in taking this test further, please fork.






