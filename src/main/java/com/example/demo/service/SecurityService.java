package com.example.demo.service;

import com.example.demo.entity.enums.UserRole;
import com.example.demo.exception.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class SecurityService {

  private static final String ROLE_HEADER = "X-User-Role";

  public UserRole getCurrentUserRole() {
    HttpServletRequest request = currentRequest();
    String role = request.getHeader(ROLE_HEADER);
    if (role == null || role.isBlank()) {
      throw new ForbiddenException("No authenticated user");
    }
    try {
      return UserRole.valueOf(role.trim().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new ForbiddenException("Unknown role: " + role);
    }
  }

  public void requireRole(UserRole requiredRole) {
    UserRole currentRole = getCurrentUserRole();
    if (currentRole != requiredRole) {
      throw new ForbiddenException("Access denied for role " + currentRole);
    }
  }

  private HttpServletRequest currentRequest() {
    var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes == null) {
      throw new ForbiddenException("No authenticated user");
    }
    return attributes.getRequest();
  }
}
