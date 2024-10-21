package ru.ibatov.billing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "userService")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long id_service;

    private int id_phone;

    private int value;

    private Date dayPayment;
}
