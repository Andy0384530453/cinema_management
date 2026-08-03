package com.example.demo.service;

import com.example.demo.dto.ReservationDetail;
import com.example.demo.mapper.ReservationMapper;
import com.example.demo.repository.ReservationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final ReservationMapper reservationMapper;

  public List<ReservationDetail> findAll() {
    return reservationRepository.findAll().stream().map(reservationMapper::toDetail).toList();
  }
}
