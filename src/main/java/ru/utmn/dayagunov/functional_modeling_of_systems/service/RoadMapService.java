package ru.utmn.dayagunov.functional_modeling_of_systems.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.RoadMap;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map.Step;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.user.User;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.road_map.RoadMapRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.road_map.RuleRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.road_map.StepRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.user.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoadMapService {
    private final RoadMapRepository roadMapRepository;
    private final RuleRepository ruleRepository;
    private final StepRepository stepRepository;
    private final UserRepository userRepository;

    public RoadMapService(RoadMapRepository roadMapRepository, RuleRepository ruleRepository, StepRepository stepRepository, UserRepository userRepository) {
        this.roadMapRepository = roadMapRepository;
        this.ruleRepository = ruleRepository;
        this.stepRepository = stepRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public RoadMap createRoadMap(User user) {
        if (user.getRoadMap() != null) {
            roadMapRepository.deleteById(user.getRoadMap().getId());
        }

        RoadMap roadMap = new RoadMap();

        List<Rule> rules = ruleRepository.findAll();
        List<Step> steps = new ArrayList<>();

        for (var rule : rules) {
            String actionRequired = rule.getActionRequired();
            String requiredOutcome = rule.getRequiredOutcome();
            Integer period = rule.getPeriod();

            Step step = new Step();
            LocalDate deadline;

            if (user.getCheckInDate() == null) {
                deadline = LocalDate.now().plusDays(period);
            } else {
                deadline = user.getCheckInDate().plusDays(period);
            }

            String description = String.format("Что нужно получить: %s\nЧто нужно сделать: %s\nКрайний срок: %s", requiredOutcome, actionRequired, deadline);

            step.setDeadline(deadline);
            step.setDescription(description);
            step.setRule(rule);
//            step.setRoadMap(roadMap);

            steps.add(step);
        }

        roadMapRepository.save(roadMap);
        stepRepository.saveAll(steps);
        roadMap.setSteps(steps);
        roadMapRepository.save(roadMap);
        user.setRoadMap(roadMap);
        userRepository.save(user);

        return roadMap;
    }

    public RoadMap getRoadMap(Integer id) {
        return roadMapRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Дорожная карта с таким id=%d не существует", id)
                )
        );
    }
}
