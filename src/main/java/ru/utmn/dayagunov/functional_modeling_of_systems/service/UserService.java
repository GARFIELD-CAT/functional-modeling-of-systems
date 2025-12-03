package ru.utmn.dayagunov.functional_modeling_of_systems.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.*;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.user.CountryRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.user.PurposeOfVisitRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.user.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final CountryRepository countryRepository;
    private final PurposeOfVisitRepository purposeOfVisitRepository;

    public User createUser(String login, String password) {
        Optional<User> result = findUserByLogin(login);

        if (result.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, String.format("Пользователь с таким login=%s уже существует.", login)
            );
        }

        User user = new User(login, password);

        repository.save(user);

        return user;
    }

    public Optional<User> findUserByLogin(String login) {
        return repository.findUserByLogin(login);
    }

    public User updateUser(UpdateUserRequestBody updateUserRequestBody) {
        User user = repository.findById(updateUserRequestBody.getId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Пользователь с таким id=%d не существует", updateUserRequestBody.getId())
                )
        );

        Optional<User> userByLogin = findUserByLogin(updateUserRequestBody.getLogin());

        if (userByLogin.isPresent()) {
            if (!user.getId().equals(userByLogin.get().getId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, String.format("Пользователь с таким login=%s уже существует", updateUserRequestBody.getLogin())
                );
            }
        }

        Country country = countryRepository.findById(updateUserRequestBody.getCountryOfCitizenshipId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Страны с таким id=%d не существует", updateUserRequestBody.getCountryOfCitizenshipId())
                )
        );
        PurposeOfVisit purposeOfVisit = purposeOfVisitRepository.findById(updateUserRequestBody.getPurposeOfVisitId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Цели визита с таким id=%d не существует", updateUserRequestBody.getPurposeOfVisitId())
                )
        );

        user.setCountryOfCitizenship(country);
        user.setPurposeOfVisit(purposeOfVisit);
        user.setLogin(updateUserRequestBody.getLogin());
        user.setPassword(updateUserRequestBody.getPassword());
        user.setCheckInDate(updateUserRequestBody.getCheckInDate());
        user.setPlannedDurationOfStay(updateUserRequestBody.getPlannedDurationOfStay());
        user.setHealthInsurancePolicyAvailable(updateUserRequestBody.getHealthInsurancePolicyAvailable());
        user.setMedicalExaminationResultAvailable(updateUserRequestBody.getMedicalExaminationResultAvailable());

        return repository.save(user);
    }

    public User getUser(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Пользователь с таким id=%d не существует", id)
                )
        );
    }

    public void deleteUser(Integer id) {
        repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("Пользователь с таким id=%d не существует", id)
                )
            );

        repository.deleteById(id);
    }

    public UserResponseDto prepareUserResponseDto(User user) {
        UserResponseDto userDto = new UserResponseDto();

        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        userDto.setCheckInDate(user.getCheckInDate());
        userDto.setPlannedDurationOfStay(user.getPlannedDurationOfStay());
        userDto.setHealthInsurancePolicyAvailable(user.getHealthInsurancePolicyAvailable());
        userDto.setMedicalExaminationResultAvailable(user.getMedicalExaminationResultAvailable());

        userDto.setCountryOfCitizenship(Optional.ofNullable(user.getCountryOfCitizenship())
                .map(Country::getName)
                .orElse(null));

        userDto.setPurposeOfVisit(Optional.ofNullable(user.getPurposeOfVisit())
                .map(PurposeOfVisit::getName)
                .orElse(null));

        userDto.setRoadMapId(Optional.ofNullable(user.getRoadMap())
                .map(RoadMap::getId)
                .orElse(null));

        return userDto;
    }
}
