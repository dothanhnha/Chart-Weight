package com.example.weightchart;

public enum UnitWeightMeasure {

    WEIGHT {
        public String toString() {
            return "kg";
        }
    },

    BMI {
        public String toString() {
            return "";
        }
    },
    BODY_FAT {
        public String toString() {
            return "%p";
        }
    },
    MUSCLE_MASS {
        public String toString() {
            return "kg";
        }
    },
    BODY_MOISTURE {
        public String toString() {
            return "%p";
        }
    };
}
