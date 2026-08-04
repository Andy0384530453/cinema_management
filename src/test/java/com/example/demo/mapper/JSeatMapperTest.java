package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.demo.entity.JSeat;
import com.example.demo.model.Seat;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JSeatMapperTest {
  private final JSeatMapper jSeatMapper = new JSeatMapper();

  @Test
  void toJpa_shouldMapAllFields() {
    Seat seat = Seat.builder().idSeat(UUID.randomUUID()).number("A1-01").build();

    JSeat jSeat = jSeatMapper.toJpa(seat);

    assertEquals(seat.getIdSeat(), jSeat.getIdSeat());
    assertEquals("A1-01", jSeat.getNumber());
  }

  @Test
  void toDomain_shouldMapAllFields() {
    JSeat jSeat = JSeat.builder().idSeat(UUID.randomUUID()).number("A1-01").build();

    Seat seat = jSeatMapper.toDomain(jSeat);

    assertEquals(jSeat.getIdSeat(), seat.getIdSeat());
    assertEquals("A1-01", seat.getNumber());
  }

  @Test
  void withNull_shouldReturnNull() {
    assertNull(jSeatMapper.toJpa(null));
    assertNull(jSeatMapper.toDomain(null));
  }
}
