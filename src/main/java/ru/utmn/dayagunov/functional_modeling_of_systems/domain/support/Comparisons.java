package ru.utmn.dayagunov.functional_modeling_of_systems.domain.support;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public final class Comparisons {
    public static int compare(Object actual, String expected) {
        actual = unwrapEntity(actual);

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

        // Дата (ISO: yyyy-mm-dd)
        if (actual instanceof LocalDate d) {
            try {
                return d.compareTo(LocalDate.parse(expected));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(
                        "Ожидаемая дата '" + expected + "' не в формате yyyy-mm-dd", e);
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
        if (actual == null) {
            return false;
        }
        if (expected == null || expected.isBlank()) {
            return false;
        }

        actual = unwrapEntity(actual);

        String actualStr = String.valueOf(actual);
        return Arrays.stream(expected.split(","))
                .map(String::trim)
                .anyMatch(actualStr::equals);
    }

    public static Object unwrapEntity(Object value) {
        if (value == null) return null;
        try {
            return value.getClass().getMethod("getId").invoke(value);
        } catch (NoSuchMethodException e) {
            return value;        // не сущность — оставляем как было
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Не удалось вызвать getId() у " + value, e);
        }
    }
}
