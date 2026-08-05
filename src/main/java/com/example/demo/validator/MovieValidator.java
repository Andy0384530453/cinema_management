package com.example.demo.validator;

import com.example.demo.dto.MovieInput;
import com.example.demo.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieValidator {

  public void validate(MovieInput input) {
    if (input.getTitle() == null || input.getTitle().isBlank()) {
      throw new BadRequestException("Title is required");
    }
    if (input.getGenre() == null) {
      throw new BadRequestException("Genre is required");
    }
    if (input.getDuration() == null) {
      throw new BadRequestException("Duration is required");
    }
  }
}
