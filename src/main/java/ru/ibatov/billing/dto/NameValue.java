package ru.ibatov.billing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NameValue {

    private String name;

    private float value;

    public NameValue(String name, float value) {
        this.name = name;
        this.value = value;
    }
}
