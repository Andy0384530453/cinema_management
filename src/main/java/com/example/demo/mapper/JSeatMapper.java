package com.example.demo.mapper;

import com.example.demo.entity.JSeat;
import com.example.demo.model.Seat;
import org.springframework.stereotype.Component;

@Component
public class JSeatMapper {

  public JSeat toJpa(Seat seat) {
    if (seat == null) {
      return null;
    }
    return JSeat.builder().idSeat(seat.getIdSeat()).number(seat.getNumber()).build();
  }

  public Seat toDomain(JSeat jSeat) {
    if (jSeat == null) {
      return null;
    }
    return Seat.builder().idSeat(jSeat.getIdSeat()).number(jSeat.getNumber()).build();
  }
}
