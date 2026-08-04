package com.example.demo.entity;

import com.example.demo.model.UserRole;
import jakarta.persistence.*;
import java.time.LocalDate;
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
@Table(name = "app_user")
public class JUser {

  @Id private UUID idUser;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private LocalDate birthdate;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  private String phone;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;
}