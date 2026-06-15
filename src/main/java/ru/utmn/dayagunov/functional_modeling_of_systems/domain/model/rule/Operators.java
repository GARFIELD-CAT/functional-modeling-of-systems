package ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.rule;

public enum Operators {
    EQ {
        @Override
        public boolean check(Object actual, String expected) {
            if (actual == null) {
                return expected == null || "null".equals(expected);
            }

            actual = Comparisons.unwrapEntity(actual);

            return String.valueOf(actual).equals(expected);
        }
    },
    NO_EQ {
        @Override
        public boolean check(Object actual, String expected) {
            return !EQ.check(actual, expected);
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