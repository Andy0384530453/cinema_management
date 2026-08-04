package com.example.demo.mapper;

import com.example.demo.entity.JRoom;
import com.example.demo.model.Room;
import org.springframework.stereotype.Component;

@Component
public class JRoomMapper {

  public JRoom toJpa(Room room) {
    if (room == null) {
      return null;
    }
    return JRoom.builder()
        .idRoom(room.getIdRoom())
        .number(room.getNumber())
        .capacity(room.getCapacity())
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
        .build();
  }
}
