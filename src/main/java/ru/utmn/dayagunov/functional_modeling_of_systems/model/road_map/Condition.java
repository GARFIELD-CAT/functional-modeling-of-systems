package ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conditions")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rule_id", nullable = false)
    private Rule rule;

    @Column(nullable = false, length = 100)
    private String field;

    @Column(nullable = false, length = 10)
    private String operator = Operators.EQ.getDescription();

    @Column(nullable = false, length = 500)
    private String value;

}
