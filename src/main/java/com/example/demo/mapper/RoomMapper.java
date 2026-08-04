package com.example.demo.mapper;

import com.example.demo.dto.RoomDetail;
import com.example.demo.model.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

  public RoomDetail toDetail(Room room) {
    if (room == null) {
      return null;
    }
    return RoomDetail.builder()
        .idRoom(room.getIdRoom())
        .number(room.getNumber())
        .capacity(room.getCapacity())
        .build();
  }
}
