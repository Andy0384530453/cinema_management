package com.example.demo.endpoint.rest.controller;

import com.example.demo.dto.ProjectionDetail;
import com.example.demo.dto.ProjectionInput;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.UserRole;
import com.example.demo.service.ProjectionService;
import com.example.demo.service.SecurityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<ProjectionDetail> saveProjection(@RequestBody ProjectionInput input) {
    try {
      securityService.requireRole(UserRole.MANAGER);
      return ResponseEntity.ok(projectionService.save(input));
    } catch (ForbiddenException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } catch (BadRequestException e) {
      return ResponseEntity.badRequest().build();
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/projections")
  public ResponseEntity<List<ProjectionDetail>> getProjections() {
    try {
      securityService.requireAnyRole(UserRole.CLIENT, UserRole.EMPLOYEE, UserRole.MANAGER);
      return ResponseEntity.ok(projectionService.findAll());
    } catch (ForbiddenException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
