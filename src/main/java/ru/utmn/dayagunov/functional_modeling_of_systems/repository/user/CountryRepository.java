package ru.utmn.dayagunov.functional_modeling_of_systems.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
