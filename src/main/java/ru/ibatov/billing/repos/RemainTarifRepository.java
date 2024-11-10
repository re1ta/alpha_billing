package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.RemainTarif;

@Repository
public interface RemainTarifRepository extends JpaRepository<RemainTarif, Long> {

    @Modifying
    @Query(value = "UPDATE Remain_Tarif SET internet = ?2, sms = ?3, minutes = ?4 WHERE id_phone = ?1", nativeQuery = true)
    void updateByIdPhone(int id_phone, float internet, int sms, float minutes);

    @Query(value = "Select new ru.ibatov.billing.entity.RemainTarif(a.id, a.id_phone, a.internet, a.minutes, a.sms) From RemainTarif a Where a.id_phone = :id")
    RemainTarif getCurrentStatus(@Param("id") int id_phone);
}