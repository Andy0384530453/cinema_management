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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectionDetail {
  private UUID idProjection;
  private Instant datetime;
  private BigDecimal seatPrice;
  private MovieDetail movie;
  private RoomDetail room;
}
