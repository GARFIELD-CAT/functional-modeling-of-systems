package ru.utmn.dayagunov.functional_modeling_of_systems.model.condition;

import jakarta.persistence.*;
import lombok.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.Rule;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "rule")
@EqualsAndHashCode(of = "id")
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Operators operator = Operators.EQ;

    @Column(nullable = false, length = 500)
    private String value;
}
