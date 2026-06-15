package ru.utmn.dayagunov.functional_modeling_of_systems.web.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap.Step;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.roadmap.RoadMapResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.roadmap.StepResponseDto;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class RoadMapMapper {

    public RoadMapResponseDto toResponseDto(RoadMap roadMap) {
        RoadMapResponseDto dto = new RoadMapResponseDto();
        BeanUtils.copyProperties(roadMap, dto, "steps");

        List<StepResponseDto> steps = Optional.ofNullable(roadMap.getSteps())
                .orElse(List.of()).stream()
                .sorted(Comparator.comparing(
                        Step::getDeadline,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .map(this::toStepDto)
                .toList();

        dto.setSteps(steps);

        return dto;
    }

    private StepResponseDto toStepDto(Step step) {
        StepResponseDto dto = new StepResponseDto();
        BeanUtils.copyProperties(step, dto);

        return dto;
    }
}