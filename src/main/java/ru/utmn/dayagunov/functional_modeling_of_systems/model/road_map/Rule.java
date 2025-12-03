package ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map;

import jakarta.persistence.*;
import lombok.Data;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.PurposeOfVisit;


@Entity
@Data
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 2000)
    String description;

    // Что нужно получить.
    @Column(nullable = false, length = 1000)
    String requiredOutcome;

    // Что нужно сделать.
    @Column(nullable = false, length = 1000)
    String actionRequired;

    // Сроки.
    @Column(nullable = false)
    Integer period;

    // Для какой цели визита действует правило.
    @ManyToOne
    @JoinColumn(name = "purpose_of_visit_id")
    PurposeOfVisit purposeOfVisit;

    // Для жителей каких стран действует правило.
    @Column(name = "countries", columnDefinition = "TEXT[]")
    String[] countries;
}
