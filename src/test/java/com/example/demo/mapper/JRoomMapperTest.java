package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.entity.JRoom;
import com.example.demo.entity.JSeat;
import com.example.demo.model.Room;
import com.example.demo.model.Seat;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JRoomMapperTest {
  private final JRoomMapper jRoomMapper = new JRoomMapper(new JSeatMapper());

  @Test
  void toJpa_shouldMapAllFields() {
    Seat seat = Seat.builder().idSeat(UUID.randomUUID()).number("A1-01").build();
    Room room =
        Room.builder()
            .idRoom(UUID.randomUUID())
            .number("A1")
            .capacity(50)
            .seats(Set.of(seat))
            .build();

    JRoom jRoom = jRoomMapper.toJpa(room);

    assertEquals(room.getIdRoom(), jRoom.getIdRoom());
    assertEquals("A1", jRoom.getNumber());
    assertEquals(50, jRoom.getCapacity());
    assertEquals(1, jRoom.getSeats().size());
    assertEquals("A1-01", jRoom.getSeats().iterator().next().getNumber());
  }

  @Test
  void toDomain_shouldMapAllFields() {
    JSeat jSeat = JSeat.builder().idSeat(UUID.randomUUID()).number("A1-01").build();
    JRoom jRoom =
        JRoom.builder()
            .idRoom(UUID.randomUUID())
            .number("A1")
            .capacity(50)
            .seats(Set.of(jSeat))
            .build();

    Room room = jRoomMapper.toDomain(jRoom);

    assertEquals(jRoom.getIdRoom(), room.getIdRoom());
    assertEquals("A1", room.getNumber());
    assertEquals(50, room.getCapacity());
    assertEquals(1, room.getSeats().size());
    assertEquals("A1-01", room.getSeats().iterator().next().getNumber());
  }

  @Test
  void toDomain_withoutSeats_shouldReturnEmptySeats() {
    JRoom jRoom = JRoom.builder().idRoom(UUID.randomUUID()).number("A1").capacity(50).build();

    Room room = jRoomMapper.toDomain(jRoom);

    assertEquals(Set.of(), room.getSeats());
  }

  @Test
  void withNull_shouldReturnNull() {
    assertNull(jRoomMapper.toJpa(null));
    assertNull(jRoomMapper.toDomain(null));
  }
}
