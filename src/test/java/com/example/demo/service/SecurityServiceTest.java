package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.demo.entity.enums.UserRole;
import com.example.demo.exception.ForbiddenException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class SecurityServiceTest {
  private final SecurityService securityService = new SecurityService();

  @AfterEach
  void tearDown() {
    RequestContextHolder.resetRequestAttributes();
  }

  private void mockRequestWithRole(String role) {
    MockHttpServletRequest request = new MockHttpServletRequest();
    if (role != null) {
      request.addHeader("X-User-Role", role);
    }
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }

  @Test
  void getCurrentUserRole_withManagerHeader_shouldReturnManager() {
    mockRequestWithRole("MANAGER");

    assertEquals(UserRole.MANAGER, securityService.getCurrentUserRole());
  }

  @Test
  void getCurrentUserRole_withClientHeader_shouldReturnClient() {
    mockRequestWithRole("CLIENT");

    assertEquals(UserRole.CLIENT, securityService.getCurrentUserRole());
  }

  @Test
  void getCurrentUserRole_withoutHeader_shouldThrow403() {
    mockRequestWithRole(null);

    assertThrows(ForbiddenException.class, () -> securityService.getCurrentUserRole());
  }

  @Test
  void getCurrentUserRole_withUnknownRole_shouldThrow403() {
    mockRequestWithRole("PIRATE");

    assertThrows(ForbiddenException.class, () -> securityService.getCurrentUserRole());
  }

  @Test
  void getCurrentUserRole_withoutRequestContext_shouldThrow403() {
    assertThrows(ForbiddenException.class, () -> securityService.getCurrentUserRole());
  }

  @Test
  void requireRole_withMatchingRole_shouldNotThrow() {
    mockRequestWithRole("MANAGER");

    assertDoesNotThrow(() -> securityService.requireRole(UserRole.MANAGER));
  }

  @Test
  void requireRole_withLowerRole_shouldThrow403() {
    mockRequestWithRole("CLIENT");

    assertThrows(ForbiddenException.class, () -> securityService.requireRole(UserRole.MANAGER));
  }
}
