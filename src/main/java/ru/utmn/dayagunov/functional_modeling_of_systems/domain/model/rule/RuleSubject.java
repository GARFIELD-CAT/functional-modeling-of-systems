package ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule;

import java.time.LocalDate;

public interface RuleSubject {
    Object getFieldValue(String field);
    LocalDate getEntryDate();
}