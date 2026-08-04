package com.example.demo.endpoint.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.ProjectionDetail;
import com.example.demo.dto.ProjectionInput;
import com.example.demo.dto.RoomDetail;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Genre;
import com.example.demo.model.UserRole;
import com.example.demo.service.ProjectionService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({ProjectionController.class, GlobalExceptionHandler.class})
class ProjectionControllerTest {
  private ProjectionDetail projectionDetail;

  @Autowired private MockMvc mockMvc;

  @MockBean private ProjectionService projectionService;

  @MockBean private SecurityService securityService;

  @BeforeEach
  void setUp() {
    projectionDetail =
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
            .room(RoomDetail.builder().idRoom(UUID.randomUUID()).number("A1").capacity(50).build())
            .build();
  }

  @Test
  void putProjection_withManagerRole_shouldReturn200() throws Exception {
    when(projectionService.save(any(ProjectionInput.class))).thenReturn(projectionDetail);

    mockMvc
        .perform(
            put("/projection").contentType(MediaType.APPLICATION_JSON).content(projectionJson()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.idProjection").value(projectionDetail.getIdProjection().toString()))
        .andExpect(jsonPath("$.datetime").value("2026-08-10T20:00:00Z"))
        .andExpect(jsonPath("$.seatPrice").value(12.5))
        .andExpect(jsonPath("$.movie.title").value("Inception"))
        .andExpect(jsonPath("$.room.number").value("A1"));
  }

  @Test
  void putProjection_withClientRole_shouldThrow403() throws Exception {
    doThrow(new ForbiddenException("Access denied for role CLIENT"))
        .when(securityService)
        .requireRole(UserRole.MANAGER);

    mockMvc
        .perform(
            put("/projection").contentType(MediaType.APPLICATION_JSON).content(projectionJson()))
        .andExpect(status().isForbidden());
  }

  @Test
  void putProjection_withoutRole_shouldThrow403() throws Exception {
    doThrow(new ForbiddenException("No authenticated user"))
        .when(securityService)
        .requireRole(UserRole.MANAGER);

    mockMvc
        .perform(
            put("/projection").contentType(MediaType.APPLICATION_JSON).content(projectionJson()))
        .andExpect(status().isForbidden());
  }

  @Test
  void putProjection_withMissingMovie_shouldThrow404() throws Exception {
    when(projectionService.save(any(ProjectionInput.class)))
        .thenThrow(new NotFoundException("Movie with id 1 not found"));

    mockMvc
        .perform(
            put("/projection").contentType(MediaType.APPLICATION_JSON).content(projectionJson()))
        .andExpect(status().isNotFound());
  }

  @Test
  void putProjection_withInvalidInput_shouldThrow400() throws Exception {
    when(projectionService.save(any(ProjectionInput.class)))
        .thenThrow(new BadRequestException("Datetime is required"));

    mockMvc
        .perform(
            put("/projection").contentType(MediaType.APPLICATION_JSON).content(projectionJson()))
        .andExpect(status().isBadRequest());
  }

  @Test
  void putProjection_whenServiceFails_shouldThrow500() throws Exception {
    when(projectionService.save(any(ProjectionInput.class)))
        .thenThrow(new RuntimeException("boom"));

    mockMvc
        .perform(
            put("/projection").contentType(MediaType.APPLICATION_JSON).content(projectionJson()))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void getProjections_withManagerRole_shouldReturn200() throws Exception {
    when(projectionService.findAll()).thenReturn(List.of(projectionDetail));

    mockMvc
        .perform(get("/projections"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(
            jsonPath("$[0].idProjection").value(projectionDetail.getIdProjection().toString()))
        .andExpect(jsonPath("$[0].seatPrice").value(12.5))
        .andExpect(jsonPath("$[0].movie.title").value("Inception"))
        .andExpect(jsonPath("$[0].room.number").value("A1"));
  }

  @Test
  void getProjections_withEmployeeRole_shouldReturn200() throws Exception {
    when(projectionService.findAll()).thenReturn(List.of(projectionDetail));

    mockMvc.perform(get("/projections")).andExpect(status().isOk());
  }

  @Test
  void getProjections_withoutRole_shouldThrow403() throws Exception {
    doThrow(new ForbiddenException("No authenticated user"))
        .when(securityService)
        .requireAnyRole(UserRole.CLIENT, UserRole.EMPLOYEE, UserRole.MANAGER);

    mockMvc.perform(get("/projections")).andExpect(status().isForbidden());
  }

  @Test
  void getProjections_whenServiceFails_shouldThrow500() throws Exception {
    when(projectionService.findAll()).thenThrow(new RuntimeException("boom"));

    mockMvc.perform(get("/projections")).andExpect(status().isInternalServerError());
  }

  @Test
  void getProjections_withEmptyList_shouldReturn200() throws Exception {
    when(projectionService.findAll()).thenReturn(List.of());

    mockMvc.perform(get("/projections")).andExpect(status().isOk());
  }

  private String projectionJson() {
    return "{\"idProjection\":\""
        + projectionDetail.getIdProjection()
        + "\",\"datetime\":\"2026-08-10T20:00:00Z\",\"seatPrice\":12.5"
        + ",\"idMovie\":\""
        + projectionDetail.getMovie().getIdMovie()
        + "\",\"idRoom\":\""
        + projectionDetail.getRoom().getIdRoom()
        + "\"}";
  }
}
