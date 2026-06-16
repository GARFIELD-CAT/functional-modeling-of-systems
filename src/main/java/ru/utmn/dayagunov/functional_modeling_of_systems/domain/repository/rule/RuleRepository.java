package ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.rule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;

import java.time.LocalDate;
import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, Integer> {
    // Правила с условиями, действующие на указанную дату.
    @Query("""
            SELECT DISTINCT r FROM Rule r
             LEFT JOIN FETCH r.conditions
             WHERE r.effectiveFrom <= :date
               AND (r.effectiveTo IS NULL OR r.effectiveTo >= :date)
            """)
    List<Rule> findEffectiveOn(LocalDate date);

    @Query("SELECT DISTINCT r FROM Rule r LEFT JOIN FETCH r.conditions")
    List<Rule> findAllWithConditions();
}