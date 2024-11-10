package ru.ibatov.billing.entity.History;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "income")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int id_phone;

    private float money;

    private String source;

    private Date date;
}
