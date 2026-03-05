package com.example.project2backend.services;

import com.example.project2backend.dto.HikeTimeLogDtos;
import com.example.project2backend.models.HikeTimeLog;
import com.example.project2backend.repositories.HikeTimeLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HikeTimeLogService {

    private final HikeTimeLogRepository repo;

    public HikeTimeLogService(HikeTimeLogRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public HikeTimeLog start(String userEmail, HikeTimeLogDtos.StartRequest req) {
        HikeTimeLog log = new HikeTimeLog();
        log.setUserEmail(userEmail);
        log.setTrailName(req.trailName());
        log.setNotes(req.notes());
        log.setStartedAt(LocalDateTime.now());
        log.setEndedAt(null);
        log.setDurationMinutes(null);
        log.setIsRunning(req.running());
        return repo.save(log);
    }

    @Transactional(readOnly = true)
    public List<HikeTimeLog> listMine(String userEmail) {
        return repo.findByUserEmailOrderByStartedAtDesc(userEmail);
    }

    @Transactional(readOnly = true)
    public HikeTimeLog getMine(String userEmail, Long id) {
        return repo.findByIdAndUserEmail(id, userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "HikeTimeLog not found"));
    }

    @Transactional(readOnly = true)
    public List<HikeTimeLog> listRunning(boolean running) {
        return repo.findByRunning(running);
    }

    @Transactional
    public HikeTimeLog stop(String userEmail, Long id) {
        HikeTimeLog log = getMine(userEmail, id);
        if (log.getEndedAt() == null) {
            log.setEndedAt(LocalDateTime.now());
            log.recomputeDurationIfPossible();
        }
        return repo.save(log);
    }

    @Transactional
    public HikeTimeLog replace(String userEmail, Long id, HikeTimeLogDtos.ReplaceRequest req) {
        HikeTimeLog log = getMine(userEmail, id);
        log.setTrailName(req.trailName());
        log.setNotes(req.notes());
        log.setStartedAt(req.startedAt());
        log.setEndedAt(req.endedAt());
        log.recomputeDurationIfPossible();
        return repo.save(log);
    }

    @Transactional
    public HikeTimeLog patch(String userEmail, Long id, HikeTimeLogDtos.PatchRequest req) {
        HikeTimeLog log = getMine(userEmail, id);

        if (req.trailName() != null) log.setTrailName(req.trailName());
        if (req.notes() != null) log.setNotes(req.notes());
        if (req.startedAt() != null) log.setStartedAt(req.startedAt());
        if (req.endedAt() != null) log.setEndedAt(req.endedAt());

        log.recomputeDurationIfPossible();
        return repo.save(log);
    }

    @Transactional
    public void delete(String userEmail, Long id) {
        HikeTimeLog log = getMine(userEmail, id);
        repo.delete(log);
    }
}