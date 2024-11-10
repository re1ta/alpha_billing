package ru.ibatov.billing.dto;

import lombok.Builder;
import lombok.Data;
import ru.ibatov.billing.entity.Tarif;

import java.util.List;

@Data
@Builder
public class SortedTarifs {

    private List<Tarif> internetList;

    private List<Tarif> minuteList;

    private Tarif smsList;
}
