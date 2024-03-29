package com.kabh.client.rest;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

public class ApiClient {

    private String basePath = "http://localhost:8080";
    private OkHttpClient client = null;

    private WebClient webClient;


    public ApiClient() {
        buildOkHTTPClient();
        buildWebClient();

    }

    private  void buildWebClient() {
        webClient =  WebClient.create();
    }
    public String getBasePath(){
        return basePath;
    }
    private void buildOkHTTPClient() {
        client = new OkHttpClient();
    }

    public ResponseBody invokeApi(Request request) throws IOException {
        Call call = client.newCall(request);
        ResponseBody response = call.execute().body();
        return response;
    }

    public WebClient getWebClient() {
        return webClient;
    }

}
