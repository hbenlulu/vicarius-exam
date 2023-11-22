package io.vicarius.exam;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@Import(TestContainerConfiguration.class)
class VicariusExamApplicationTests {

	@Autowired
	private  WebTestClient webClient;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateIndex() {
		webClient.post()
				.uri(uriBuilder -> uriBuilder.path("/index/create")
						.queryParam("name", "index-name")
						.build())
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	public void indexDocument() {

		LogTrace trace = LogTrace.builder()
				.os("macos")
				.deviceType("laptop")
				.message("cyber attack")
				.build();

		webClient.post()
				.uri(uriBuilder -> uriBuilder.path("/doc/log-trace")
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(trace))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(LogTrace.class)
				.consumeWith(result -> {
					Assertions.assertThat(result.getResponseBody().getId()).isNotEmpty();
					Assertions.assertThat(result.getResponseBody().getOs()).isEqualTo("macos");
				});
	}
}
