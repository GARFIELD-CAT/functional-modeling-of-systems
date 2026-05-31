package ru.utmn.dayagunov.functional_modeling_of_systems.repository.road_map;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.Rule;

public interface RuleRepository extends JpaRepository<Rule, Integer> {
}
