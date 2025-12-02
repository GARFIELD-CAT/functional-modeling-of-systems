package ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String status;

    @Column(nullable = false)
    LocalDateTime deadline;

    @Column(nullable = false)
    String description;

    @ManyToOne
    @JoinColumn(name = "rule_id", nullable = false)
    Rule rule;

    @ManyToOne
    @JoinColumn(name = "road_map_id")
    private RoadMap roadMap;
}
