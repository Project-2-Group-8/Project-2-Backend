package com.example.project2backend.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "hike")
public class Hike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hikeId;

    @Column(nullable = false)
    private String hikeName;

    private String location;

    private Double lengthMi;

    private Double difficulty;

    private String imageUrl;

    // Relationships
    @OneToMany(mappedBy = "hike", cascade = CascadeType.ALL)
    private List<HikeLog> logs;

    // getters & setters
}