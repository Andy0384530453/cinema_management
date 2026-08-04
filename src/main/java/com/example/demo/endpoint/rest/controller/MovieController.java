package com.example.demo.endpoint.rest.controller;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.MovieInput;
import com.example.demo.model.UserRole;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.service.MovieService;
import com.example.demo.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;
  private final SecurityService securityService;

  @PutMapping("/movies")
  public ResponseEntity<MovieDetail> saveMovie(@RequestBody MovieInput input) {
    try {
      securityService.requireRole(UserRole.MANAGER);
      MovieDetail movie = movieService.save(input);
      return ResponseEntity.ok(movie);
    } catch (ForbiddenException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } catch (BadRequestException e) {
      return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
