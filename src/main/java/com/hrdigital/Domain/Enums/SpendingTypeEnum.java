package com.hrdigital.Domain.Enums;

public enum SpendingTypeEnum {
    MATRICULA("Matrícula"),
    MENSALIDADE("Mensalidade"),
    EXTRA_SPENDING("Despesa Extra"),
    OTHER("Outro");

    private final String description;

    SpendingTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
