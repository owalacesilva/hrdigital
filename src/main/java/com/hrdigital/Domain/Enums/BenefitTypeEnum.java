package com.hrdigital.Domain.Enums;

public enum BenefitTypeEnum {
    AUXILIO_EDUCACAO("Auxílio Educação"),
    AUXILIO_SAUDE("Auxílio Saúde");

    private final String description;

    BenefitTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}