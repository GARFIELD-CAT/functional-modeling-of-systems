package ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule;

import jakarta.persistence.*;
import lombok.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Migrant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "conditions")
@EqualsAndHashCode(of = "id")
@Table(name = "rules")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    // Что нужно получить.
    @Column(nullable = false, columnDefinition = "TEXT")
    private String requiredResult;

    // Что нужно сделать.
    @Column(nullable = false, columnDefinition = "TEXT")
    private String requiredAction;

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

    /**
     * Information Expert: правило владеет своими условиями, поэтому само и решает,
     * подходит ли оно конкретному мигранту (все условия должны выполниться).
     */
    public boolean matches(Migrant migrant) {
        for (Condition condition : conditions) {
            if (!migrant.check(condition)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Information Expert: правило знает свой срок (period), поэтому само считает
     * крайнюю дату относительно даты въезда мигранта.
     */
    public LocalDate deadlineFor(Migrant migrant) {
        LocalDate base = migrant.getEntryDate() != null ? migrant.getEntryDate() : LocalDate.now();
        return base.plusDays(period);
    }
}
