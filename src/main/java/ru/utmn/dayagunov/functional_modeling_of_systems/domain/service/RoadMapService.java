package ru.utmn.dayagunov.functional_modeling_of_systems.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Migrant;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap.Step;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.roadmap.RoadMapRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.roadmap.StepRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.rule.RuleRepository;

import java.time.LocalDate;
import java.util.*;

import static ru.utmn.dayagunov.functional_modeling_of_systems.infrastructure.util.Constants.WARNING_MESSAGE_DOCUMENTS_DEADLINE_EXPIRED;
import static ru.utmn.dayagunov.functional_modeling_of_systems.infrastructure.util.Constants.WARNING_MESSAGE_UNABLE_TO_CREATE_ROADMAP;


@Service
@RequiredArgsConstructor
public class RoadMapService {
    private final RoadMapRepository roadMapRepository;
    private final RuleRepository ruleRepository;
    private final StepRepository stepRepository;
    private final MigrantService migrantService;

    @Transactional
    public RoadMap createRoadMap() {
        Migrant migrant = migrantService.getCurrentMigrant();

        //TODO удалить. Карты в базе не храним!!!!
        if (migrant.getRoadMap() != null) {
            migrant.setRoadMap(null);
            migrantService.save(migrant);
        }

        RoadMap roadMap = new RoadMap();
        roadMap.setSteps(createSteps(migrant));
        roadMapRepository.save(roadMap);

        migrant.setRoadMap(roadMap);
        migrantService.save(migrant);

        return roadMap;
    }

    @Transactional(readOnly = true)
    public RoadMap getRoadMap(Integer id) {
        return roadMapRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Дорожная карта с id=%d не существует", id)
                )
        );
    }

    private List<Step> createSteps(Migrant migrant) {
        List<Rule> rules = ruleRepository.findEffectiveOn(LocalDate.now());
        List<Step> steps = new ArrayList<>();
        Set<String> seenMessages = new HashSet<>();

        for (Rule rule : rules) {
            if (!rule.matches(migrant)) {
                continue;
            }

            LocalDate deadline = rule.deadlineFor(migrant);
            String message = generateMessage(rule, deadline);

            if (seenMessages.add(message)) {
                steps.add(Step.fromRule(rule, deadline, message));
            }
        }

        if (steps.isEmpty()) {
            steps.add(Step.notice("Дорожная карта", WARNING_MESSAGE_UNABLE_TO_CREATE_ROADMAP));
        }

        stepRepository.saveAll(steps);

        return steps;
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
}