package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.entity.JProjection;
import com.example.demo.entity.JReservation;
import com.example.demo.entity.JUser;
import com.example.demo.model.Projection;
import com.example.demo.model.Reservation;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JReservationMapperTest {
  private final JMovieMapper movieMapper = new JMovieMapper();
  private final JRoomMapper roomMapper = new JRoomMapper();
  private final JProjectionMapper projectionMapper = new JProjectionMapper(movieMapper, roomMapper);
  private final JUserMapper userMapper = new JUserMapper();
  private final JReservationMapper jReservationMapper =
      new JReservationMapper(userMapper, projectionMapper);

  @Test
  void toJpa_shouldMapAllFields() {
    User user =
        User.builder()
            .idUser(UUID.randomUUID())
            .email("john.doe@example.com")
            .role(UserRole.CLIENT)
            .build();
    Projection projection = Projection.builder().idProjection(UUID.randomUUID()).build();
    Reservation reservation =
        Reservation.builder()
            .idReservation(UUID.randomUUID())
            .user(user)
            .projection(projection)
            .build();

    JReservation jReservation = jReservationMapper.toJpa(reservation);

    assertEquals(reservation.getIdReservation(), jReservation.getIdReservation());
    assertEquals(user.getIdUser(), jReservation.getUser().getIdUser());
    assertEquals("john.doe@example.com", jReservation.getUser().getEmail());
    assertEquals(projection.getIdProjection(), jReservation.getProjection().getIdProjection());
  }

  @Test
  void toDomain_shouldMapAllFields() {
    JUser jUser =
        JUser.builder()
            .idUser(UUID.randomUUID())
            .email("john.doe@example.com")
            .role(UserRole.CLIENT)
            .build();
    JProjection jProjection = JProjection.builder().idProjection(UUID.randomUUID()).build();
    JReservation jReservation =
        JReservation.builder()
            .idReservation(UUID.randomUUID())
            .user(jUser)
            .projection(jProjection)
            .build();

    Reservation reservation = jReservationMapper.toDomain(jReservation);

    assertEquals(jReservation.getIdReservation(), reservation.getIdReservation());
    assertEquals(jUser.getIdUser(), reservation.getUser().getIdUser());
    assertEquals("john.doe@example.com", reservation.getUser().getEmail());
    assertEquals(jProjection.getIdProjection(), reservation.getProjection().getIdProjection());
  }

  @Test
  void withNull_shouldReturnNull() {
    assertNull(jReservationMapper.toJpa(null));
    assertNull(jReservationMapper.toDomain(null));
  }
}
