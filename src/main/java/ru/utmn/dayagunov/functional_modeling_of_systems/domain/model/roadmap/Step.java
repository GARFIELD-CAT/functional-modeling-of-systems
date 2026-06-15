package ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap;

import jakarta.persistence.*;
import lombok.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;

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
    private String title;

    @Column(nullable = false)
    private LocalDate deadline = LocalDate.now();

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "rule_id")
    private Rule rule;

    public static Step fromRule(Rule rule, LocalDate deadline, String message) {
        Step step = new Step();
        step.title = rule.getTitle();
        step.deadline = deadline;
        step.message = message;
        step.rule = rule;
        return step;
    }

     // Шаг-заглушка, когда подходящих правил не нашлось.
    public static Step notice(String title, String message) {
        Step step = new Step();
        step.title = title;
        step.message = message;
        return step;
    }
}
