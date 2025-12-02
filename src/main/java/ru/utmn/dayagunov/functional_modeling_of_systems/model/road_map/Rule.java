package ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true, nullable = false)
    String message;

    @Column(nullable = false)
    Integer period;
}
