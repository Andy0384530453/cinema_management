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
@Table(name = "seat")
public class JSeat {

  @Id private UUID idSeat;

  @Column(nullable = false)
  private String number;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_room")
  private JRoom room;
}
