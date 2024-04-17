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
GET operation in this test refers to a invocation in which Rest and gRPC clients asks for the data in the batch of 100/200/500/1000/2000 weather records from respective servers.

gRPC outperforms the REST by 6x on average. 
Although gRPC performace started to degrade as the size of the load/batch increased. 

![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/7de64f85-43b3-4e21-b72b-29e1f04c69ae)

Note in the graph below 100/200/500/1000/2000 denotes the size of the batch.
<img width="875" alt="image" src="https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/6e30e298-7efa-4686-9d40-07894096cab1">


# Rest vs gRPC - POST as LIST
POST operation in this test refers to a invocation in which Rest and gRPC clients sends in the batch of 100/200/500/1000/2000 weather records to respective servers.

gRPC outperforms the REST by 5x on average. 
Interesting observation is that, POST behaviour with batches of load is completely different that a single record POST.
I was not able to investigate this deviation is behaviour but for sure if there payload size is huge, gRPC is winning the round !!


GRPC-SYNC-POST-AS-LIST 	296585.6667
![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/027f1256-a7e9-40ec-a704-740940175a09)
<img width="875" alt="image" src="https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/e097e143-eda7-47ed-8885-5c1f4cd421bb">


# Rest vs gRPC - GET as Bulk
GET operation in this test refers to a invocation in which Rest and gRPC clients asks for the data in a stream which is batch of 100/200/500/1000/2000 weather records from respective servers.

Although gRPC performs 2x faster than REST, REST response time remained consistent through out the test irrespective of batch size,  on the other hand gRPC response time kept climbing and it surpassed REST time of the load of 2000 record batches. If this is just one of the case or the consistent behaviour, it need to tested on a different machines to figure out.

![image](https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/46f7badf-0338-40b9-84d4-eb0e93a3e733)
<img width="875" alt="image" src="https://github.com/kabhishek01/rest-grpc-benchmarking/assets/11838719/a793c437-4c91-472b-a82b-d49c6e9521ab">

