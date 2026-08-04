package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.entity.JUser;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JUserMapperTest {
  private final JUserMapper jUserMapper = new JUserMapper();

  @Test
  void toJpa_shouldMapAllFields() {
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

    JUser jUser = jUserMapper.toJpa(user);

    assertEquals(user.getIdUser(), jUser.getIdUser());
    assertEquals("John", jUser.getFirstName());
    assertEquals("Doe", jUser.getLastName());
    assertEquals(LocalDate.of(2000, 1, 1), jUser.getBirthdate());
    assertEquals("john.doe@example.com", jUser.getEmail());
    assertEquals("secret", jUser.getPassword());
    assertEquals("+261 34 00 000 00", jUser.getPhone());
    assertEquals(UserRole.CLIENT, jUser.getRole());
  }

  @Test
  void toDomain_shouldMapAllFields() {
    JUser jUser =
        JUser.builder()
            .idUser(UUID.randomUUID())
            .firstName("John")
            .lastName("Doe")
            .birthdate(LocalDate.of(2000, 1, 1))
            .email("john.doe@example.com")
            .password("secret")
            .phone("+261 34 00 000 00")
            .role(UserRole.CLIENT)
            .build();

    User user = jUserMapper.toDomain(jUser);

    assertEquals(jUser.getIdUser(), user.getIdUser());
    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
    assertEquals(LocalDate.of(2000, 1, 1), user.getBirthdate());
    assertEquals("john.doe@example.com", user.getEmail());
    assertEquals("secret", user.getPassword());
    assertEquals("+261 34 00 000 00", user.getPhone());
    assertEquals(UserRole.CLIENT, user.getRole());
  }

  @Test
  void withNull_shouldReturnNull() {
    assertNull(jUserMapper.toJpa(null));
    assertNull(jUserMapper.toDomain(null));
  }
}
