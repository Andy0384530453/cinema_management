package com.example.demo.endpoint.rest.controller;

import com.example.demo.dto.ReservationDetail;
import com.example.demo.entity.enums.UserRole;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.service.ReservationService;
import com.example.demo.service.SecurityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;
  private final SecurityService securityService;

  @GetMapping("/reservations")
  public ResponseEntity<List<ReservationDetail>> getReservations() {
    try {
      securityService.requireAnyRole(UserRole.EMPLOYEE, UserRole.MANAGER);
      return ResponseEntity.ok(reservationService.findAll());
    } catch (ForbiddenException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
