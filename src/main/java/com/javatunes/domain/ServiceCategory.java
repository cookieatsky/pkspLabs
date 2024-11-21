package com.javatunes.domain;

public enum ServiceCategory {
    MASSAGE("Массажи"),
    FACIAL("Уход за лицом"),
    BODY_CARE("Уход за телом"),
    MANICURE("Маникюр"),
    PEDICURE("Педикюр"),
    HAIR_CARE("Уход за волосами");

    private final String description;

    private ServiceCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
