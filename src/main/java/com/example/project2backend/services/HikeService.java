package com.example.project2backend.services;

import com.example.project2backend.models.Hike;
import com.example.project2backend.repositories.HikeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HikeService {

    private final HikeRepository hikeRepository;

    public HikeService(HikeRepository hikeRepository) {
        this.hikeRepository = hikeRepository;
    }

    public List<Hike> getAllHikes() {
        return hikeRepository.findAll();
    }

    public Hike getHike(Long id) {
        return hikeRepository.findById(id).orElseThrow();
    }

    public Hike createHike(Hike hike) {
        return hikeRepository.save(hike);
    }
}