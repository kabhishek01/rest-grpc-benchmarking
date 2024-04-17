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

Program supports JDK17 and above to run. 

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
<img width="1122" alt="image" src="https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/97e263ab-448a-4ed2-a038-35bbe340bae2">


# Results

## Rest vs gRPC - GET
GET operation in this test refers to a invocation in which Rest and gRPC clients asks for a single weather records from respective servers.

gRPC outperforms the REST by 7x on each of the iteration runs. 

![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/79034a5f-a900-4e1a-9605-1ca0d0caffe5)

<img width="765" alt="image" src="https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/3c4ea183-ffc8-421b-9df4-9a7f4b72aa4c">


## Rest vs gRPC - POST
POST operation in this test refers to a invocation in which Rest and gRPC clients sends for a single weather records to respective servers.
To my surprise, although gRPC has little better response time, it was negligible enough to conclude that both performed equal.

![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/f9f21bf3-ff5b-4793-8fb0-7cfcb8439274)

<img width="765" alt="image" src="https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/4f0ce5f9-61e6-475b-8e75-c34e3bc3e453">


# Rest vs gRPC - GET as LIST

