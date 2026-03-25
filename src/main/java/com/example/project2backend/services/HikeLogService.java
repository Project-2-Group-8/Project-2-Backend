package com.example.project2backend.services;

import com.example.project2backend.models.HikeLog;
import com.example.project2backend.repositories.HikeLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HikeLogService {

    private final HikeLogRepository hikeLogRepository;

    public HikeLogService(HikeLogRepository hikeLogRepository) {
        this.hikeLogRepository = hikeLogRepository;
    }

    public List<HikeLog> getLogsByHikeId(Long hikeId) {
        return hikeLogRepository.findByHike_HikeId(hikeId);
    }
}
