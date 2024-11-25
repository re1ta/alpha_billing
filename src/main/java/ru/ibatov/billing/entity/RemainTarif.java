package ru.ibatov.billing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "remainTarif")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemainTarif {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int id_phone;

    private float internet;

    private float minutes;

    private int sms;

    public RemainTarif(int id_phone, float internet, float minutes, int sms) {
        this.id_phone = id_phone;
        this.internet = internet;
        this.minutes = minutes;
        this.sms = sms;
    }
}
