package ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.roadmap;

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
