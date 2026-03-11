package com.example.project2backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "hike_logs")
public class HikeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "duration_min")
    private Double durationMin;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "notes")
    private String notes;

    @Column(name = "activity_type")
    private String activityType; // "Walking" or "Running"

    @Column(name = "distance_mile")
    private Double distanceMile;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "hike_id") // FK column in hike_logs
    private Hike hike;


    public HikeLog() {}

    // Getters and Setters
    public Long getLogId() { return logId; }
    public void setLogId(Long logId) { this.logId = logId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Double getDurationMin() { return durationMin; }
    public void setDurationMin(Double durationMin) { this.durationMin = durationMin; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public Double getDistanceMile() { return distanceMile; }
    public void setDistanceMile(Double distanceMile) { this.distanceMile = distanceMile; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Hike getHike() { return hike; }
    public void setHike(Hike hike) { this.hike = hike; }
}
