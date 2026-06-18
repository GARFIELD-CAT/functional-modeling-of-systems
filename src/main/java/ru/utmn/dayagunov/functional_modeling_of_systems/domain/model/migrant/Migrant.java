package ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanWrapperImpl;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.RuleSubjectInterface;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.OwnedByUserInterface;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.user.User;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "countryOfCitizenship", "purposeOfVisit"})
@EqualsAndHashCode(of = "id")
@Table(name = "migrants")
public class Migrant implements OwnedByUserInterface, RuleSubjectInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate entryDate;

    @Column(nullable = false)
    private Integer plannedDurationOfStay;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country countryOfCitizenship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purpose_of_visit_id", nullable = false)
    private PurposeOfVisit purposeOfVisit;

    // Миграционные статусы (Да/Нет)
    @Column(nullable = false)
    private boolean hqsOrFamilyMember = false;

    @Column(nullable = false)
    private boolean hasFingerprinting = false;

    @Column(nullable = false)
    private boolean hasPhotoRegistration = false;

    @Column(nullable = false)
    private boolean hasMigrationRegistration = false;

    @Column(nullable = false)
    private boolean hasMedicalInsurance = false;

    @Column(nullable = false)
    private boolean hasMedicalExamination = false;

    @Column(nullable = false)
    private boolean hasRussianLanguageCert = false;

    @Column(nullable = false)
    private boolean hasInn = false;

    @Column(nullable = false)
    private boolean resettlementParticipant = false;

    @Column(nullable = false)
    private boolean hasUssrCertificate = false;

    @Column(nullable = false)
    private boolean hasRussianDiploma = false;

    public Object getFieldValue(String field) {
        try {
            return new BeanWrapperImpl(this).getPropertyValue(field);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Не удалось прочитать поле '" + field + "' у мигранта id=" + id, e);
        }
    }
}
