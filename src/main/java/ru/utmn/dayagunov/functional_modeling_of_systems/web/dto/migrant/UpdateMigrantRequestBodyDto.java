package ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.migrant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@Getter
@Setter
public class UpdateMigrantRequestBodyDto {
    @Schema(description = "ID профиля мигранта", example = "1")
    @NotNull(message = "ID профиля мигранта должен быть указан.")
    private Integer id;

    @Schema(requiredMode = NOT_REQUIRED, description = "Дата въезда в РФ")
    @PastOrPresent(message = "Дата въезда не может быть в будущем.")
    private LocalDate entryDate;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Планируемый срок пребывания в днях",
            example = "90",
            minimum = "1",
            maximum = "1100")
    @Positive(message = "Срок пребывания должен быть положительным числом.")
    private Integer plannedDurationOfStay;

    @Schema(requiredMode = NOT_REQUIRED, description = "ID страны гражданства", example = "1")
    private Integer countryOfCitizenshipId;

    @Schema(requiredMode = NOT_REQUIRED, description = "ID цели въезда", example = "1")
    private Integer purposeOfVisitId;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Высококвалифицированный специалист или член его семьи",
            defaultValue = "null")
    private Boolean hqsOrFamilyMember;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдена дактилоскопия в РФ",
            defaultValue = "null")
    private Boolean hasFingerprinting;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдено фотографирование в РФ",
            defaultValue = "null")
    private Boolean hasPhotoRegistration;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Состояли ли ранее на миграционном учёте",
            defaultValue = "null")
    private Boolean hasMigrationRegistration;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется полис ОМС или ДМС",
            defaultValue = "null")
    private Boolean hasMedicalInsurance;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдено медицинское освидетельствование",
            defaultValue = "null")
    private Boolean hasMedicalExamination;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется действующий сертификат о владении русским языком",
            defaultValue = "null")
    private Boolean hasRussianLanguageCert;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется ИНН, полученный в РФ",
            defaultValue = "null")
    private Boolean hasInn;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Участник госпрограммы переселения соотечественников",
            defaultValue = "null")
    private Boolean resettlementParticipant;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется аттестат об образовании СССР до 1991 года",
            defaultValue = "null")
    private Boolean hasUssrCertificate;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется диплом об образовании, полученный в РФ",
            defaultValue = "null")
    private Boolean hasRussianDiploma;
}