package com.example.demo.dto;

import com.example.demo.entity.enums.Genre;
import java.time.Duration;
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
public class MovieInput {
  private UUID idMovie;
  private String title;
  private Genre genre;
  private String description;
  private Duration duration;
}
