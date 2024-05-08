package com.puchen.java.esoptions;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class ESTestDocDeleteClient {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        DeleteRequest getRequest = new DeleteRequest();
        getRequest.index("user").id("1001");

        DeleteResponse delete = esClient.delete(getRequest, RequestOptions.DEFAULT);
        System.out.println(delete.getResult());

        esClient.close();
    }
}
