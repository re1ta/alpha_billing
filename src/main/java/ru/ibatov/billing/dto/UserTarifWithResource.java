package ru.ibatov.billing.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class UserTarifWithResource {

    private ArrayList<NameValue> tarifValues;

    private ArrayList<NameValue> CurrentTarifValue;

    private float money;
}
