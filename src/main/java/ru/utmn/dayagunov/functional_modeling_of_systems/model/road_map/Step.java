package ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map;

import jakarta.persistence.*;
import lombok.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.Rule;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "rule")
@EqualsAndHashCode(of = "id")
@Table(name = "steps")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate deadline = LocalDate.now();

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    private Rule rule;
}
