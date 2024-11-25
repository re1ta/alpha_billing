package ru.ibatov.billing.dto;

import lombok.Data;

@Data
public class EmailCode {

    private String email;

    private String code;
}
