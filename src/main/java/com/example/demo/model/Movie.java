package com.example.demo.model;

import com.example.demo.entity.enums.Genre;
import java.time.Duration;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
  private UUID idMovie;
  private String title;
  private Genre genre;
  private String description;
  private Duration duration;
}
