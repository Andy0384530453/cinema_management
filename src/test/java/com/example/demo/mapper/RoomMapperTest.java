package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.dto.RoomDetail;
import com.example.demo.model.Room;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class RoomMapperTest {
  private final RoomMapper roomMapper = new RoomMapper();

  @Test
  void toDetail_shouldMapAllFields() {
    Room room = Room.builder().idRoom(UUID.randomUUID()).number("A1").capacity(50).build();

    RoomDetail detail = roomMapper.toDetail(room);

    assertEquals(room.getIdRoom(), detail.getIdRoom());
    assertEquals("A1", detail.getNumber());
    assertEquals(50, detail.getCapacity());
  }

  @Test
  void toDetail_withNull_shouldReturnNull() {
    assertNull(roomMapper.toDetail(null));
  }
}
