package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.entity.JRoom;
import com.example.demo.model.Room;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JRoomMapperTest {
  private final JRoomMapper jRoomMapper = new JRoomMapper();

  @Test
  void toJpa_shouldMapAllFields() {
    Room room = Room.builder().idRoom(UUID.randomUUID()).number("A1").capacity(50).build();

    JRoom jRoom = jRoomMapper.toJpa(room);

    assertEquals(room.getIdRoom(), jRoom.getIdRoom());
    assertEquals("A1", jRoom.getNumber());
    assertEquals(50, jRoom.getCapacity());
  }

  @Test
  void toDomain_shouldMapAllFields() {
    JRoom jRoom = JRoom.builder().idRoom(UUID.randomUUID()).number("A1").capacity(50).build();

    Room room = jRoomMapper.toDomain(jRoom);

    assertEquals(jRoom.getIdRoom(), room.getIdRoom());
    assertEquals("A1", room.getNumber());
    assertEquals(50, room.getCapacity());
  }

  @Test
  void withNull_shouldReturnNull() {
    assertNull(jRoomMapper.toJpa(null));
    assertNull(jRoomMapper.toDomain(null));
  }
}
