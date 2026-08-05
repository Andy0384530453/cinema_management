package com.example.demo.validator;

import com.example.demo.dto.ProjectionInput;
import com.example.demo.entity.JMovie;
import com.example.demo.entity.JRoom;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectionValidator {

  private final MovieRepository movieRepository;
  private final RoomRepository roomRepository;

  public ProjectionDeps validate(ProjectionInput input) {
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
    JMovie movie =
        movieRepository
            .findById(input.getIdMovie())
            .orElseThrow(
                () -> new NotFoundException("Movie with id " + input.getIdMovie() + " not found"));
    JRoom room =
        roomRepository
            .findById(input.getIdRoom())
            .orElseThrow(
                () -> new NotFoundException("Room with id " + input.getIdRoom() + " not found"));
    return new ProjectionDeps(movie, room);
  }

  public record ProjectionDeps(JMovie movie, JRoom room) {}
}
