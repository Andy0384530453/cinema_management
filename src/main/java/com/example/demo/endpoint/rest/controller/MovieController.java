package com.example.demo.endpoint.rest.controller;

import com.example.demo.dto.MovieDetail;
import com.example.demo.dto.MovieInput;
import com.example.demo.model.UserRole;
import com.example.demo.service.MovieService;
import com.example.demo.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;
  private final SecurityService securityService;

  @PutMapping("/movies")
  public MovieDetail saveMovie(@RequestBody MovieInput input) {
    securityService.requireRole(UserRole.MANAGER);
    return movieService.save(input);
  }
}
