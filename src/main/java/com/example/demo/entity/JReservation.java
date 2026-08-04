package com.example.demo.entity;

import jakarta.persistence.*;
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
@Table(name = "reservation")
public class JReservation {

  @Id private UUID idReservation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user")
  private JUser user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_projection")
  private JProjection projection;
}
