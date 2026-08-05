package com.example.demo.endpoint.rest.controller;

import com.example.demo.dto.ProjectionDetail;
import com.example.demo.dto.ProjectionInput;
import com.example.demo.model.UserRole;
import com.example.demo.service.ProjectionService;
import com.example.demo.service.SecurityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectionController {

  private final ProjectionService projectionService;
  private final SecurityService securityService;

  @PutMapping("/projection")
  public ProjectionDetail saveProjection(@RequestBody ProjectionInput input) {
    securityService.requireRole(UserRole.MANAGER);
    return projectionService.save(input);
  }

  @GetMapping("/projections")
  public List<ProjectionDetail> getProjections() {
    securityService.requireAnyRole(UserRole.CLIENT, UserRole.EMPLOYEE, UserRole.MANAGER);
    return projectionService.findAll();
  }
}
