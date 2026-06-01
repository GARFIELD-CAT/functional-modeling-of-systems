package ru.utmn.dayagunov.functional_modeling_of_systems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.dto.CreateMigrantRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.dto.UpdateMigrantRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.dto.MigrantResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.migrant.CountryRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.migrant.PurposeOfVisitRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.migrant.MigrantRepository;

import java.util.Optional;

import static ru.utmn.dayagunov.functional_modeling_of_systems.util.BeanCopyUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class MigrantService {
    private final UserService userService;
    private final MigrantRepository migrantRepository;
    private final CountryRepository countryRepository;
    private final PurposeOfVisitRepository purposeOfVisitRepository;

    @Transactional
    public Migrant createMigrant(CreateMigrantRequestBodyDto body) {
        User currentUser = userService.getCurrentUser();
        Optional<Migrant> result = migrantRepository.findMigrantByUserId(currentUser.getId());

        if (result.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Профиль мигранта для текущего пользователя уже существует."
            );
        }

        Country country = findCountryById(body.getCountryOfCitizenshipId());
        PurposeOfVisit purpose = findPurposeById(body.getPurposeOfVisitId());

        Migrant migrant = new Migrant();
        BeanUtils.copyProperties(body, migrant);
        migrant.setUser(currentUser);
        migrant.setCountryOfCitizenship(country);
        migrant.setPurposeOfVisit(purpose);

        return migrantRepository.save(migrant);
    }

    @Transactional
    public Migrant updateMigrant(UpdateMigrantRequestBodyDto body) {
        Migrant migrant = findMigrantById(body.getId());
        userService.isOwnerOrAdmin(migrant);
        Country country = findCountryById(body.getCountryOfCitizenshipId());
        PurposeOfVisit purpose = findPurposeById(body.getPurposeOfVisitId());

        BeanUtils.copyProperties(body, migrant, getNullPropertyNames(body));
        migrant.setCountryOfCitizenship(country);
        migrant.setPurposeOfVisit(purpose);

        return migrantRepository.save(migrant);
    }

    @Transactional(readOnly = true)
    public Migrant getMigrant(Integer id) {
        Migrant migrant = findMigrantById(id);
        userService.isOwnerOrAdmin(migrant);

        return migrant;
    }

    @Transactional
    public void deleteMigrant(Integer id) {
        Migrant migrant = findMigrantById(id);
        userService.isOwnerOrAdmin(migrant);

        migrantRepository.deleteById(id);
    }

    public MigrantResponseDto prepareMigrantResponseDto(Migrant migrant) {
        MigrantResponseDto dto = new MigrantResponseDto();
        BeanUtils.copyProperties(migrant, dto);

        dto.setCountryOfCitizenship(Optional.ofNullable(migrant.getCountryOfCitizenship())
                .map(Country::getName).orElse(null));
        dto.setPurposeOfVisit(Optional.ofNullable(migrant.getPurposeOfVisit())
                .map(PurposeOfVisit::getName).orElse(null));
        dto.setRoadMapId(Optional.ofNullable(migrant.getRoadMap())
                .map(RoadMap::getId).orElse(null));

        return dto;
    }

    private Country findCountryById(Integer id) {
        return countryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Страны с id=%d не существует", id)
                )
        );
    }

    private PurposeOfVisit findPurposeById(Integer id) {
        return purposeOfVisitRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Цели визита с id=%d не существует", id)
                )
        );
    }

    private Migrant findMigrantById(Integer id) {
        return migrantRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Профиль мигранта с id=%d не существует", id)
                )
        );
    }
}
