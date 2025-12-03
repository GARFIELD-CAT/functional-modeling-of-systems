package ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String status = StepStatus.NOT_DONE.getDescription();

    @Column(nullable = false)
    LocalDate deadline = LocalDate.now();

    @Column(nullable = false, length = 2500)
    String description;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    Rule rule;
}
