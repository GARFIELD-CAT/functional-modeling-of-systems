package ru.utmn.dayagunov.functional_modeling_of_systems.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.condition.dto.ConditionResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.dto.CreateRuleRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.dto.RuleResponseDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.model.rule.dto.UpdateRuleRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.rule.RuleRepository;

import java.util.List;
import java.util.Optional;

import static ru.utmn.dayagunov.functional_modeling_of_systems.util.BeanCopyUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final RuleRepository ruleRepository;
    private final ConditionService conditionService;

    @Transactional
    public Rule createRule(CreateRuleRequestBodyDto body) {
        Rule rule = new Rule();
        BeanUtils.copyProperties(body, rule);

        return ruleRepository.save(rule);
    }

    @Transactional
    public Rule updateRule(UpdateRuleRequestBodyDto body) {
        Rule rule = findRuleById(body.getId());
        BeanUtils.copyProperties(body, rule, getNullPropertyNames(body));
        ruleRepository.save(rule);

        return rule;
    }

    public Rule getRule(Integer id) {
        return findRuleById(id);
    }

    public List<Rule> listRules() {
        return ruleRepository.findAll();
    }

    @Transactional
    public void deleteRule(Integer id) {
        Rule rule = findRuleById(id);
        ruleRepository.delete(rule);
    }

    public RuleResponseDto prepareRuleResponseDto(Rule rule) {
        RuleResponseDto dto = new RuleResponseDto();
        BeanUtils.copyProperties(rule, dto);

        List<ConditionResponseDto> conditions = Optional.ofNullable(rule.getConditions())
                .orElse(List.of()).stream()
                .map(conditionService::prepareConditionResponseDto)
                .toList();

        dto.setConditions(conditions);

        return dto;
    }

    private Rule findRuleById(Integer id) {
        return ruleRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Правило с id=%d не найдено", id)
                )
        );
    }
}