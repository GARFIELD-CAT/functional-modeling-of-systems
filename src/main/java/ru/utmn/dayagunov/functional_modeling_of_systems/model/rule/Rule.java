package ru.utmn.dayagunov.functional_modeling_of_systems.model.rule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.Condition;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rules")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Что нужно получить.
    @Column(nullable = false, columnDefinition = "TEXT")
    private String requiredResult;

    // Что нужно сделать.
    private @Column(nullable = false, columnDefinition = "TEXT")
    String requiredAction;

    // Срок.
    @Column(nullable = false)
    private Integer period;

    @Column(nullable = false)
    private LocalDate effectiveFrom;

    // NULL = бессрочно
    private LocalDate effectiveTo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id")
    private List<Condition> conditions = new ArrayList<>();

    @PrePersist
    void onCreate() {
        effectiveFrom = LocalDate.now();
    }
}
