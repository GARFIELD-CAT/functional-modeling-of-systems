package ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoadMap {
    private List<Step> steps = new ArrayList<>();
}
