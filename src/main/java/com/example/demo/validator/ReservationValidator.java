package com.example.demo.validator;

import com.example.demo.dto.ReservationInput;
import com.example.demo.entity.JProjection;
import com.example.demo.entity.JSeat;
import com.example.demo.entity.JUser;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ProjectionRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationValidator {

  private final UserRepository userRepository;
  private final ProjectionRepository projectionRepository;
  private final SeatRepository seatRepository;

  public ReservationDeps validate(ReservationInput input, UUID userId) {
    if (input.getIdProjection() == null) {
      throw new BadRequestException("Projection is required");
    }
    JUser user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    JProjection projection =
        projectionRepository
            .findById(input.getIdProjection())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        "Projection with id " + input.getIdProjection() + " not found"));
    Set<JSeat> seats =
        input.getSeatIds() == null
            ? new HashSet<>()
            : input.getSeatIds().stream()
                .map(
                    seatId ->
                        seatRepository
                            .findById(seatId)
                            .orElseThrow(
                                () ->
                                    new NotFoundException("Seat with id " + seatId + " not found")))
                .collect(Collectors.toSet());
    return new ReservationDeps(user, projection, seats);
  }

  public record ReservationDeps(JUser user, JProjection projection, Set<JSeat> seats) {}
}
