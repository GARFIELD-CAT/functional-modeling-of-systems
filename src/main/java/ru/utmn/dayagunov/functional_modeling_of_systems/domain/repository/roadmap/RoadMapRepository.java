package ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.roadmap;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap.RoadMap;

public interface RoadMapRepository extends JpaRepository<RoadMap, Integer> {
}
