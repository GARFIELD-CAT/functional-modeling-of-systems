package ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.Condition;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.Operators;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.OwnedByUser;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;

import java.lang.reflect.Field;
import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "migrants")
public class Migrant implements OwnedByUser {
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "road_map_id")
    private RoadMap roadMap;

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

    private boolean check(Condition condition) {
        Object actual = readProperty(condition.getField());

        return condition.getOperator().check(actual, condition.getValue());    }

    private Object readProperty(String path) {
        try {
            return new BeanWrapperImpl(this).getPropertyValue(path);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Не удалось прочитать поле '" + path + "' у мигранта id=" + id, e);
        }
    }
}
