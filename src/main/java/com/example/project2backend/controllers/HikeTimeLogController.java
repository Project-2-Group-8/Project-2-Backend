package com.example.project2backend.controllers;

import com.example.project2backend.dto.HikeTimeLogDtos;
import com.example.project2backend.models.HikeTimeLog;
import com.example.project2backend.services.HikeTimeLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/hike-times")
public class HikeTimeLogController {

    private final HikeTimeLogService service;

    public HikeTimeLogController(HikeTimeLogService service) {
        this.service = service;
    }

    // GET /api/hike-times  (list my time logs)
    @GetMapping
    public List<HikeTimeLog> listMine(@AuthenticationPrincipal OAuth2User user) {
        return service.listMine(email(user));
    }

    // GET /api/hike-times/{id} (get one)
    @GetMapping("/{id}")
    public HikeTimeLog getOne(@AuthenticationPrincipal OAuth2User user, @PathVariable Long id) {
        return service.getMine(email(user), id);
    }

    // POST /api/hike-times (start timer)
    @PostMapping
    public ResponseEntity<HikeTimeLog> start(
            @AuthenticationPrincipal OAuth2User user,
            @Valid @RequestBody HikeTimeLogDtos.StartRequest req
    ) {
        HikeTimeLog created = service.start(email(user), req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PATCH /api/hike-times/{id}/stop (stop timer)
    @PatchMapping("/{id}/stop")
    public HikeTimeLog stop(@AuthenticationPrincipal OAuth2User user, @PathVariable Long id) {
        return service.stop(email(user), id);
    }

    // PUT /api/hike-times/{id} (replace)
    @PutMapping("/{id}")
    public HikeTimeLog replace(
            @AuthenticationPrincipal OAuth2User user,
            @PathVariable Long id,
            @Valid @RequestBody HikeTimeLogDtos.ReplaceRequest req
    ) {
        return service.replace(email(user), id, req);
    }

    // PATCH /api/hike-times/{id} (partial update)
    @PatchMapping("/{id}")
    public HikeTimeLog patch(
            @AuthenticationPrincipal OAuth2User user,
            @PathVariable Long id,
            @RequestBody HikeTimeLogDtos.PatchRequest req
    ) {
        return service.patch(email(user), id, req);
    }

    // DELETE /api/hike-times/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal OAuth2User user, @PathVariable Long id) {
        service.delete(email(user), id);
        return ResponseEntity.noContent().build();
    }

    private static String email(OAuth2User user) {
        if (user == null) {
            // In theory SecurityConfig blocks this already, but it keeps the API honest...hopefully.
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }
        String email = user.getAttribute("email");
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing email claim");
        }
        return email;
    }
}