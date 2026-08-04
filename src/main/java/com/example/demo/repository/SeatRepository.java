package com.example.demo.repository;

import com.example.demo.entity.JSeat;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<JSeat, UUID> {}
