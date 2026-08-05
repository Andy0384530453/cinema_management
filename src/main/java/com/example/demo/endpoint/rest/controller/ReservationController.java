package com.example.demo.endpoint.rest.controller;

import com.example.demo.dto.ReservationDetail;
import com.example.demo.dto.ReservationInput;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.model.UserRole;
import com.example.demo.service.ReservationService;
import com.example.demo.service.SecurityService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
  public ReservationDetail saveReservation(@RequestBody ReservationInput input) {
    securityService.requireAnyRole(UserRole.CLIENT, UserRole.EMPLOYEE, UserRole.MANAGER);
    return reservationService.save(input, securityService.getCurrentUserId());
  }

  @GetMapping("/reservations")
  public List<ReservationDetail> getReservations() {
    securityService.requireAnyRole(UserRole.EMPLOYEE, UserRole.MANAGER);
    return reservationService.findAll();
  }

  @GetMapping("/reservationById")
  public ReservationDetail getReservationById(@RequestParam UUID idReservation) {
    ReservationDetail detail = reservationService.findById(idReservation);
    UserRole role = securityService.getCurrentUserRole();
    if (role == UserRole.CLIENT && !isOwner(detail, securityService.getCurrentUserId())) {
      throw new ForbiddenException("Access denied: not the owner of this reservation");
    }
    return detail;
  }

  private boolean isOwner(ReservationDetail detail, UUID currentUserId) {
    return detail.getUser() != null && currentUserId.equals(detail.getUser().getIdUser());
  }
}
