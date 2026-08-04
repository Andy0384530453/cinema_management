package com.example.demo.endpoint.rest.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.ProjectionDetail;
import com.example.demo.dto.ReservationDetail;
import com.example.demo.dto.RoomDetail;
import com.example.demo.dto.UserDetail;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Genre;
import com.example.demo.model.UserRole;
import com.example.demo.service.ReservationService;
import com.example.demo.service.SecurityService;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({ReservationController.class, GlobalExceptionHandler.class})
class ReservationControllerTest {
  private ReservationDetail reservationDetail;

  @Autowired private MockMvc mockMvc;

  @MockBean private ReservationService reservationService;

  @MockBean private SecurityService securityService;

  @BeforeEach
  void setUp() {
    reservationDetail =
        ReservationDetail.builder()
            .idReservation(UUID.randomUUID())
            .user(
                UserDetail.builder()
                    .idUser(UUID.randomUUID())
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@example.com")
                    .build())
            .projection(
                ProjectionDetail.builder()
                    .idProjection(UUID.randomUUID())
                    .datetime(Instant.parse("2026-08-10T20:00:00Z"))
                    .seatPrice(new BigDecimal("12.50"))
                    .movie(
                        MovieDetail.builder()
                            .idMovie(UUID.randomUUID())
                            .title("Inception")
                            .genre(Genre.ACTION)
                            .description("A thief who steals corporate secrets")
                            .duration(Duration.ofHours(2).plusMinutes(28))
                            .build())
                    .room(
                        RoomDetail.builder()
                            .idRoom(UUID.randomUUID())
                            .number("A1")
                            .capacity(50)
                            .build())
                    .build())
            .build();
  }

  @Test
  void getReservations_withEmployeeRole_shouldReturn200() throws Exception {
    when(reservationService.findAll()).thenReturn(List.of(reservationDetail));

    mockMvc
        .perform(get("/reservations"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(
            jsonPath("$[0].idReservation").value(reservationDetail.getIdReservation().toString()))
        .andExpect(jsonPath("$[0].user.firstName").value("John"))
        .andExpect(jsonPath("$[0].user.email").value("john.doe@example.com"))
        .andExpect(jsonPath("$[0].projection.seatPrice").value(12.5))
        .andExpect(jsonPath("$[0].projection.movie.title").value("Inception"))
        .andExpect(jsonPath("$[0].projection.room.number").value("A1"));
  }

  @Test
  void getReservations_withManagerRole_shouldReturn200() throws Exception {
    when(reservationService.findAll()).thenReturn(List.of(reservationDetail));

    mockMvc.perform(get("/reservations")).andExpect(status().isOk());
  }

  @Test
  void getReservations_withClientRole_shouldThrow403() throws Exception {
    doThrow(new ForbiddenException("Access denied for role CLIENT"))
        .when(securityService)
        .requireAnyRole(UserRole.EMPLOYEE, UserRole.MANAGER);

    mockMvc.perform(get("/reservations")).andExpect(status().isForbidden());
  }

  @Test
  void getReservations_withoutRole_shouldThrow403() throws Exception {
    doThrow(new ForbiddenException("No authenticated user"))
        .when(securityService)
        .requireAnyRole(UserRole.EMPLOYEE, UserRole.MANAGER);

    mockMvc.perform(get("/reservations")).andExpect(status().isForbidden());
  }

  @Test
  void getReservations_whenServiceFails_shouldThrow500() throws Exception {
    when(reservationService.findAll()).thenThrow(new RuntimeException("boom"));

    mockMvc.perform(get("/reservations")).andExpect(status().isInternalServerError());
  }

  @Test
  void getReservationById_withManagerRole_shouldReturn200() throws Exception {
    when(reservationService.findById(reservationDetail.getIdReservation()))
        .thenReturn(reservationDetail);
    when(securityService.getCurrentUserRole()).thenReturn(UserRole.MANAGER);

    mockMvc
        .perform(
            get("/reservationById")
                .param("idReservation", reservationDetail.getIdReservation().toString()))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.idReservation").value(reservationDetail.getIdReservation().toString()))
        .andExpect(jsonPath("$.user.firstName").value("John"))
        .andExpect(jsonPath("$.projection.movie.title").value("Inception"));
  }

  @Test
  void getReservationById_withEmployeeRole_shouldReturn200() throws Exception {
    when(reservationService.findById(reservationDetail.getIdReservation()))
        .thenReturn(reservationDetail);
    when(securityService.getCurrentUserRole()).thenReturn(UserRole.EMPLOYEE);

    mockMvc
        .perform(
            get("/reservationById")
                .param("idReservation", reservationDetail.getIdReservation().toString()))
        .andExpect(status().isOk());
  }

  @Test
  void getReservationById_withOwnerClient_shouldReturn200() throws Exception {
    when(reservationService.findById(reservationDetail.getIdReservation()))
        .thenReturn(reservationDetail);
    when(securityService.getCurrentUserRole()).thenReturn(UserRole.CLIENT);
    when(securityService.getCurrentUserId()).thenReturn(reservationDetail.getUser().getIdUser());

    mockMvc
        .perform(
            get("/reservationById")
                .param("idReservation", reservationDetail.getIdReservation().toString()))
        .andExpect(status().isOk());
  }

  @Test
  void getReservationById_withOtherClient_shouldThrow403() throws Exception {
    when(reservationService.findById(reservationDetail.getIdReservation()))
        .thenReturn(reservationDetail);
    when(securityService.getCurrentUserRole()).thenReturn(UserRole.CLIENT);
    when(securityService.getCurrentUserId()).thenReturn(UUID.randomUUID());

    mockMvc
        .perform(
            get("/reservationById")
                .param("idReservation", reservationDetail.getIdReservation().toString()))
        .andExpect(status().isForbidden());
  }

  @Test
  void getReservationById_withNonExistingReservation_shouldThrow404() throws Exception {
    UUID id = UUID.randomUUID();
    when(reservationService.findById(id))
        .thenThrow(new NotFoundException("Reservation with id " + id + " not found"));

    mockMvc
        .perform(get("/reservationById").param("idReservation", id.toString()))
        .andExpect(status().isNotFound());
  }

  @Test
  void getReservationById_withInvalidUUID_shouldThrow400() throws Exception {
    mockMvc
        .perform(get("/reservationById").param("idReservation", "1"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getReservationById_whenServiceFails_shouldThrow500() throws Exception {
    when(reservationService.findById(reservationDetail.getIdReservation()))
        .thenThrow(new RuntimeException("boom"));

    mockMvc
        .perform(
            get("/reservationById")
                .param("idReservation", reservationDetail.getIdReservation().toString()))
        .andExpect(status().isInternalServerError());
  }
}
