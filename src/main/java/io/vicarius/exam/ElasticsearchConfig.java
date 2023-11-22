package io.vicarius.exam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;

//@Configuration
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUri;

    @Bean
    public ClientConfiguration buildClient() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUri)
                .build();
    }
}
