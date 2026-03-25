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
        return hikeRepository.findAllByOrderByHikeIdAsc();
    }

    public Hike getHike(Long id) {
        return hikeRepository.findById(id).orElseThrow();
    }

    public Hike createHike(Hike hike) {
        return hikeRepository.save(hike);
    }

    public Hike updateHike(Long id, Hike updatedHike) {
        Hike existing = hikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hike not found"));

        existing.setHikeName(updatedHike.getHikeName());
        existing.setLocation(updatedHike.getLocation());
        existing.setLengthMi(updatedHike.getLengthMi());
        existing.setDifficulty(updatedHike.getDifficulty());


        return hikeRepository.save(existing);
    }

    public Hike partialUpdateHike(Long id, Hike updatedHike) {
        Hike existing = hikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hike not found"));

        if (updatedHike.getHikeName() != null)
            existing.setHikeName(updatedHike.getHikeName());

        if (updatedHike.getLocation() != null)
            existing.setLocation(updatedHike.getLocation());

        if (updatedHike.getLengthMi() != null)
            existing.setLengthMi(updatedHike.getLengthMi());

        if (updatedHike.getDifficulty() != null)
            existing.setDifficulty(updatedHike.getDifficulty());

        return hikeRepository.save(existing);
    }

    public void deleteHike(Long id) {
        hikeRepository.deleteById(id);
    }
}