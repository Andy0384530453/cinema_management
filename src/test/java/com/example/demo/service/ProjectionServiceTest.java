package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.ProjectionDetail;
import com.example.demo.dto.ProjectionInput;
import com.example.demo.entity.JMovie;
import com.example.demo.entity.JProjection;
import com.example.demo.entity.JRoom;
import com.example.demo.mapper.JProjectionMapper;
import com.example.demo.mapper.ProjectionMapper;
import com.example.demo.model.Movie;
import com.example.demo.model.Projection;
import com.example.demo.model.Room;
import com.example.demo.repository.ProjectionRepository;
import com.example.demo.validator.ProjectionValidator;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectionServiceTest {
  @Mock private ProjectionRepository projectionRepository;

  @Mock private ProjectionMapper projectionMapper;

  @Mock private JProjectionMapper jProjectionMapper;

  @Mock private ProjectionValidator projectionValidator;

  @InjectMocks private ProjectionService projectionService;

  private ProjectionInput validInput() {
    return ProjectionInput.builder()
        .idProjection(UUID.randomUUID())
        .datetime(Instant.parse("2026-08-10T20:00:00Z"))
        .seatPrice(new BigDecimal("12.50"))
        .idMovie(UUID.randomUUID())
        .idRoom(UUID.randomUUID())
        .build();
  }

  @Test
  void save_shouldAttachEntitiesAndReturnDetail() {
    ProjectionInput input = validInput();
    Projection domain =
        Projection.builder()
            .idProjection(input.getIdProjection())
            .datetime(input.getDatetime())
            .seatPrice(input.getSeatPrice())
            .movie(Movie.builder().idMovie(input.getIdMovie()).build())
            .room(Room.builder().idRoom(input.getIdRoom()).build())
            .build();
    JProjection jpa = JProjection.builder().idProjection(domain.getIdProjection()).build();
    JMovie movie = JMovie.builder().idMovie(input.getIdMovie()).build();
    JRoom room = JRoom.builder().idRoom(input.getIdRoom()).build();
    JProjection saved = JProjection.builder().idProjection(domain.getIdProjection()).build();
    Projection savedDomain = Projection.builder().idProjection(domain.getIdProjection()).build();
    ProjectionDetail detail =
        ProjectionDetail.builder().idProjection(domain.getIdProjection()).build();

    when(projectionMapper.toDomain(input)).thenReturn(domain);
    when(jProjectionMapper.toJpa(domain)).thenReturn(jpa);
    when(projectionValidator.validate(input))
        .thenReturn(new ProjectionValidator.ProjectionDeps(movie, room));
    when(projectionRepository.save(jpa)).thenReturn(saved);
    when(jProjectionMapper.toDomain(saved)).thenReturn(savedDomain);
    when(projectionMapper.toDetail(savedDomain)).thenReturn(detail);

    ProjectionDetail result = projectionService.save(input);

    assertEquals(detail, result);
    assertEquals(movie, jpa.getMovie());
    assertEquals(room, jpa.getRoom());
    verify(projectionRepository).save(jpa);
  }

  @Test
  void findAll_shouldReturnMappedProjections() {
    JProjection jpa = JProjection.builder().idProjection(UUID.randomUUID()).build();
    Projection domain = Projection.builder().idProjection(jpa.getIdProjection()).build();
    ProjectionDetail detail =
        ProjectionDetail.builder().idProjection(jpa.getIdProjection()).build();
    when(projectionRepository.findAll()).thenReturn(List.of(jpa));
    when(jProjectionMapper.toDomain(jpa)).thenReturn(domain);
    when(projectionMapper.toDetail(domain)).thenReturn(detail);

    List<ProjectionDetail> result = projectionService.findAll();

    assertEquals(List.of(detail), result);
  }

  @Test
  void findAll_withEmptyTable_shouldReturnEmptyList() {
    when(projectionRepository.findAll()).thenReturn(List.of());

    assertEquals(List.of(), projectionService.findAll());
  }
}
