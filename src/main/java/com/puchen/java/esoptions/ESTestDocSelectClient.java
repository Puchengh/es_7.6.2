package com.puchen.java.esoptions;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class ESTestDocSelectClient {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        GetRequest getRequest = new GetRequest();
        getRequest.index("user").id("1001");

        GetResponse response = esClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
        esClient.close();
    }
}
