package com.example.demo.endpoint.rest.controller;

import com.example.demo.dto.ReservationDetail;
import com.example.demo.dto.ReservationInput;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.UserRole;
import com.example.demo.service.ReservationService;
import com.example.demo.service.SecurityService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;
  private final SecurityService securityService;

  @PutMapping("/reservation")
  public ResponseEntity<ReservationDetail> saveReservation(@RequestBody ReservationInput input) {
    try {
      securityService.requireAnyRole(UserRole.CLIENT, UserRole.EMPLOYEE, UserRole.MANAGER);
      return ResponseEntity.ok(reservationService.save(input, securityService.getCurrentUserId()));
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

  @GetMapping("/reservationById")
  public ResponseEntity<ReservationDetail> getReservationById(@RequestParam UUID idReservation) {
    try {
      ReservationDetail detail = reservationService.findById(idReservation);
      UserRole role = securityService.getCurrentUserRole();
      if (role == UserRole.CLIENT && !isOwner(detail, securityService.getCurrentUserId())) {
        throw new ForbiddenException("Access denied: not the owner of this reservation");
      }
      return ResponseEntity.ok(detail);
    } catch (ForbiddenException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  private boolean isOwner(ReservationDetail detail, UUID currentUserId) {
    return detail.getUser() != null && currentUserId.equals(detail.getUser().getIdUser());
  }
}
