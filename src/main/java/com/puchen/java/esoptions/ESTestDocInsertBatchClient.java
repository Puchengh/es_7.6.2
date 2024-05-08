package com.puchen.java.esoptions;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class ESTestDocInsertBatchClient {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        //插入数据

        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON,"name","zhangsan","sex","男","age",30));
        bulkRequest.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON,"name","lisi","sex","女","age",29));
        bulkRequest.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON,"name","wangwu","sex","男","age",28));
        bulkRequest.add(new IndexRequest().index("user").id("1004").source(XContentType.JSON,"name","zhaoliu","sex","男","age",27));

        BulkResponse bulk = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        System.out.println(bulk.getTook());
        System.out.println(bulk.getItems());

        esClient.close();
    }
}
