package ru.utmn.dayagunov.functional_modeling_of_systems.repository.migrant;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
