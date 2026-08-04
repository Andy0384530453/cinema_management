package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.Instant;
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
public class ProjectionInput {
  private UUID idProjection;
  private Instant datetime;
  private BigDecimal seatPrice;
  private UUID idMovie;
  private UUID idRoom;
}
