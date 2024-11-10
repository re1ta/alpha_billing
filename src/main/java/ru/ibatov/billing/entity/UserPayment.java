package ru.ibatov.billing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "userPayment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int id_phone;

    private float money;

    private Date dayPayment;
}
