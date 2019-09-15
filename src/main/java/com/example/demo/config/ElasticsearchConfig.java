package com.example.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class ElasticsearchConfig {
	
	@Value("${elasticsearch.host}")
	private String host;
	
	@Value("${elasticsearch.port}")
	private int port;
	
	@Bean(destroyMethod = "close")
	public RestHighLevelClient client() {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost(host,port)));
		return client;
	}
}
