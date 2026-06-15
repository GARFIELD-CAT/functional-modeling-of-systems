package ru.utmn.dayagunov.functional_modeling_of_systems.web.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Country;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.PurposeOfVisit;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.migrant.CountryResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.migrant.PurposeOfVisitResponseDto;

@Component
public class ReferenceMapper {
    public CountryResponseDto toCountryDto(Country country) {
        CountryResponseDto dto = new CountryResponseDto();
        BeanUtils.copyProperties(country, dto);
        return dto;
    }

    public PurposeOfVisitResponseDto toPurposeOfVisitDto(PurposeOfVisit purpose) {
        PurposeOfVisitResponseDto dto = new PurposeOfVisitResponseDto();
        BeanUtils.copyProperties(purpose, dto);
        return dto;
    }
}