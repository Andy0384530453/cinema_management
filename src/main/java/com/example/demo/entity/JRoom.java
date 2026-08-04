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
@Table(name = "room")
public class JRoom {

  @Id private UUID idRoom;

  @Column(nullable = false)
  private String number;

  @Column(nullable = false)
  private Integer capacity;
}