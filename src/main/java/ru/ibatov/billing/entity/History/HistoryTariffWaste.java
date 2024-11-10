package ru.ibatov.billing.entity.History;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "historyTariffWaste")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryTariffWaste {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int id_phone;

    private float value;

    private int id_typeResource;

    private Date date;

    public HistoryTariffWaste(int id_phone, float value, int id_typeResource, Date date) {
        this.id_phone = id_phone;
        this.value = value;
        this.id_typeResource = id_typeResource;
        this.date = date;
    }
}
