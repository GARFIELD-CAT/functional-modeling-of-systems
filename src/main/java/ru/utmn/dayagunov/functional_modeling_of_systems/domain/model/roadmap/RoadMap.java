package ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "steps")
@EqualsAndHashCode(of = "id")
@Table(name = "roadmaps")
public class RoadMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "roadmap_id")
    private List<Step> steps = new ArrayList<>();
}
