package ru.utmn.dayagunov.functional_modeling_of_systems.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Country;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Migrant;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.PurposeOfVisit;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.migrant.CountryRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.migrant.MigrantRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.migrant.PurposeOfVisitRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.migrant.CreateMigrantRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.migrant.UpdateMigrantRequestBodyDto;

import static ru.utmn.dayagunov.functional_modeling_of_systems.infrastructure.util.BeanCopyUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class MigrantService {
    private final UserService userService;
    private final AccessGuard accessGuard;
    private final MigrantRepository migrantRepository;
    private final CountryRepository countryRepository;
    private final PurposeOfVisitRepository purposeOfVisitRepository;

    @Transactional
    public Migrant createMigrant(CreateMigrantRequestBodyDto body) {
        User currentUser = userService.getCurrentUser();

        if (migrantRepository.findByUserLogin(currentUser.getLogin()).isPresent()) {
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
        accessGuard.checkOwnerOrAdmin(migrant);

        BeanUtils.copyProperties(body, migrant, getNullPropertyNames(body));

        if (body.getCountryOfCitizenshipId() != null) {
            migrant.setCountryOfCitizenship(findCountryById(body.getCountryOfCitizenshipId()));
        }
        if (body.getPurposeOfVisitId() != null) {
            migrant.setPurposeOfVisit(findPurposeById(body.getPurposeOfVisitId()));
        }

        return migrantRepository.save(migrant);
    }

    @Transactional(readOnly = true)
    public Migrant getMigrant(Integer id) {
        Migrant migrant = findMigrantById(id);
        accessGuard.checkOwnerOrAdmin(migrant);

        return migrant;
    }

    @Transactional(readOnly = true)
    public Migrant getCurrentMigrant() {
        String userLogin = userService.getCurrentUserLogin();

        return migrantRepository.findByUserLogin(userLogin).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Профиль мигранта для текущего пользователя не найден."
                )
        );
    }

    @Transactional
    public void deleteMigrant(Integer id) {
        Migrant migrant = findMigrantById(id);
        accessGuard.checkOwnerOrAdmin(migrant);

        migrantRepository.deleteById(id);
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