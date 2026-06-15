package ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.migrant;

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
            description = "Высококвалифицированный специалист или член его семьи")
    private Boolean hqsOrFamilyMember;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдена дактилоскопия в РФ")
    private Boolean hasFingerprinting;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдено фотографирование в РФ")
    private Boolean hasPhotoRegistration;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Состояли ли ранее на миграционном учёте")
    private Boolean hasMigrationRegistration;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется полис ОМС или ДМС")
    private Boolean hasMedicalInsurance;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Пройдено медицинское освидетельствование")
    private Boolean hasMedicalExamination;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется действующий сертификат о владении русским языком")
    private Boolean hasRussianLanguageCert;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется ИНН, полученный в РФ")
    private Boolean hasInn;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Участник госпрограммы переселения соотечественников")
    private Boolean resettlementParticipant;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется аттестат об образовании СССР до 1991 года")
    private Boolean hasUssrCertificate;

    @Schema(requiredMode = NOT_REQUIRED,
            description = "Имеется диплом об образовании, полученный в РФ")
    private Boolean hasRussianDiploma;
}