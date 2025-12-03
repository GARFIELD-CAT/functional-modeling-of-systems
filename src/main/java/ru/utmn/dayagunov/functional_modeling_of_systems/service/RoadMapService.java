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
import java.util.*;

import static ru.utmn.dayagunov.functional_modeling_of_systems.Constants.*;


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
            user.setRoadMap(null);
        }

        List<Step> steps = createRoadMapStep(user);
        RoadMap roadMap = new RoadMap();

        stepRepository.saveAll(steps);
        roadMap.setSteps(steps);
        roadMapRepository.save(roadMap);
        user.setRoadMap(roadMap);
        userRepository.save(user);

        return roadMap;
    }

    private List<Step> createRoadMapStep(User user) {
        List<Rule> rules = ruleRepository.findAll();
        List<Step> steps = new ArrayList<>();
        HashSet<String> addedOutcome = new HashSet<>();

        if (user.getHealthInsurancePolicyAvailable()) {
            addedOutcome.add(HEALTH_INSURANCE_POLICY_MARKER);
        }

        if (user.getMedicalExaminationResultAvailable()) {
            addedOutcome.add(MEDICAL_EXAMINATION_RESULT_MARKER);
        }

        for (var rule : rules) {
            String actionRequired = rule.getActionRequired();
            String requiredOutcome = rule.getRequiredOutcome();
            Integer period = rule.getPeriod();

            if (addedOutcome.contains(rule.getRequiredOutcome())) {
                continue;
            }

            if (Objects.equals(requiredOutcome, MEDICAL_EXAMINATION_RESULT_MARKER)) {
                if (Objects.equals(user.getPurposeOfVisit().getId(), WORK_ACTIVITY_ID)) {
                    period = 30;
                } else if ((user.getPlannedDurationOfStay() > 90) && (rule.getPurposeOfVisit() == null)) {
                    period = 90;
                } else {
                    continue;
                }
            } else if (Objects.equals(requiredOutcome, HEALTH_INSURANCE_POLICY_MARKER)) {
                if (!Objects.equals(user.getPurposeOfVisit().getId(), WORK_ACTIVITY_ID)) {
                    continue;
                }

                if (!Arrays.asList(rule.getCountries()).contains(user.getCountryOfCitizenship().getName())) {
                    continue;
                }

                period = 30;
            }

            Step step = new Step();
            LocalDate deadline;

            if (user.getCheckInDate() == null) {
                deadline = LocalDate.now().plusDays(period);
            } else {
                deadline = user.getCheckInDate().plusDays(period);
            }

            String description = String.format(
                    "Что нужно получить: %s\nЧто нужно сделать: %s\nКрайний срок: %s",
                    requiredOutcome, actionRequired, deadline
            );

            if (deadline.isBefore(LocalDate.now())) {
                description = WARNING_MESSAGE_DOCUMENTS_DEADLINE_EXPIRED + '\n' + description;
            }

            step.setDeadline(deadline);
            step.setDescription(description);
            step.setRule(rule);

            steps.add(step);
            addedOutcome.add(rule.getRequiredOutcome());
        }

        if (steps.isEmpty()) {
            Step step = new Step();
            step.setDescription(WARNING_MESSAGE_UNABLE_TO_CREATE_ROADMAP);
            steps.add(step);
        }

        return steps;
    }


    public RoadMap getRoadMap(Integer id) {
        return roadMapRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Дорожная карта с таким id=%d не существует", id)
                )
        );
    }
}
