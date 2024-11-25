package ru.ibatov.billing.entity.People;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "code")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String code;

    private Instant expireTime;
}
