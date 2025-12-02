package ru.utmn.dayagunov.functional_modeling_of_systems.model.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PurposeOfVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true, nullable = false)
    String name;
}
