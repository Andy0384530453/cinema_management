package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.dto.ReservationDetail;
import com.example.demo.dto.ReservationInput;
import com.example.demo.model.Genre;
import com.example.demo.model.Movie;
import com.example.demo.model.Projection;
import com.example.demo.model.Reservation;
import com.example.demo.model.Room;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ReservationMapperTest {
  private final MovieMapper movieMapper = new MovieMapper();
  private final RoomMapper roomMapper = new RoomMapper();
  private final ProjectionMapper projectionMapper = new ProjectionMapper(movieMapper, roomMapper);
  private final UserMapper userMapper = new UserMapper();
  private final ReservationMapper reservationMapper =
      new ReservationMapper(userMapper, projectionMapper);

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
    Movie movie =
        Movie.builder()
            .idMovie(UUID.randomUUID())
            .title("Inception")
            .genre(Genre.ACTION)
            .description("A thief who steals corporate secrets")
            .duration(Duration.ofHours(2).plusMinutes(28))
            .build();
    Room room = Room.builder().idRoom(UUID.randomUUID()).number("A1").capacity(50).build();
    Projection projection =
        Projection.builder()
            .idProjection(UUID.randomUUID())
            .datetime(Instant.parse("2026-08-10T20:00:00Z"))
            .seatPrice(new BigDecimal("12.50"))
            .movie(movie)
            .room(room)
            .build();
    Reservation reservation =
        Reservation.builder()
            .idReservation(UUID.randomUUID())
            .user(user)
            .projection(projection)
            .build();

    ReservationDetail detail = reservationMapper.toDetail(reservation);

    assertEquals(reservation.getIdReservation(), detail.getIdReservation());
    assertEquals(user.getIdUser(), detail.getUser().getIdUser());
    assertEquals("John", detail.getUser().getFirstName());
    assertEquals("john.doe@example.com", detail.getUser().getEmail());
    assertEquals(projection.getIdProjection(), detail.getProjection().getIdProjection());
    assertEquals(new BigDecimal("12.50"), detail.getProjection().getSeatPrice());
    assertEquals(movie.getIdMovie(), detail.getProjection().getMovie().getIdMovie());
    assertEquals("Inception", detail.getProjection().getMovie().getTitle());
    assertEquals(room.getIdRoom(), detail.getProjection().getRoom().getIdRoom());
    assertEquals("A1", detail.getProjection().getRoom().getNumber());
  }

  @Test
  void toDetail_shouldNotExposePassword() throws Exception {
    User user =
        User.builder()
            .idUser(UUID.randomUUID())
            .firstName("John")
            .lastName("Doe")
            .birthdate(LocalDate.of(2000, 1, 1))
            .email("john.doe@example.com")
            .password("top-secret")
            .phone("+261 34 00 000 00")
            .role(UserRole.CLIENT)
            .build();
    Reservation reservation =
        Reservation.builder().idReservation(UUID.randomUUID()).user(user).build();

    ReservationDetail detail = reservationMapper.toDetail(reservation);
    String json = new ObjectMapper().writeValueAsString(detail);

    assertFalse(json.contains("password"));
  }

  @Test
  void toDomain_shouldMapIdAndProjection() {
    UUID idProjection = UUID.randomUUID();
    ReservationInput input =
        ReservationInput.builder()
            .idReservation(UUID.randomUUID())
            .idProjection(idProjection)
            .build();

    Reservation reservation = reservationMapper.toDomain(input);

    assertEquals(input.getIdReservation(), reservation.getIdReservation());
    assertEquals(idProjection, reservation.getProjection().getIdProjection());
  }

  @Test
  void toDomain_withNullId_shouldGenerateUuid() {
    ReservationInput input = ReservationInput.builder().idProjection(UUID.randomUUID()).build();

    Reservation reservation = reservationMapper.toDomain(input);

    assertNotNull(reservation.getIdReservation());
  }

  @Test
  void toDomain_withNull_shouldReturnNull() {
    assertNull(reservationMapper.toDomain(null));
  }

  @Test
  void toDetail_withNull_shouldReturnNull() {
    assertNull(reservationMapper.toDetail(null));
  }
}
