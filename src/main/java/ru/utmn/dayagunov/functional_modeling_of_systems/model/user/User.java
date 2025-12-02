package ru.utmn.dayagunov.functional_modeling_of_systems.model.user;

import jakarta.persistence.*;
import lombok.Data;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.RoadMap;

import java.time.LocalDateTime;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @Column(unique = true, nullable = false)
    String login;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    LocalDateTime checkInDate;

    @ManyToOne
    @JoinColumn(name = "purpose_of_visit_id", nullable = false)
    PurposeOfVisit purposeOfVisit;

    @Column(nullable = false)
    Integer plannedDurationOfStay;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    Country countryOfCitizenship;

    Boolean healthInsurancePolicyAvailable = false;

    Boolean medicalExaminationResultAvailable = false;

    @OneToOne
    @JoinColumn(name = "road_map_id")
    RoadMap roadMap;
}
