package ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StepResponseDto {
    private String title;
    private LocalDate deadline;
    private String message;
}
