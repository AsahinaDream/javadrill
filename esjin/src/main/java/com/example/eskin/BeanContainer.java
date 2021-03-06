package com.example.eskin;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanContainer {
    @Bean
    public RestClient getRestClient(){
        return RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();
    }

    @Bean
    public RestHighLevelClient getRestHighLevelClient(){
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }
}
