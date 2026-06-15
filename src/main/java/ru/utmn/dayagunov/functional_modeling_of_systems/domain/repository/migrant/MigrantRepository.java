package ru.utmn.dayagunov.functional_modeling_of_systems.domain.repository.migrant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.utmn.dayagunov.functional_modeling_of_systems.domain.model.migrant.Migrant;

import java.util.Optional;


public interface MigrantRepository extends JpaRepository<Migrant, Integer> {
    @Query("SELECT m FROM Migrant m JOIN FETCH m.user WHERE m.user.login = :login")
    Optional<Migrant> findByUserLogin(String login);
}
