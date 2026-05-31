package ru.utmn.dayagunov.functional_modeling_of_systems.model.condition;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

@NoArgsConstructor
public final class Comparisons {
    public static int compare(Object actual, String expected) {
        if (actual == null) {
            throw new IllegalStateException(
                    "Невозможно сравнить null с '" + expected + "'");
        }
        if (expected == null) {
            throw new IllegalArgumentException(
                    "Ожидаемое значение не задано для сравнения с " + actual);
        }

        // Числа: Integer, Long, Double, BigDecimal и т. п.
        if (actual instanceof Number n) {
            try {
                return Double.compare(n.doubleValue(), Double.parseDouble(expected));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Ожидаемое значение '" + expected + "' не является числом", e);
            }
        }

        // Дата (ISO: yyyy-MM-dd)
        if (actual instanceof LocalDate d) {
            try {
                return d.compareTo(LocalDate.parse(expected));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                        "Ожидаемая дата '" + expected + "' не в формате yyyy-MM-dd", e);
            }
        }

        // Строки — лексикографически
        if (actual instanceof String s) {
            return s.compareTo(expected);
        }

        throw new IllegalStateException(
                "Тип " + actual.getClass().getSimpleName() +
                        " не поддерживает сравнение операторами GT/LT/GTE/LTE");
    }

    public static boolean containsIn(Object actual, String expected) {
        if (actual == null) {return false;}
        if (expected == null || expected.isBlank()) {return false;}

        String actualStr = String.valueOf(actual);
        return Arrays.stream(expected.split(","))
                .map(String::trim)
                .anyMatch(actualStr::equals);
    }
}