package ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MigrantResponseDto {
    private Integer id;
    private LocalDate entryDate;
    private Integer plannedDurationOfStay;
    private String countryOfCitizenship;
    private String purposeOfVisit;
    private Integer roadMapId;
    private boolean hqsOrFamilyMember;
    private boolean hasFingerprinting;
    private boolean hasPhotoRegistration;
    private boolean hasMigrationRegistration;
    private boolean hasMedicalInsurance;
    private boolean hasMedicalExamination;
    private boolean hasRussianLanguageCert;
    private boolean hasInn;
    private boolean resettlementParticipant;
    private boolean hasUssrCertificate;
    private boolean hasRussianDiploma;
}
