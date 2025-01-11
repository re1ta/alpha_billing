package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.UserTarif;

import java.util.List;

@Repository
public interface UserTarifRepository extends JpaRepository<UserTarif, Long> {

    @Modifying
    @Query(value = "Select * From User_Service Where id_phone = ?1", nativeQuery = true)
    List<UserTarif> findByPhoneId(int id);

    @Modifying
    @Query(value = "DELETE FROM User_Service WHERE id_phone = ?1",nativeQuery = true)
    void deleteByIdPhone(int id_phone);

    @Query(value = "Select count(*) From UserTarif a Where (a.id_service >= 1 and a.id_service <= 3) and (a.value >= :from and a.value <= :to)")
    int countProcInternet(@Param("from") int from, @Param("to") int to);

    @Query(value = "Select count(*) From UserTarif a Where (a.id_service >= 1 and a.id_service <= 3)")
    int countInternetUsers();
}