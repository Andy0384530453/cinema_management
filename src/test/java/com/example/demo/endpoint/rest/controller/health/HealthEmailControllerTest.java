package com.example.demo.endpoint.rest.controller.health;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.mail.Mailer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HealthEmailController.class)
class HealthEmailControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private Mailer mailer;

  @Test
  void sendEmails_shouldReturnOK() throws Exception {
    mockMvc
        .perform(get("/health/email").param("to", "user@example.com"))
        .andExpect(status().isOk());
  }
}
