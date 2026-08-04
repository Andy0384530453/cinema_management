package com.example.demo.repository;

import com.example.demo.entity.JMovie;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<JMovie, UUID> {}
