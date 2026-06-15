package ru.utmn.dayagunov.functional_modeling_of_systems.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Migrant;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MigrantFieldValidator implements ConstraintValidator<MigrantField, String> {

    // Поля, по которым запрещено строить условия.
    private static final Set<String> FORBIDDEN_FIELDS = Set.of("id", "user");

    // Разрешённые поля = читаемые свойства Migrant
    // минус служебное "class" (от getClass()) и минус запрещённые.
    private static final Set<String> ALLOWED_FIELDS = Arrays.stream(
                    BeanUtils.getPropertyDescriptors(Migrant.class))
            .map(PropertyDescriptor::getName)
            .filter(name -> !"class".equals(name))
            .filter(name -> !FORBIDDEN_FIELDS.contains(name))
            .collect(Collectors.toUnmodifiableSet());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return ALLOWED_FIELDS.contains(value);
    }
}