package com.example.demo.entity;

import com.example.demo.model.Room;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "seat")
public class Seat {

  @Id private UUID idSeat;

  @Column(nullable = false)
  private String number;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_room")
  private Room room;
}
