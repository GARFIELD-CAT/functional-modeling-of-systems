package ru.utmn.dayagunov.functional_modeling_of_systems.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String login;
    private String password;
    private LocalDate checkInDate;
    private Integer plannedDurationOfStay;
    private String purposeOfVisit;
    private String countryOfCitizenship;
    private Integer roadMapId;
    private Boolean healthInsurancePolicyAvailable;
    private Boolean medicalExaminationResultAvailable;
}
