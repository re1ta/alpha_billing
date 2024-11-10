package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.UserPayment;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {

    @Query(value = "Select money From UserPayment a Where a.id_phone = :id")
    float getMoneyTarif(@Param("id") int id_phone);

}
