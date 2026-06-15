package ru.utmn.dayagunov.functional_modeling_of_systems.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Country;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.PurposeOfVisit;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.migrant.CountryRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.migrant.PurposeOfVisitRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferenceDataService {
    private final CountryRepository countryRepository;
    private final PurposeOfVisitRepository purposeOfVisitRepository;

    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<PurposeOfVisit> getAllPurposesOfVisit() {
        return purposeOfVisitRepository.findAll();
    }
}