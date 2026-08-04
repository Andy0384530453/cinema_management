package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.demo.exception.ForbiddenException;
import com.example.demo.model.UserRole;
import java.util.UUID;
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
    mockRequestWithRoleAndId(role, null);
  }

  private void mockRequestWithRoleAndId(String role, String userId) {
    MockHttpServletRequest request = new MockHttpServletRequest();
    if (role != null) {
      request.addHeader("X-User-Role", role);
    }
    if (userId != null) {
      request.addHeader("X-User-Id", userId);
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

  @Test
  void requireAnyRole_withAllowedRole_shouldNotThrow() {
    mockRequestWithRole("EMPLOYEE");
    assertDoesNotThrow(() -> securityService.requireAnyRole(UserRole.EMPLOYEE, UserRole.MANAGER));
  }

  @Test
  void requireAnyRole_withManagerRole_shouldNotThrow() {
    mockRequestWithRole("MANAGER");
    assertDoesNotThrow(() -> securityService.requireAnyRole(UserRole.EMPLOYEE, UserRole.MANAGER));
  }

  @Test
  void requireAnyRole_withClientRole_shouldThrow403() {
    mockRequestWithRole("CLIENT");
    assertThrows(
        ForbiddenException.class,
        () -> securityService.requireAnyRole(UserRole.EMPLOYEE, UserRole.MANAGER));
  }

  @Test
  void getCurrentUserId_withHeader_shouldReturnUuid() {
    UUID id = UUID.randomUUID();
    mockRequestWithRoleAndId("CLIENT", id.toString());
    assertEquals(id, securityService.getCurrentUserId());
  }

  @Test
  void getCurrentUserId_withoutHeader_shouldThrow403() {
    mockRequestWithRoleAndId("CLIENT", null);
    assertThrows(ForbiddenException.class, () -> securityService.getCurrentUserId());
  }

  @Test
  void getCurrentUserId_withInvalidUuid_shouldThrow403() {
    mockRequestWithRoleAndId("CLIENT", "not-a-uuid");
    assertThrows(ForbiddenException.class, () -> securityService.getCurrentUserId());
  }
}