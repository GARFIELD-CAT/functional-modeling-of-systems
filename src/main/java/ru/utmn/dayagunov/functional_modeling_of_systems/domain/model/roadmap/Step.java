package ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Step {
    private String title;
    private LocalDate deadline = LocalDate.now();
    private String message;

    public Step(String title, String message) {
        Step step = new Step();
        step.title = title;
        step.message = message;
    }
}
