package ru.ibatov.billing.entity.Names;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typeResource")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeResource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
}
