package com.example.demo.model;

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
public class Movie {

  private UUID idMovie;
  private String title;
  private Genre genre;
  private String description;
  private Duration duration;
}