package ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map;

import jakarta.persistence.*;
import lombok.Data;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;


import java.util.List;

@Entity
@Data
public class RoadMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany(mappedBy = "roadMap", cascade = CascadeType.ALL)
    List<Step> steps;

    @OneToOne(mappedBy = "roadMap")
    User user;
}
