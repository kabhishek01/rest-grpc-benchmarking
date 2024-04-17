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


# Test format 

Test is divided into 3 categories of the data done over iteration of 500 , 1000 and 2000

- Sigle weather record is passed in GET & POST method for REST/gRPC.
- List of 100,200,500,1000,2000 weather records is passed in GET & POST method for REST/gRPC.
- Bulk(Stream) of 100,200,500,1000,2000 weather recods is passed in GET method for REST/gRPC.

Each of these operation over 500/1000/2000 iteration are averaged out for response times.
Note for average/mean I have used very basic average/mean calculation logic. 

When program runs its prints the results in below format 

11:06:07.965 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- OS Name : Mac OS X
11:06:07.967 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- OS Version : 14.4.1
11:06:07.967 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- OS Arch : aarch64
11:06:07.967 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- java Version : 19
11:06:07.967 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- Test Started ..
11:06:07.967 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- running 500 iteration ..
11:06:56.203 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- running 1000 iteration ..
11:08:26.279 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- running 2000 iteration ..
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- Test Finished ..
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | METHOD | Iterations | Objects Count | Mean (ns) | Mean (ms)
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | REST-SYNC-POST-AS-LIST | 500 | 500  | 1793459 | 1
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | GRPC-SYNC-GET-BULK | 1000 | 1000  | 5520726 | 5
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | REST-SYNC-GET-AS-LIST | 1000 | 2000  | 1736049 | 1
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | REST-SYNC-POST-AS-LIST | 500 | 100  | 1786355 | 1
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | REST-SYNC-GET-AS-LIST | 2000 | 100  | 1705343 | 1
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | GRPC-SYNC-GET-AS-LIST | 2000 | 200  | 198915 | 0
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | REST-SYNC-GET-BULK | 2000 | 1000  | 7614859 | 7
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | REST-SYNC-GET-BULK | 1000 | 100  | 7841856 | 7
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | REST-SYNC-GET-AS-LIST | 2000 | 500  | 1695185 | 1
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | REST-SYNC-POST-AS-LIST | 1000 | 2000  | 1709374 | 1
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | GRPC-SYNC-GET | 2000 | 1  | 168534 | 0
11:11:25.506 [main] INFO com.kabh.client.WeatherApiRestGrpcClient -- | GRPC-SYNC-GET | 1000 | 1  | 182012 | 0
.....
....
...
..
.


# Results



