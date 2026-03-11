package com.example.project2backend.controllers;

import com.example.project2backend.models.Hike;
import com.example.project2backend.services.HikeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hikes")
public class HikeController {

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

    @PostMapping
    public Hike create(@RequestBody Hike hike) {
        return hikeService.createHike(hike);
    }
}