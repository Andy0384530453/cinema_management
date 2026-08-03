package com.example.demo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class GlobalExceptionHandlerTest {
  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  void handleForbidden_shouldReturn403() {
    var response = handler.handleForbidden(new ForbiddenException("Access denied"));

    assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode().value());
    assertEquals("Access denied", response.getBody());
  }

  @Test
  void handleNotFound_shouldReturn404() {
    var response = handler.handleNotFound(new NotFoundException("Movie not found"));

    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
    assertEquals("Movie not found", response.getBody());
  }

  @Test
  void handleBadRequest_shouldReturn400() {
    var response = handler.handleBadRequest(new BadRequestException("Title is required"));

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
    assertEquals("Title is required", response.getBody());
  }

  @Test
  void handleUnexpected_shouldReturn500() {
    var response = handler.handleUnexpected(new RuntimeException("boom"));

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode().value());
  }
}
