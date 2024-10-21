package ru.ibatov.billing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
