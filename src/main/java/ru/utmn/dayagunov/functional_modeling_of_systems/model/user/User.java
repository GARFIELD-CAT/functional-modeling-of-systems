package ru.utmn.dayagunov.functional_modeling_of_systems.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.RoadMap;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true, nullable = false)
    String login;

    @Column(nullable = false)
    String password;

    LocalDate checkInDate;

    @ManyToOne
    @JoinColumn(name = "purpose_of_visit_id")
    PurposeOfVisit purposeOfVisit;

    Integer plannedDurationOfStay;

    @ManyToOne
    @JoinColumn(name = "country_id")
    Country countryOfCitizenship;

    Boolean healthInsurancePolicyAvailable = false;

    Boolean medicalExaminationResultAvailable = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "road_map_id")
    RoadMap roadMap;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
