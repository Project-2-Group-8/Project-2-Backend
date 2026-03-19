//package com.example.project2backend.unit;
//
//import com.example.project2backend.controllers.HikeTimeLogController;
//import com.example.project2backend.dto.HikeTimeLogDtos;
//import com.example.project2backend.models.HikeTimeLog;
//import com.example.project2backend.services.HikeTimeLogService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class HikeTimeLogControllerUnitTest {
//
//    private HikeTimeLogService service;
//    private HikeTimeLogController controller;
//
//    @BeforeEach
//    void setUp() {
//        service = mock(HikeTimeLogService.class);
//        controller = new HikeTimeLogController(service);
//    }
//
//    private OAuth2User mockUser(String email) {
//        OAuth2User user = mock(OAuth2User.class);
//        when(user.getAttribute("email")).thenReturn(email);
//        return user;
//    }
//
//    @Test
//    void listMineDelegatesToService() {
//        OAuth2User user = mockUser("alice@example.com");
//        HikeTimeLog log = new HikeTimeLog();
//        when(service.listMine("alice@example.com")).thenReturn(List.of(log));
//
//        List<HikeTimeLog> result = controller.listMine(user);
//
//        assertEquals(1, result.size());
//        verify(service, times(1)).listMine("alice@example.com");
//    }
//
//    @Test
//    void getOneDelegatesToService() {
//        OAuth2User user = mockUser("alice@example.com");
//        HikeTimeLog log = new HikeTimeLog();
//        when(service.getMine("alice@example.com", 1L)).thenReturn(log);
//
//        HikeTimeLog result = controller.getOne(user, 1L);
//
//        assertEquals(log, result);
//        verify(service).getMine("alice@example.com", 1L);
//    }
//
//    @Test
//    void startDelegatesToService() {
//        OAuth2User user = mockUser("alice@example.com");
//        HikeTimeLogDtos.StartRequest req = new HikeTimeLogDtos.StartRequest(
//                "trail",
//                "Nice hike",
//                false
//        );
//        HikeTimeLog log = new HikeTimeLog();
//
//        when(service.start("alice@example.com", req)).thenReturn(log);
//
//        ResponseEntity<HikeTimeLog> response = controller.start(user, req);
//
//        assertEquals(201, response.getStatusCode().value());
//        assertEquals(log, response.getBody());
//        verify(service).start("alice@example.com", req);
//    }
//
//    @Test
//    void stopDelegatesToService() {
//        OAuth2User user = mockUser("alice@example.com");
//        HikeTimeLog log = new HikeTimeLog();
//        when(service.stop("alice@example.com", 1L)).thenReturn(log);
//
//        HikeTimeLog result = controller.stop(user, 1L);
//
//        assertEquals(log, result);
//        verify(service).stop("alice@example.com", 1L);
//    }
//
//    @Test
//    void patchDelegatesToService() {
//        OAuth2User user = mockUser("alice@example.com");
//        HikeTimeLogDtos.PatchRequest req = new HikeTimeLogDtos.PatchRequest(
//                "updated trail",
//                LocalDateTime.now().minusHours(1),
//                LocalDateTime.now(),
//                "Updated notes"
//        );
//        HikeTimeLog log = new HikeTimeLog();
//        when(service.patch("alice@example.com", 1L, req)).thenReturn(log);
//
//        HikeTimeLog result = controller.patch(user, 1L, req);
//
//        assertEquals(log, result);
//        verify(service).patch("alice@example.com", 1L, req);
//    }
//
//    @Test
//    void replaceDelegatesToService() {
//        OAuth2User user = mockUser("alice@example.com");
//        HikeTimeLogDtos.ReplaceRequest req = new HikeTimeLogDtos.ReplaceRequest(
//                "new trail",
//                LocalDateTime.now().minusHours(2),
//                LocalDateTime.now(),
//                "Replacement notes"
//        );
//        HikeTimeLog log = new HikeTimeLog();
//        when(service.replace("alice@example.com", 1L, req)).thenReturn(log);
//
//        HikeTimeLog result = controller.replace(user, 1L, req);
//
//        assertEquals(log, result);
//        verify(service).replace("alice@example.com", 1L, req);
//    }
//
//    @Test
//    void deleteDelegatesToService() {
//        OAuth2User user = mockUser("alice@example.com");
//
//        ResponseEntity<Void> response = controller.delete(user, 1L);
//
//        assertEquals(204, response.getStatusCode().value());
//        verify(service).delete("alice@example.com", 1L);
//    }
//
//    @Test
//    void nullUserThrowsUnauthorized() {
//        assertThrows(ResponseStatusException.class, () -> controller.listMine(null));
//    }
//}