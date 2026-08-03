package com.example.demo.entity;

import com.example.demo.entity.enums.Genre;
import jakarta.persistence.*;
import java.time.Duration;
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
@Table(name = "movie")
public class JMovie {

  @Id private UUID idMovie;

  @Column(nullable = false)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Genre genre;

  private String description;

  @Column(nullable = false)
  private Duration duration;
}
