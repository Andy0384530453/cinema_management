package com.example.demo.mapper;

import com.example.demo.dto.UserDetail;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDetail toDetail(User user) {
    if (user == null) {
      return null;
    }
    return UserDetail.builder()
        .idUser(user.getIdUser())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .build();
  }
}
