package ru.ibatov.billing.dto;

import lombok.Data;

@Data
public class UserDto {

    private String name;

    private String surname;

    private String patronymic;

    private String email;
}
