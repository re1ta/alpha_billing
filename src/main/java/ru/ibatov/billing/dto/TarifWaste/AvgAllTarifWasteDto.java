package ru.ibatov.billing.dto.TarifWaste;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgAllTarifWasteDto {

    private double value;

    private String name;
}