package ru.ibatov.billing.repos.History;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.History.Income;
import ru.ibatov.billing.entity.History.Outcome;

import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {

    @Modifying
    @Query(value = "Select * From Outcome Where id_phone = ?1", nativeQuery = true)
    List<Outcome> findOutcomesByIdPhone(int id_phone);
}