package ru.ibatov.billing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ibatov.billing.dto.NameValue;

import java.util.List;

@Data
@AllArgsConstructor
public class MonthNameValue {

    private String resource;
    private List<NameValue> nameValueList;
}
