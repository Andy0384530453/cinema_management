package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservation")
public class Reservation {

  @Id private UUID idReservation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_projection")
  private Projection projection;
}
