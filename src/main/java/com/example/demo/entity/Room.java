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
@Table(name = "room")
public class Room {

  @Id private UUID idRoom;

  @Column(nullable = false)
  private String number;

  @Column(nullable = false)
  private Integer capacity;
}
