package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "projection")
public class JProjection {

  @Id private UUID idProjection;

  @Column(nullable = false)
  private Instant datetime;

  @Column(nullable = false)
  private BigDecimal seatPrice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_movie")
  private JMovie movie;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_room")
  private JRoom room;
}