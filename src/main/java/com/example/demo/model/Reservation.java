package com.example.demo.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
  private UUID idReservation;
  private Instant createdAt;
  private User user;
  private Projection projection;
  @Builder.Default private Set<Seat> seats = new HashSet<>();
}
