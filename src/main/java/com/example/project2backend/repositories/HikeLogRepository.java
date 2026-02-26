package com.example.project2backend.repositories;

import com.example.project2backend.models.HikeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HikeLogRepository extends JpaRepository<HikeLog, Long> {
    // This allows the Landing Page to filter the leaderboard
    List<HikeLog> findByActivityTypeOrderByDistanceMilesDesc(String activityType);
}