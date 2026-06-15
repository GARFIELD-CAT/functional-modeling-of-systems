package ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.roadmap;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoadMapResponseDto {
    private Integer id;
    private List<StepResponseDto> steps;
}
