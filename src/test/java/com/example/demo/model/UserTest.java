package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void builderAndSetters_shouldExposeFields() {
    UUID id = UUID.randomUUID();
    User user =
        User.builder()
            .idUser(id)
            .firstName("John")
            .lastName("Doe")
            .birthdate(LocalDate.of(2000, 1, 1))
            .email("john.doe@example.com")
            .password("secret")
            .phone("+261 34 00 000 00")
            .role(UserRole.CLIENT)
            .build();

    assertNotNull(new User());
    assertEquals(id, user.getIdUser());
    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
    assertEquals(LocalDate.of(2000, 1, 1), user.getBirthdate());
    assertEquals("john.doe@example.com", user.getEmail());
    assertEquals("secret", user.getPassword());
    assertEquals("+261 34 00 000 00", user.getPhone());
    assertEquals(UserRole.CLIENT, user.getRole());

    user.setRole(UserRole.MANAGER);
    assertEquals(UserRole.MANAGER, user.getRole());
  }
}