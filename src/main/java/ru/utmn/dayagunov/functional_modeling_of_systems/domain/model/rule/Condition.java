package ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule;

import jakarta.persistence.*;
import lombok.*;

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

    public boolean matches(RuleSubjectInterface subject) {
        Object actual = subject.getFieldValue(field);
        return operator.check(actual, value);
    }
}
