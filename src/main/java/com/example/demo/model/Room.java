package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
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
public class Room {
  private UUID idRoom;
  private String number;
  private Integer capacity;
  @Builder.Default private List<Seat> seats = new ArrayList<>();
}
