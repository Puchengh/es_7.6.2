package com.puchen.java.esoptions;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class ESTestDeteleClient {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //查询
        DeleteIndexRequest request = new DeleteIndexRequest("user");
        AcknowledgedResponse delete = esClient.indices().delete(request, RequestOptions.DEFAULT);

        System.out.println(delete.isAcknowledged());

        esClient.close();
    }
}
