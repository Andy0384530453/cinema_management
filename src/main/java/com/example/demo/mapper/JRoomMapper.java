package com.example.demo.mapper;

import com.example.demo.entity.JRoom;
import com.example.demo.model.Room;
import java.util.HashSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JRoomMapper {

  private final JSeatMapper seatMapper;

  public JRoom toJpa(Room room) {
    if (room == null) {
      return null;
    }
    return JRoom.builder()
        .idRoom(room.getIdRoom())
        .number(room.getNumber())
        .capacity(room.getCapacity())
        .seats(
            room.getSeats() == null
                ? new HashSet<>()
                : room.getSeats().stream().map(seatMapper::toJpa).collect(Collectors.toSet()))
        .build();
  }

  public Room toDomain(JRoom jRoom) {
    if (jRoom == null) {
      return null;
    }
    return Room.builder()
        .idRoom(jRoom.getIdRoom())
        .number(jRoom.getNumber())
        .capacity(jRoom.getCapacity())
        .seats(
            jRoom.getSeats() == null
                ? new HashSet<>()
                : jRoom.getSeats().stream().map(seatMapper::toDomain).collect(Collectors.toSet()))
        .build();
  }
}
