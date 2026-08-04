package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.dto.UserDetail;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserMapperTest {
  private final UserMapper userMapper = new UserMapper();

  @Test
  void toDetail_shouldMapAllFields() {
    User user =
        User.builder()
            .idUser(UUID.randomUUID())
            .firstName("John")
            .lastName("Doe")
            .birthdate(LocalDate.of(2000, 1, 1))
            .email("john.doe@example.com")
            .password("secret")
            .phone("+261 34 00 000 00")
            .role(UserRole.CLIENT)
            .build();

    UserDetail detail = userMapper.toDetail(user);

    assertEquals(user.getIdUser(), detail.getIdUser());
    assertEquals("John", detail.getFirstName());
    assertEquals("Doe", detail.getLastName());
    assertEquals("john.doe@example.com", detail.getEmail());
  }

  @Test
  void toDetail_withNull_shouldReturnNull() {
    assertNull(userMapper.toDetail(null));
  }
}
