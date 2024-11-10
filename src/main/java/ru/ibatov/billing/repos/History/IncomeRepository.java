package ru.ibatov.billing.repos.History;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.History.Income;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Modifying
    @Query(value = "Select * From Income Where id_phone = ?1", nativeQuery = true)
    List<Income> findIncomesByIdPhone(int id_phone);
}