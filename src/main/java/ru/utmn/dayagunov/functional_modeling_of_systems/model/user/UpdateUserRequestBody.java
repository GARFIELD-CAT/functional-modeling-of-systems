package ru.utmn.dayagunov.functional_modeling_of_systems.model.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class UpdateUserRequestBody {
    @NotNull(message = "ID должно быть указано.")
    private Integer id;

    @Size(min = 8, max = 30, message = "Размер должен быть от 8 до 30 символов")
    private String login;

    @Size(min = 8, max = 30, message = "Размер должен быть от 8 до 30 символов")
    private String password;

    @NotNull(message = "Дата въезда должна быть указана.")
    private LocalDate checkInDate;

    @NotNull(message = "Запланированная длительность пребывания должна быть указана.")
    private Integer plannedDurationOfStay;

    @NotNull(message = "ID цели визита должно быть указано.")
    private Integer purposeOfVisitId;

    @NotNull(message = "ID страны гражданства должно быть указано.")
    private Integer countryOfCitizenshipId;

    @NotNull(message = "Наличие полиса обязательного или дополнительного медицинского страхования должно быть указано.")
    private Boolean healthInsurancePolicyAvailable;

    @NotNull(message = "Наличие результата медицинского освидетельствования на отсутствие заболеваний должно быть указано.")
    private Boolean medicalExaminationResultAvailable;
}