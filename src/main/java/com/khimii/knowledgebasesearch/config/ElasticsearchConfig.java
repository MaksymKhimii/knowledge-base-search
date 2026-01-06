package com.khimii.knowledgebasesearch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;

import static com.khimii.knowledgebasesearch.util.ApplicationConstants.*;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(HOST_AND_PORT_ELASTICSEARCH)
                .withHeaders(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HEADERS_KEY_ACCEPT, HEADERS_VALUE_APPLICATION_JSON);
                    headers.add(HEADERS_KEY_CONTENT_TYPE, HEADERS_VALUE_APPLICATION_JSON);
                    headers.add(HEADERS_KEY_X_ELASTIC_PRODUCT, HEADERS_VALUE_X_ELASTICSEARCH);
                    return headers;
                })
                .build();
    }
}
