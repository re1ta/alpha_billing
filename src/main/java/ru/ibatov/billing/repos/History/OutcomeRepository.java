package ru.ibatov.billing.repos.History;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.dto.InOutcomePhone;
import ru.ibatov.billing.entity.History.Income;
import ru.ibatov.billing.entity.History.Outcome;

import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {

    @Modifying
    @Query(value = "Select * From Outcome Where id_phone = ?1", nativeQuery = true)
    List<Outcome> findOutcomesByIdPhone(int id_phone);

    @Modifying
    @Query(value = "Select new ru.ibatov.billing.dto.InOutcomePhone(b.number, avg(a.money)) From Outcome a Left Join Phone b on a.id_phone = b.id Where a.id_phone = ?1 group by b.number")
    List<InOutcomePhone> findAvgOutcome(int id_phone);

    @Query(value = "Select avg(a.money) From Outcome a")
    double findAllAvgOutcome();
}