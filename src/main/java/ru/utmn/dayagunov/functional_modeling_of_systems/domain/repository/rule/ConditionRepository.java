package ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.rule;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Condition;

import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Integer> {
    List<Condition> findByRuleId(Integer ruleId);
}