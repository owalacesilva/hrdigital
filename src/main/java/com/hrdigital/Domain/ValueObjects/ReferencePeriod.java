package com.hrdigital.Domain.ValueObjects;

public class ReferencePeriod {
    private final String value;

    private ReferencePeriod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ReferencePeriod of(String value) {
        // Implement validation logic for the reference period format (e.g., "2023-01" for January 2023)
        if (value == null || !value.matches("\\d{4}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid reference period format. Expected format: YYYY-MM");
        }
        return new ReferencePeriod(value);
    }
}
