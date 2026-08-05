package com.example.demo.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.demo.dto.MovieInput;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.Genre;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieValidatorTest {

  @InjectMocks private MovieValidator movieValidator;

  private MovieInput validInput() {
    return MovieInput.builder()
        .idMovie(UUID.randomUUID())
        .title("Inception")
        .genre(Genre.ACTION)
        .description("A thief who steals corporate secrets")
        .duration(Duration.ofHours(2).plusMinutes(28))
        .build();
  }

  @Test
  void validate_withValidInput_shouldNotThrow() {
    assertDoesNotThrow(() -> movieValidator.validate(validInput()));
  }

  @Test
  void validate_withNullTitle_shouldThrow400() {
    MovieInput input = validInput();
    input.setTitle(null);

    assertThrows(BadRequestException.class, () -> movieValidator.validate(input));
  }

  @Test
  void validate_withBlankTitle_shouldThrow400() {
    MovieInput input = validInput();
    input.setTitle("   ");

    assertThrows(BadRequestException.class, () -> movieValidator.validate(input));
  }

  @Test
  void validate_withNullGenre_shouldThrow400() {
    MovieInput input = validInput();
    input.setGenre(null);

    assertThrows(BadRequestException.class, () -> movieValidator.validate(input));
  }

  @Test
  void validate_withNullDuration_shouldThrow400() {
    MovieInput input = validInput();
    input.setDuration(null);

    assertThrows(BadRequestException.class, () -> movieValidator.validate(input));
  }
}
