package ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.dto;

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
public class CreateMigrantRequestBodyDto {
    @Schema(requiredMode = REQUIRED,
            description = "Дата въезда в РФ",
            example = "2026-01-01")
    @NotNull(message = "Дата въезда должна быть указана.")
    @PastOrPresent(message = "Дата въезда не может быть в будущем.")
    private LocalDate entryDate;

    @Schema(requiredMode = REQUIRED,
            description = "Планируемый срок пребывания в днях",
            example = "90",
            minimum = "1")
    @NotNull(message = "Планируемый срок пребывания должен быть указан.")
    @Positive(message = "Срок пребывания должен быть положительным числом.")
    private Integer plannedDurationOfStay;

    @Schema(requiredMode = REQUIRED,
            description = "ID страны гражданства",
            example = "1")
    @NotNull(message = "Страна гражданства должна быть указана.")
    private Integer countryOfCitizenshipId;

    @Schema(requiredMode = REQUIRED,
            description = "ID цели въезда",
            example = "1")
    @NotNull(message = "Цель въезда должна быть указана.")
    private Integer purposeOfVisitId;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Высококвалифицированный специалист или член его семьи",
            defaultValue = "false")
    private boolean hqsOrFamilyMember = false;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдена дактилоскопия в РФ",
            defaultValue = "false")
    private boolean hasFingerprinting = false;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдено фотографирование в РФ",
            defaultValue = "false")
    private boolean hasPhotoRegistration = false;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Состояли ли ранее на миграционном учёте",
            defaultValue = "false")
    private boolean hasMigrationRegistration = false;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется полис ОМС или ДМС",
            defaultValue = "false")
    private boolean hasMedicalInsurance = false;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдено медицинское освидетельствование",
            defaultValue = "false")
    private boolean hasMedicalExamination = false;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется действующий сертификат о владении русским языком",
            defaultValue = "false")
    private boolean hasRussianLanguageCert = false;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется ИНН, полученный в РФ",
            defaultValue = "false")
    private boolean hasInn = false;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Участник госпрограммы переселения соотечественников",
            defaultValue = "false")
    private boolean resettlementParticipant = false;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется аттестат об образовании СССР до 1991 года",
            defaultValue = "false")
    private boolean hasUssrCertificate = false;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется диплом об образовании, полученный в РФ",
            defaultValue = "false")
    private boolean hasRussianDiploma = false;
}