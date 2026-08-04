package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class SeatTest {

  @Test
  void builderAndSetters_shouldExposeFields() {
    UUID id = UUID.randomUUID();
    Room room = Room.builder().idRoom(UUID.randomUUID()).number("A1").capacity(50).build();
    Seat seat = Seat.builder().idSeat(id).number("A1-01").room(room).build();

    assertNotNull(new Seat());
    assertEquals(id, seat.getIdSeat());
    assertEquals("A1-01", seat.getNumber());
    assertEquals(room, seat.getRoom());

    seat.setNumber("A1-02");
    assertEquals("A1-02", seat.getNumber());
  }

  @Test
  void builderWithoutRoom_shouldLeaveRoomNull() {
    Seat seat = Seat.builder().idSeat(UUID.randomUUID()).number("A1-01").build();

    assertNull(seat.getRoom());
  }
}
