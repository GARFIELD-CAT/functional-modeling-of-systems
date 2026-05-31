package ru.utmn.dayagunov.functional_modeling_of_systems.model.condition;

import lombok.Getter;

//@Getter
//public enum Operators {
//    EQ("EQ"), // Равно
//    NO_EQ("NO_EQ"), // Не равно
//    GT("GT"), // Больше
//    LT("LT"), // Меньше
//    GTE("GTE"), // Больше или равно
//    LTE("LTE"), // Меньше или равно
//    IN("IN"), // Содержит
//    NOT_IN("NOT_IN"); // Не содержит
//
//    private final String value;
//
//    Operators(String value) {
//        this.value = value;
//    }
//}
public enum Operators {
    EQ {
        @Override public boolean check(Object actual, String expected) {
            if (actual == null) {
                return expected == null || "null".equals(expected);
            }

            return String.valueOf(actual).equals(expected);
        }
    },
    NO_EQ {
        @Override public boolean check(Object actual, String expected) {
            return !EQ.check(actual, expected);
        }
    },
    GT  { @Override public boolean check(Object a, String e) { return Comparisons.compare(a, e) >  0; } },
    LT  { @Override public boolean check(Object a, String e) { return Comparisons.compare(a, e) <  0; } },
    GTE { @Override public boolean check(Object a, String e) { return Comparisons.compare(a, e) >= 0; } },
    LTE { @Override public boolean check(Object a, String e) { return Comparisons.compare(a, e) <= 0; } },
    IN     { @Override public boolean check(Object a, String e) { return  Comparisons.containsIn(a, e); } },
    NOT_IN { @Override public boolean check(Object a, String e) { return !Comparisons.containsIn(a, e); } };

    public abstract boolean check(Object actual, String expected);
}
