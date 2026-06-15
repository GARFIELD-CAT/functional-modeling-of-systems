package ru.utmn.dayagunov.functional_modeling_of_systems.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Migrant;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap.Step;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.rule.RuleRepository;

import java.time.LocalDate;
import java.util.*;

import static ru.utmn.dayagunov.functional_modeling_of_systems.infrastructure.util.Constants.WARNING_MESSAGE_DOCUMENTS_DEADLINE_EXPIRED;
import static ru.utmn.dayagunov.functional_modeling_of_systems.infrastructure.util.Constants.WARNING_MESSAGE_UNABLE_TO_CREATE_ROADMAP;


@Service
@RequiredArgsConstructor
public class RoadMapService {
    private final RuleRepository ruleRepository;
    private final MigrantService migrantService;

    public RoadMap createRoadMap() {
        Migrant migrant = migrantService.getCurrentMigrant();
        RoadMap roadMap = new RoadMap();
        roadMap.setSteps(createSteps(migrant));

        return roadMap;
    }

    private List<Step> createSteps(Migrant migrant) {
        List<Rule> rules = ruleRepository.findEffectiveOn(LocalDate.now());
        List<Step> steps = new ArrayList<>();
        Set<Integer> seenRules = new HashSet<>();

        for (Rule rule : rules) {
            if (!rule.matches(migrant)) {
                continue;
            }

            LocalDate deadline = rule.calculateDeadline(migrant);
            String message = generateMessage(rule, deadline);

            if (seenRules.add(rule.getId())) {
                steps.add(new Step(rule.getTitle(), deadline, message));
            }
        }

        if (steps.isEmpty()) {
            steps.add(new Step("Дорожная карта", WARNING_MESSAGE_UNABLE_TO_CREATE_ROADMAP));
        }

        return steps;
    }

    private String generateMessage(Rule rule, LocalDate deadline) {
        String message = String.format(
                "Что нужно получить: %s\nЧто нужно сделать: %s",
                rule.getRequiredResult(), rule.getRequiredAction()
        );

        if (deadline.isBefore(LocalDate.now())) {
            message = WARNING_MESSAGE_DOCUMENTS_DEADLINE_EXPIRED + '\n' + message;
        }

        return message;
    }
}