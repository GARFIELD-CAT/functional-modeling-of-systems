package ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule;

import ru.utmn.dayagunov.functional_modeling_of_systems.domain.support.Comparisons;

public enum Operators {
    EQ {
        @Override
        public boolean check(Object actual, String expected) {
            return Comparisons.equalsValue(actual, expected);
        }
    },
    NO_EQ {
        @Override
        public boolean check(Object actual, String expected) {
            return !Comparisons.equalsValue(actual, expected);
        }
    },
    GT {
        @Override
        public boolean check(Object a, String e) {
            return Comparisons.compare(a, e) > 0;
        }
    },
    LT {
        @Override
        public boolean check(Object a, String e) {
            return Comparisons.compare(a, e) < 0;
        }
    },
    GTE {
        @Override
        public boolean check(Object a, String e) {
            return Comparisons.compare(a, e) >= 0;
        }
    },
    LTE {
        @Override
        public boolean check(Object a, String e) {
            return Comparisons.compare(a, e) <= 0;
        }
    },
    IN {
        @Override
        public boolean check(Object a, String e) {
            return Comparisons.containsIn(a, e);
        }
    },
    NOT_IN {
        @Override
        public boolean check(Object a, String e) {
            return !Comparisons.containsIn(a, e);
        }
    };

    public abstract boolean check(Object actual, String expected);
}