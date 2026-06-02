package ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;


@Getter
@Setter
public class UpdateMigrantRequestBodyDto {
    @Schema(description = "ID профиля мигранта", example = "1")
    @NotNull(message = "ID профиля мигранта должен быть указан.")
    private Integer id;

    @Schema(description = "Дата въезда в РФ")
    @PastOrPresent(message = "Дата въезда не может быть в будущем.")
    private LocalDate entryDate;

    @Schema(description = "Планируемый срок пребывания в днях")
    @Positive(message = "Срок пребывания должен быть положительным числом.")
    private Integer plannedDurationOfStay;

    @Schema(description = "ID страны гражданства")
    private Integer countryOfCitizenshipId;

    @Schema(description = "ID цели въезда")
    private Integer purposeOfVisitId;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Высококвалифицированный специалист или член его семьи",
            defaultValue = "null")
    private boolean hqsOrFamilyMember;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдена дактилоскопия в РФ",
            defaultValue = "null")
    private boolean hasFingerprinting;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдено фотографирование в РФ",
            defaultValue = "null")
    private boolean hasPhotoRegistration;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Состояли ли ранее на миграционном учёте",
            defaultValue = "null")
    private boolean hasMigrationRegistration;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется полис ОМС или ДМС",
            defaultValue = "null")
    private boolean hasMedicalInsurance;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдено медицинское освидетельствование",
            defaultValue = "null")
    private boolean hasMedicalExaminatione;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется действующий сертификат о владении русским языком",
            defaultValue = "null")
    private boolean hasRussianLanguageCert;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется ИНН, полученный в РФ",
            defaultValue = "null")
    private boolean hasInn;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Участник госпрограммы переселения соотечественников",
            defaultValue = "null")
    private boolean resettlementParticipant;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется аттестат об образовании СССР до 1991 года",
            defaultValue = "null")
    private boolean hasUssrCertificate;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется диплом об образовании, полученный в РФ",
            defaultValue = "null")
    private boolean hasRussianDiploma;
}