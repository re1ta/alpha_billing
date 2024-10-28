package ru.ibatov.billing.dto;

import lombok.Data;

@Data
public class UserPhoneDto {
    private Long userId;
    private String phoneNumber;
}
