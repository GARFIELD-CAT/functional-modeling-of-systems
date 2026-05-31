package ru.utmn.dayagunov.functional_modeling_of_systems.model.condition;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.Rule;

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
    private Operators operator = Operators.EQ;

    @Column(nullable = false, length = 500)
    private String value;

}
