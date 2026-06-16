package ru.utmn.dayagunov.functional_modeling_of_systems.web.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Country;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Migrant;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.PurposeOfVisit;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.migrant.MigrantResponseDto;

import java.util.Optional;

@Component
public class MigrantMapper {
    public MigrantResponseDto toResponseDto(Migrant migrant) {
        MigrantResponseDto dto = new MigrantResponseDto();
        BeanUtils.copyProperties(migrant, dto);

        dto.setCountryOfCitizenship(Optional.ofNullable(migrant.getCountryOfCitizenship())
                .map(Country::getName).orElse(null));
        dto.setPurposeOfVisit(Optional.ofNullable(migrant.getPurposeOfVisit())
                .map(PurposeOfVisit::getName).orElse(null));

        return dto;
    }
}
