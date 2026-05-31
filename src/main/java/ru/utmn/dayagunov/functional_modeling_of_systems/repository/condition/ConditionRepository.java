package ru.utmn.dayagunov.functional_modeling_of_systems.repository.condition;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.Condition;

import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Integer> {
    List<Condition> findByRuleId(Integer ruleId);
}