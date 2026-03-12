package com.example.project2backend.unit;

import com.example.project2backend.controllers.HikeLogController;
import com.example.project2backend.models.HikeLog;
import com.example.project2backend.repositories.HikeLogRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HikeLogControllerUnitTest {

    private HikeLogRepository mockRepo;
    private HikeLogController controller;

    @BeforeEach
    void setUp() {
        mockRepo = mock(HikeLogRepository.class);
        controller = new HikeLogController(mockRepo);
    }

    @Test
    void logHikeSavesHike() {

        HikeLog hike = new HikeLog();
        hike.setLogId(1L);

        when(mockRepo.save(hike)).thenReturn(hike);

        HikeLog result = controller.logHike(hike);

        assertEquals(hike, result);
        verify(mockRepo, times(1)).save(hike);
    }

    @Test
    void getLeaderboardReturnsFilteredHikes() {

        HikeLog hike1 = new HikeLog();
        hike1.setActivityType("Walking");

        HikeLog hike2 = new HikeLog();
        hike2.setActivityType("Walking");

        List<HikeLog> hikes = List.of(hike1, hike2);

        when(mockRepo.findByActivityTypeOrderByDistanceMileDesc("Walking"))
                .thenReturn(hikes);

        List<HikeLog> result = controller.getLeaderboard("Walking");

        assertEquals(2, result.size());

        verify(mockRepo, times(1))
                .findByActivityTypeOrderByDistanceMileDesc("Walking");
    }

    @Test
    void logsForUserReturnsUserLogs() {

        HikeLog hike1 = new HikeLog();
        hike1.setUserId(5L);

        HikeLog hike2 = new HikeLog();
        hike2.setUserId(5L);

        List<HikeLog> userLogs = List.of(hike1, hike2);

        when(mockRepo.findByUserIdOrderByCreatedAtDesc(5L))
                .thenReturn(userLogs);

        List<HikeLog> result = controller.logsForUser(5L);

        assertEquals(2, result.size());

        verify(mockRepo, times(1))
                .findByUserIdOrderByCreatedAtDesc(5L);
    }
}