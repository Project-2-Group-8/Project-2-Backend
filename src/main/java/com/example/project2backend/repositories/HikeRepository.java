package com.example.project2backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project2backend.models.Hike;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HikeRepository extends JpaRepository<Hike, Long> {
    List<Hike> findAllByOrderByHikeIdAsc();
    @Query("SELECT hl.userId, SUM(COALESCE(hl.durationMin, 0)) " +
            "FROM HikeLog hl " +
            "GROUP BY hl.userId " +
            "ORDER BY SUM(hl.durationMin) DESC")
    List<Object[]> getLeaderboardData();

    // Fastest Running Times (Lowest number at the top)
    @Query("SELECT hl.userId, MIN(hl.durationMin) " +
            "FROM HikeLog hl " +
            "WHERE hl.durationMin > 0 AND hl.activityType = 'Running' " +
            "GROUP BY hl.userId " +
            "ORDER BY MIN(hl.durationMin) ASC")
    List<Object[]> getFastestRunning();

    // Fastest Walking Times
    @Query("SELECT hl.userId, MIN(hl.durationMin) " +
            "FROM HikeLog hl " +
            "WHERE hl.durationMin > 0 AND hl.activityType = 'Walking' " +
            "GROUP BY hl.userId " +
            "ORDER BY MIN(hl.durationMin) ASC")
    List<Object[]> getFastestWalking();


}