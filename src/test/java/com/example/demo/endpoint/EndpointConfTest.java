package com.example.demo.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EndpointConfTest {
  private final EndpointConf endpointConf = new EndpointConf();

  @Test
  void objectMapper_shouldIgnoreUnknownProperties() throws Exception {
    ObjectMapper mapper = endpointConf.objectMapper();
    SimpleBean bean = mapper.readValue("{\"a\":1,\"unknown\":2}", SimpleBean.class);

    assertEquals(1, bean.a);
  }

  @Test
  void objectMapper_shouldRegisterJavaTimeModule() throws Exception {
    ObjectMapper mapper = endpointConf.objectMapper();

    String json = mapper.writeValueAsString(LocalDate.of(2024, 1, 1));

    assertEquals("\"2024-01-01\"", json);
  }

  public static class SimpleBean {
    public int a;
  }
}
