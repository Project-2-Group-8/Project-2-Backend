package com.example.project2backend.unit;

import com.example.project2backend.controllers.HikeLogController;
import com.example.project2backend.models.HikeLog;
import com.example.project2backend.repositories.HikeLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HikeLogControllerUnitTest {

    private HikeLogRepository mockRepo;
    private HikeLogController controller;

    @BeforeEach
    void setUp() {
        mockRepo = mock(HikeLogRepository.class);
        controller = new HikeLogController();
        controller.hikeLogRepository = mockRepo;
    }

    @Test
    void logHikeSavesHike() {
        HikeLog hike = new HikeLog();
        hike.setId(1L);
        when(mockRepo.save(hike)).thenReturn(hike);

        HikeLog result = controller.logHike(hike);

        assertEquals(hike, result);
        verify(mockRepo, times(1)).save(hike);
    }

    @Test
    void getLeaderboardReturnsFilteredHikes() {
        HikeLog hike1 = new HikeLog(); hike1.setActivityType("trail");
        HikeLog hike2 = new HikeLog(); hike2.setActivityType("trail");
        List<HikeLog> hikes = List.of(hike1, hike2);

        when(mockRepo.findByActivityTypeOrderByDistanceMilesDesc("trail")).thenReturn(hikes);

        List<HikeLog> result = controller.getLeaderboard("trail");

        assertEquals(2, result.size());
        verify(mockRepo, times(1)).findByActivityTypeOrderByDistanceMilesDesc("trail");
    }

    @Test
    void getHikeByIdReturnsOptional() {
        HikeLog hike = new HikeLog(); hike.setId(1L);
        when(mockRepo.findById(1L)).thenReturn(Optional.of(hike));

        Optional<HikeLog> result = controller.getHikeById(1L);

        assertTrue(result.isPresent());
        assertEquals(hike, result.get());
        verify(mockRepo, times(1)).findById(1L);
    }

    @Test
    void getAllHikesReturnsAll() {
        HikeLog hike1 = new HikeLog();
        HikeLog hike2 = new HikeLog();
        List<HikeLog> allHikes = List.of(hike1, hike2);

        when(mockRepo.findAll()).thenReturn(allHikes);

        List<HikeLog> result = controller.getAllHikes();

        assertEquals(2, result.size());
        verify(mockRepo, times(1)).findAll();
    }
}