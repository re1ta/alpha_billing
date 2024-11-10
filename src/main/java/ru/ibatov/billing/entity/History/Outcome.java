package ru.ibatov.billing.entity.History;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "outcome")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Outcome {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int id_phone;

    private float money;

    private String reason;

    private Date date;

    public Outcome(int id_phone, float money, String reason, Date date) {
        this.id_phone = id_phone;
        this.money = money;
        this.reason = reason;
        this.date = date;
    }
}
