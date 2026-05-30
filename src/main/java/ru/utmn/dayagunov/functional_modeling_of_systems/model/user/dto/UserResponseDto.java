package ru.utmn.dayagunov.functional_modeling_of_systems.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String login;
}
