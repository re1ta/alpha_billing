package ru.ibatov.billing.repos.History;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.dto.InOutcomePhone;
import ru.ibatov.billing.entity.History.Income;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Modifying
    @Query(value = "Select * From Income Where id_phone = ?1", nativeQuery = true)
    List<Income> findIncomesByIdPhone(int id_phone);

    @Modifying
    @Query(value = "Select new ru.ibatov.billing.dto.InOutcomePhone(b.number, avg(a.money)) From Income a Left Join Phone b on a.id_phone = b.id Where a.id_phone = ?1 group by b.number")
    List<InOutcomePhone> findAvgIncome(int id_phone);

    @Query(value = "Select avg(a.money) From Income a")
    double findAllAvgIncome();

    @Query(value = "Select count(*) From Income a Where (a.money >= :from and a.money <= :to)")
    int countUserIncome(@Param("from") float from, @Param("to") float to);
}