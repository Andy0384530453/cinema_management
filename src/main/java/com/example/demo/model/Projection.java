package com.example.demo.model;

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
public class Projection {

  private UUID idProjection;
  private Instant datetime;
  private BigDecimal seatPrice;
  private Movie movie;
  private Room room;
}