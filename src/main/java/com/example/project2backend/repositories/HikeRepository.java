package com.example.project2backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project2backend.models.Hike;

public interface HikeRepository extends JpaRepository<Hike, Long> {
}