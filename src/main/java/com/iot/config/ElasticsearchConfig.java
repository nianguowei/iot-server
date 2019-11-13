package com.iot.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述: elasticsearch 配置
 *
 * @author Nian Guowei
 * @create 2018-11-01 16:41
 **/
@Configuration
@Data
public class ElasticsearchConfig {

    /**
     * elk集群地址
     */
    @Value("${elasticsearch.ip}")
    private String hostName;
    /**
     * 端口
     */
    @Value("${elasticsearch.port}")
    private String port;

    /**
     * scheme
     */
    @Value("${elasticsearch.scheme}")
    private String scheme;
//    /**
//     * 集群名称
//     */
//    @Value("${elasticsearch.cluster-name}")
//    private String clusterName;
//
//    @Value("${elasticsearch.node-name}")
//    private String nodeName;
//    /**
//     * 连接池
//     */
//    @Value("${elasticsearch.pool}")
//    private String poolSize;


    @Bean
    public RestHighLevelClient init() {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostName, Integer.parseInt(port), scheme)));

        return client;
    }

}
