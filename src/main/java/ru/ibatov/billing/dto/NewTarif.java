package ru.ibatov.billing.dto;

import lombok.Data;

@Data
public class NewTarif {

    private int id_phone;

    private int internet;

    private int sms;

    private int minutes;

    private float money;
}
