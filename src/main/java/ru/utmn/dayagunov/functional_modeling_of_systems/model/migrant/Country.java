package ru.utmn.dayagunov.functional_modeling_of_systems.model.migrant;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;
}
