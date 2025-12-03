package ru.utmn.dayagunov.functional_modeling_of_systems.model.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class UpdateUserRequestBody {
    @NotNull(message = "ID должно быть указано.")
    private Integer id;

    @NotNull(message = "Логин должен быть указан.")
    private String login;

    @NotNull(message = "Пароль должен быть указан.")
    private String password;

    @NotNull(message = "Дата въезда должна быть указана.")
    private LocalDate checkInDate;

    @NotNull(message = "Запланированная длительность пребывания должна быть указана.")
    private Integer plannedDurationOfStay;

    @NotNull(message = "ID цели визита должно быть указано.")
    private Integer purposeOfVisitId;

    @NotNull(message = "ID страны гражданства должно быть указано.")
    private Integer countryOfCitizenshipId;

    @NotNull(message = "Запланированная длительность пребывания должна быть указана.")
    private Boolean healthInsurancePolicyAvailable;

    @NotNull(message = "Запланированная длительность пребывания должна быть указана.")
    private Boolean medicalExaminationResultAvailable;
}