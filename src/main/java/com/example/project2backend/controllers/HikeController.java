package com.example.project2backend.controllers;

import com.example.project2backend.models.HikeLog;
import com.example.project2backend.repositories.HikeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hikes")
public class HikeController {

    @Autowired
    private HikeLogRepository hikeLogRepository;

    // RECEIVE DATA: Save a hike from the Landing Page
    @PostMapping
    public HikeLog logHike(@RequestBody HikeLog hike) {
        return hikeLogRepository.save(hike);
    }

    // SEND DATA: Get the leaderboard filtered by type
    @GetMapping("/leaderboard")
    public List<HikeLog> getLeaderboard(@RequestParam String type) {
        return hikeLogRepository.findByActivityTypeOrderByDistanceMilesDesc(type);
    }
}