package com.puchen.java.spark

import org.apache.http.HttpHost
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.{RequestOptions, RestClient, RestHighLevelClient}
import org.elasticsearch.common.xcontent.XContentType

import java.util.Date

object SparkStreamingESTest {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("ESTest")
    val ssc = new StreamingContext(sparkConf, Seconds(3))
    val ds: ReceiverInputDStream[String] = ssc.socketTextStream("192.168.163.128", 9999)
    ds.foreachRDD(
      rdd => {
        println("*************** " + new Date())
        rdd.foreach(
          data => {
            val client = new RestHighLevelClient(
              RestClient.builder(new HttpHost("localhost", 9200, "http"))
            );
            // 新增文档 - 请求对象
            val request = new IndexRequest();
            // 设置索引及唯一性标识
            val ss = data.split(" ")
            println("ss = " + ss.mkString(","))
            request.index("sparkstreaming").id(ss(0));
            val productJson =
              s"""
                 | { "data":"${ss(1)}" }
                 |""".stripMargin;
            request.source(productJson, XContentType.JSON);
            val response = client.index(request,
              RequestOptions.DEFAULT);
            System.out.println("_index:" + response.getIndex());
            System.out.println("_id:" + response.getId());
            System.out.println("_result:" + response.getResult());
            client.close()
          }
        )
      }
    )
    ssc.start()
    ssc.awaitTermination()
  }
}