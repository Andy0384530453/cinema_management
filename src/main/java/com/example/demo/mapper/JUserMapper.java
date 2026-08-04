package com.example.demo.mapper;

import com.example.demo.entity.JUser;
import com.example.demo.model.User;
import org.springframework.stereotype.Component;

@Component
public class JUserMapper {

  public JUser toJpa(User user) {
    if (user == null) {
      return null;
    }
    return JUser.builder()
        .idUser(user.getIdUser())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .birthdate(user.getBirthdate())
        .email(user.getEmail())
        .password(user.getPassword())
        .phone(user.getPhone())
        .role(user.getRole())
        .build();
  }

  public User toDomain(JUser jUser) {
    if (jUser == null) {
      return null;
    }
    return User.builder()
        .idUser(jUser.getIdUser())
        .firstName(jUser.getFirstName())
        .lastName(jUser.getLastName())
        .birthdate(jUser.getBirthdate())
        .email(jUser.getEmail())
        .password(jUser.getPassword())
        .phone(jUser.getPhone())
        .role(jUser.getRole())
        .build();
  }
}
