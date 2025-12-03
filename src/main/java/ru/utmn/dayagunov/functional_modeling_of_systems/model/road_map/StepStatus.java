package ru.utmn.dayagunov.functional_modeling_of_systems.model.road_map;

public enum StepStatus {
    NOT_DONE("Не получен"),
    DONE("Получен");

    private String description;

    StepStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
