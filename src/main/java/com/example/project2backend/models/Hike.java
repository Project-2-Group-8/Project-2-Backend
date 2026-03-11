package com.example.project2backend.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "hike")
public class Hike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hike_id")
    private Long hikeId;

    @Column(name = "hike_name",nullable = false)
    private String hikeName;

    private String location;

    @Column(name = "length_mi")
    private Double lengthMi;

    private Double difficulty;

    @Column(name = "image_url")
    private String imageUrl;

    // Relationships
    @JsonIgnore
    @OneToMany(mappedBy = "hike", cascade = CascadeType.ALL)
    private List<HikeLog> logs;

    // getters & setters

    public Long getHikeId() {
        return hikeId;
    }

    public void setHikeId(Long hikeId) {
        this.hikeId = hikeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }

    public Double getLengthMi() {
        return lengthMi;
    }

    public void setLengthMi(Double lengthMi) {
        this.lengthMi = lengthMi;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHikeName() {
        return hikeName;
    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public List<HikeLog> getLogs() {
        return logs;
    }

    public void setLogs(List<HikeLog> logs) {
        this.logs = logs;
    }
}