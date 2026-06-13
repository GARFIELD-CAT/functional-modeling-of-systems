package ru.utmn.dayagunov.functional_modeling_of_systems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.Condition;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant.Migrant;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.Step;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.dto.RoadMapResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.dto.StepResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.migrant.MigrantRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.road_map.RoadMapRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.road_map.StepRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.rule.RuleRepository;

import java.time.LocalDate;
import java.util.*;

import static ru.utmn.dayagunov.functional_modeling_of_systems.Constants.WARNING_MESSAGE_DOCUMENTS_DEADLINE_EXPIRED;
import static ru.utmn.dayagunov.functional_modeling_of_systems.Constants.WARNING_MESSAGE_UNABLE_TO_CREATE_ROADMAP;


@Service
@RequiredArgsConstructor
public class RoadMapService {
    private final RoadMapRepository roadMapRepository;
    private final RuleRepository ruleRepository;
    private final StepRepository stepRepository;
    private final MigrantRepository migrantRepository;
    private final UserService userService;

    @Transactional
    public RoadMap createRoadMap() {
        String userLogin = userService.getCurrentUserLogin();
        Optional<Migrant> result = migrantRepository.findByUserLogin(userLogin);

        if (result.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Профиль мигранта для текущего пользователя не найден."
            );
        }

        Migrant migrant = result.get();

        if (migrant.getRoadMap() != null) {
            roadMapRepository.deleteById(migrant.getRoadMap().getId());
            migrant.setRoadMap(null);
        }

        List<Step> steps = createRoadMapSteps(migrant);
        RoadMap roadMap = new RoadMap();
        roadMap.setSteps(steps);
        roadMapRepository.save(roadMap);
        migrant.setRoadMap(roadMap);
        migrantRepository.save(migrant);

        return roadMap;
    }

    private List<Step> createRoadMapSteps(Migrant migrant) {
        List<Rule> rules = ruleRepository.findEffectiveOn(LocalDate.now());
        List<Step> steps = new ArrayList<>();
        HashSet<String> allMessages = new HashSet<>();

        for (var rule : rules) {
            List<Condition> conditions = rule.getConditions();
            boolean isAllConditionsPassed = true;

            for (var condition : conditions) {
                if (!migrant.check(condition)) {
                    isAllConditionsPassed = false;
                    break;
                }
            }

            if (!isAllConditionsPassed) {
                continue;
            }

            LocalDate deadline = calculateDeadline(rule, migrant);
            String message = generateMessage(rule, deadline);

            if (allMessages.contains(message)) {
                continue;
            }

            Step step = new Step();
            step.setDeadline(deadline);
            step.setMessage(message);
            step.setRule(rule);

            steps.add(step);
            allMessages.add(message);
        }

        if (steps.isEmpty()) {
            Step step = new Step();
            step.setMessage(WARNING_MESSAGE_UNABLE_TO_CREATE_ROADMAP);
            steps.add(step);
        }

        stepRepository.saveAll(steps);

        return steps;
    }

    @Transactional(readOnly = true)
    public RoadMap getRoadMap(Integer id) {
        return roadMapRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Дорожная карта с id=%d не существует", id)
                )
        );
    }

    public RoadMapResponseDto prepareRoadMapResponseDto(RoadMap roadMap) {
        RoadMapResponseDto dto = new RoadMapResponseDto();
        BeanUtils.copyProperties(roadMap, dto);

        List<StepResponseDto> steps = Optional.ofNullable(roadMap.getSteps())
                .orElse(List.of()).stream()
                .sorted(Comparator.comparing(
                        Step::getDeadline,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .map(this::prepareStepResponseDto)
                .toList();

        dto.setSteps(steps);

        return dto;
    }

    private StepResponseDto prepareStepResponseDto(Step step) {
        StepResponseDto dto = new StepResponseDto();
        BeanUtils.copyProperties(step, dto);

        return dto;
    }

    private String generateMessage(Rule rule, LocalDate deadline) {
        String message = String.format(
                "Что нужно получить: %s\nЧто нужно сделать: %s\nКрайний срок: %s",
                rule.getRequiredResult(), rule.getRequiredAction(), deadline
        );

        if (deadline.isBefore(LocalDate.now())) {
            message = WARNING_MESSAGE_DOCUMENTS_DEADLINE_EXPIRED + '\n' + message;
        }

        return message;
    }

    private LocalDate calculateDeadline(Rule rule, Migrant migrant) {
        LocalDate deadline;
        Integer period = rule.getPeriod();
        LocalDate entryDate = migrant.getEntryDate();

        if (entryDate == null) {
            deadline = LocalDate.now().plusDays(period);
        } else {
            deadline = entryDate.plusDays(period);
        }

        return deadline;
    }
}
