package com.example.project2backend.repositories;

import com.example.project2backend.models.HikeTimeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HikeTimeLogRepository extends JpaRepository<HikeTimeLog, Long> {

    List<HikeTimeLog> findByUserEmailOrderByStartedAtDesc(String userEmail);

    Optional<HikeTimeLog> findByIdAndUserEmail(Long id, String userEmail);
}