package io.vicarius.exam;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainerConfiguration {
    @Bean
    @ServiceConnection
    @RestartScope
    public ElasticsearchContainer container () {
        ElasticsearchContainer container = new ElasticsearchContainer(DockerImageName.parse("elasticsearch:8.7.1")
                .asCompatibleSubstituteFor("docker.elastic.co/elasticsearch/elasticsearch"))
                .withEnv("xpack.security.enabled","false")
                .withEnv("xpack.security.http.ssl.enabled","false")
                .withPrivilegedMode(true)
                .withExposedPorts(9200);
        container.start();

        System.setProperty("spring.elasticsearch.uris", "http://localhost:" + container.getMappedPort(9200).toString());

        return container;
    }

}
