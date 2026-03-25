package com.example.project2backend.repositories;

import com.example.project2backend.models.Hike;
import com.example.project2backend.models.HikeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HikeLogRepository extends JpaRepository<HikeLog, Long> {
    // This allows the Landing Page to filter the leaderboard
    List<HikeLog> findByActivityTypeOrderByDistanceMileDesc(String activityType);
    List<HikeLog> findByHike_HikeId(Long hikeId);
    List<HikeLog> findByUserIdOrderByCreatedAtDesc(Long userId);
}