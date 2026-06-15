package ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.migrant;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.PurposeOfVisit;

public interface PurposeOfVisitRepository extends JpaRepository<PurposeOfVisit, Integer> {
}
