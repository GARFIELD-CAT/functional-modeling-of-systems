package ru.utmn.dayagunov.functional_modeling_of_systems.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Migrant;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.roadmap.Step;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.RuleSubject;
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

    @Transactional(readOnly = true)
    public RoadMap createRoadMap() {
        Migrant migrant = migrantService.getCurrentMigrant();
        RoadMap roadMap = new RoadMap();
        roadMap.setSteps(createSteps(migrant));

        return roadMap;
    }

    private List<Step> createSteps(RuleSubject subject) {
        List<Rule> rules = ruleRepository.findEffectiveOn(LocalDate.now());
        Map<String, Step> messages = new LinkedHashMap<>();

        for (Rule rule : rules) {
            if (!rule.matches(subject)) {
                continue;
            }

            LocalDate deadline = rule.calculateDeadline(subject);
            String message = generateMessage(rule, deadline);
            Step candidate = new Step(rule.getTitle(), deadline, message);

            // Если одинаковые сообщения у шагов, то оставляем шаг с ближайшим дедлайном
            String key = String.format("%s: %s", rule.getRequiredAction(), rule.getRequiredResult());
            messages.merge(key, candidate, (existing, incoming) ->
                    incoming.getDeadline().isBefore(existing.getDeadline()) ? incoming : existing);
        }

        List<Step> steps = new ArrayList<>(messages.values());

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