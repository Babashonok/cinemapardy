package com.dev.babak.cinemapardy.es.client

import io.searchbox.client.JestClient
import io.searchbox.client.JestClientFactory
import io.searchbox.client.config.HttpClientConfig
import org.elasticsearch.index.mapper.object.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

/**
 * @author Dzmitry Mikhievich
 */
class ElasticSearchConfig {

    @Value('${px.elasticsearch.host}')
    String elasticSearchHost
    @Value('${px.elasticsearch.conn.timeout:3000}')
    int elasticSearchConnTimeout
    @Value('${px.elasticsearch.read.timeout:5000}')
    int elasticSearchReadTimeout

    @Bean
    JestClient jestClient() {
        JestClientFactory factory = new JestClientFactory()
        factory.setHttpClientConfig(new HttpClientConfig.Builder(elasticSearchHost)
                .multiThreaded(true)
                .connTimeout(elasticSearchConnTimeout)
                .readTimeout(elasticSearchReadTimeout)
                .build())
        return factory.getObject()
    }

    @Bean
    ElasticSearchClient elasticSearchClient(ObjectMapper jsonObjectMapper, JestClient jestClient) {
        return new JestElasticSearchClient(jestClient, jsonObjectMapper)
    }
}
