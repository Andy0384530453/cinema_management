package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class RoomTest {

  @Test
  void builderAndSetters_shouldExposeFields() {
    UUID id = UUID.randomUUID();
    Room room = Room.builder().idRoom(id).number("A1").capacity(50).build();

    assertNotNull(new Room());
    assertEquals(id, room.getIdRoom());
    assertEquals("A1", room.getNumber());
    assertEquals(50, room.getCapacity());

    room.setNumber("B2");
    room.setCapacity(100);
    assertEquals("B2", room.getNumber());
    assertEquals(100, room.getCapacity());
  }
}