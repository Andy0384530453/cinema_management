package com.example.demo.model;

import java.time.LocalDate;
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
public class User {

  private UUID idUser;
  private String firstName;
  private String lastName;
  private LocalDate birthdate;
  private String email;
  private String password;
  private String phone;
  private UserRole role;
}
