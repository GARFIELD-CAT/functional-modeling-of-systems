package ru.utmn.dayagunov.functional_modeling_of_systems.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule.Rule;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.rule.RuleRepository;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule.CreateRuleRequestBodyDto;
import ru.utmn.dayagunov.functional_modeling_of_systems.web.dto.rule.UpdateRuleRequestBodyDto;

import java.time.LocalDate;
import java.util.List;

import static ru.utmn.dayagunov.functional_modeling_of_systems.infrastructure.util.BeanCopyUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final RuleRepository ruleRepository;

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

        return ruleRepository.save(rule);
    }

    @Transactional(readOnly = true)
    public Rule getRule(Integer id) {
        return findRuleById(id);
    }

    @Transactional(readOnly = true)
    public List<Rule> listRules(boolean onlyActive) {
        if (onlyActive) {
            return ruleRepository.findEffectiveOn(LocalDate.now());
        }

        return ruleRepository.findAll();
    }

    @Transactional
    public void deleteRule(Integer id) {
        Rule rule = findRuleById(id);
        ruleRepository.delete(rule);
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