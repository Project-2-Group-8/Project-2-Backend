package com.example.project2backend.controllers;

import com.example.project2backend.models.Hike;
import com.example.project2backend.repositories.HikeRepository;
import com.example.project2backend.services.HikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hikes")
public class HikeController {
    @Autowired
    private HikeRepository HikeRepository;
    private final HikeService hikeService;

    public HikeController(HikeService hikeService) {
        this.hikeService = hikeService;
    }

    @GetMapping("/all")
    public List<Hike> getAll() {
        return hikeService.getAllHikes();
    }

    @GetMapping("/{id}")
    public Hike getOne(@PathVariable Long id) {
        return hikeService.getHike(id);
    }

    @GetMapping("/leaderboard")
    public List<Object[]> getLeaderboard(@RequestParam(required = false, defaultValue = "Running") String type) {
        if ("Walking".equalsIgnoreCase(type)) {
            return HikeRepository.getFastestWalking();
        }
        return HikeRepository.getFastestRunning();
    }
    @PostMapping
    public Hike create(@RequestBody Hike hike) {
        return hikeService.createHike(hike);
    }

    @PutMapping("/{id}")
    public Hike update(@PathVariable Long id, @RequestBody Hike updatedHike) {
        return hikeService.updateHike(id, updatedHike);
    }

    @PatchMapping("/{id}")
    public Hike partialUpdate(@PathVariable Long id, @RequestBody Hike updatedHike) {
        return hikeService.partialUpdateHike(id, updatedHike);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        hikeService.deleteHike(id);
    }
}