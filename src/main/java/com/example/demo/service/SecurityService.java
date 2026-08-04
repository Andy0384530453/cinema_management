package com.example.demo.service;

import com.example.demo.exception.ForbiddenException;
import com.example.demo.model.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class SecurityService {

  private static final String ROLE_HEADER = "X-User-Role";
  private static final String USER_ID_HEADER = "X-User-Id";

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

  public void requireAnyRole(UserRole... allowedRoles) {
    UserRole currentRole = getCurrentUserRole();
    for (UserRole allowedRole : allowedRoles) {
      if (currentRole == allowedRole) {
        return;
      }
    }
    throw new ForbiddenException("Access denied for role " + currentRole);
  }

  public UUID getCurrentUserId() {
    HttpServletRequest request = currentRequest();
    String userId = request.getHeader(USER_ID_HEADER);
    if (userId == null || userId.isBlank()) {
      throw new ForbiddenException("No authenticated user");
    }
    try {
      return UUID.fromString(userId);
    } catch (IllegalArgumentException e) {
      throw new ForbiddenException("Invalid user id: " + userId);
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
