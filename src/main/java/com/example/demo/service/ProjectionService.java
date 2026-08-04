package com.example.demo.service;

import com.example.demo.dto.ProjectionDetail;
import com.example.demo.dto.ProjectionInput;
import com.example.demo.entity.JProjection;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.JProjectionMapper;
import com.example.demo.mapper.ProjectionMapper;
import com.example.demo.model.Projection;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ProjectionRepository;
import com.example.demo.repository.RoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectionService {

  private final ProjectionRepository projectionRepository;
  private final MovieRepository movieRepository;
  private final RoomRepository roomRepository;
  private final ProjectionMapper projectionMapper;
  private final JProjectionMapper jProjectionMapper;

  public ProjectionDetail save(ProjectionInput input) {
    validate(input);
    Projection projection = projectionMapper.toDomain(input);
    JProjection jProjection = jProjectionMapper.toJpa(projection);
    jProjection.setMovie(
        movieRepository
            .findById(projection.getMovie().getIdMovie())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "Movie with id " + projection.getMovie().getIdMovie() + " not found")));
    jProjection.setRoom(
        roomRepository
            .findById(projection.getRoom().getIdRoom())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "Room with id " + projection.getRoom().getIdRoom() + " not found")));
    return projectionMapper.toDetail(
        jProjectionMapper.toDomain(projectionRepository.save(jProjection)));
  }

  public List<ProjectionDetail> findAll() {
    return projectionRepository.findAll().stream()
        .map(jProjectionMapper::toDomain)
        .map(projectionMapper::toDetail)
        .toList();
  }

  private void validate(ProjectionInput input) {
    if (input.getDatetime() == null) {
      throw new BadRequestException("Datetime is required");
    }
    if (input.getSeatPrice() == null) {
      throw new BadRequestException("Seat price is required");
    }
    if (input.getIdMovie() == null) {
      throw new BadRequestException("Movie is required");
    }
    if (input.getIdRoom() == null) {
      throw new BadRequestException("Room is required");
    }
  }
}
