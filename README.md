# Overview

This program compares the network response time between 2 popular protocol/architecture styles  of API programming. 
More information about gRPC and REST can be can be found at https://en.wikipedia.org/wiki/GRPC & https://en.wikipedia.org/wiki/REST wiki pages. 


# Project Structure

- Server and Client side code are built into a single maven project structure. 
- Java packages com.kbh.server include  server side code
- Java Packages com.kbh.client include client side code
- package proto contains .proto file
- package swagger contains the swagger file.

Generates protobuf files using mavan plugin, all the generate proto files are placed under the directory target/generated-sources/protobuf
Generates rest model files using mavan plugin, all the generate swagger model files are placed under the directory target/generated-sources/openapi

# Project Working

This project draws a quick comparision between 2 popular protocol and highlights the area's they each excel in terms of GET/POST operation. This is not a comprehensive benchmarking and anyone using these data should be doing their own due deligence.

Project uses the weather history data to pass between rest/grpc client and rest/grpc server to measure the response time. 
Response times are measured from the point client makes the call and process the response. To be consistent between both the protocols.

Program supports JDK17 and above to run. 

In future I will add .bat/.sh script to run the program but for now, this need to be run in any of the ide which can support Java.

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

Example of single record of weather data -
{"date": "2017-01-01", "tmin": 41, "tmax": 50, "prcp": 0.54, "snow": 0.0, "snwd": 0.0, "awnd": 6.49}

Each of these operation over 500/1000/2000 iteration are averaged out for response times.
Note for average/mean I have used very basic average/mean calculation logic. 

When program runs its prints the results in below format 
<img width="1122" alt="image" src="https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/97e263ab-448a-4ed2-a038-35bbe340bae2">


# Results

## Rest vs gRPC - GET
GET operation in this test refers to a invocation in which Rest and gRPC clients asks for a single weather records from respective servers.

gRPC outperforms the REST by 26x on each of the iteration(500/1000/2000) runs. 

![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/79034a5f-a900-4e1a-9605-1ca0d0caffe5)

(500/1000/2000) --> Represents number of Iterations
![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/c24efecc-4575-4cc3-a9e6-6c9b0b9ec9ff)



## Rest vs gRPC - POST
POST operation in this test refers to a invocation in which Rest and gRPC clients sends for a single weather records to respective servers.
To my surprise, although gRPC has little better response time, it was negligible enough to conclude that both performed equal.

![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/f9f21bf3-ff5b-4793-8fb0-7cfcb8439274)

(500/1000/2000) --> Represents number of Iterations
![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/a607500f-1222-44ee-9eed-8b8fbcfd1386)



## Rest vs gRPC - GET as LIST
GET operation in this test refers to a invocation in which Rest and gRPC clients asks for the data in the batch of 100/200/500/1000/2000 weather records from respective servers.

gRPC outperforms the REST by 6x on average. 
Although gRPC performace started to degrade as the size of the load/batch increased. 

![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/7de64f85-43b3-4e21-b72b-29e1f04c69ae)

Note in the graph below 100/200/500/1000/2000 denotes the size of the batch.
<img width="875" alt="image" src="https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/6e30e298-7efa-4686-9d40-07894096cab1">


## Rest vs gRPC - POST as LIST
POST operation in this test refers to a invocation in which Rest and gRPC clients sends in the batch of 100/200/500/1000/2000 weather records to respective servers.

gRPC outperforms the REST by 5x on average. 
Interesting observation is that, POST behaviour with batches of load is completely different that a single record POST.
I was not able to investigate this deviation is behaviour but for sure if there payload size is huge, gRPC is winning the round !!

![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/027f1256-a7e9-40ec-a704-740940175a09)

Note in the graph below 100/200/500/1000/2000 denotes the size of the batch.
<img width="875" alt="image" src="https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/e097e143-eda7-47ed-8885-5c1f4cd421bb">


## Rest vs gRPC - GET as Bulk
GET operation in this test refers to a invocation in which Rest and gRPC clients asks for the data in a stream which is batch of 100/200/500/1000/2000 weather records from respective servers.

Although gRPC performs 2x faster than REST, REST response time remained consistent through out the test irrespective of batch size,  on the other hand gRPC response time kept climbing and it surpassed REST time of the load of 2000 record batches. If this is just one of the case or the consistent behaviour, it need to tested on a different machines to figure out.

![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/46f7badf-0338-40b9-84d4-eb0e93a3e733)

Note in the graph below 100/200/500/1000/2000 denotes the size of the batch.
<img width="875" alt="image" src="https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/a793c437-4c91-472b-a82b-d49c6e9521ab">

# Conclusion
gRPC seems to be much faster compare to REST. In my observation if the payload is pure String and does not contains any primitive data types, REST is more suitable and flexible protocol. In case payload has lot of primitive data types, gRPC for sure out performes REST but its complex and there is learning curve to it. 

Good read about this topic can be found at https://blog.postman.com/grpc-vs-rest/



