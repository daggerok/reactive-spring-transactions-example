package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClientConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(

    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class DemoApplicationTests {

  @LocalServerPort
  Integer port;

  @Autowired
  WebTestClient webClient;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void contextLoads() {
    webClient.get()
             //.uri(format("http://127.0.0.1:%s/", port))
             .exchange()
             .expectStatus().is2xxSuccessful()
             .expectBody()
             .consumeWith(entityExchangeResult -> {
               String json = entityExchangeResult.toString();
               System.out.println(json);
               String result = new String(Objects.requireNonNull(entityExchangeResult.getResponseBody()), UTF_8);
               assertThat(result).isNotNull()
                                 .isNotEmpty();
               Try<List<MyData>> aTry = Try.of(() -> objectMapper.readValue(result, new TypeReference<List<MyData>>() {}));
               assertThat(aTry.isSuccess()).isTrue();
               List<MyData> list = aTry.getOrElseThrow(RuntimeException::new);
               assertThat(list).hasSizeGreaterThanOrEqualTo(3);
             });
  }
}
