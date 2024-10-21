package ru.ibatov.billing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
