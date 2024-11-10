package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.UserTarif;

import java.util.List;

@Repository
public interface UserTarifRepository extends JpaRepository<UserTarif, Long> {

    @Modifying
    @Query(value = "Select * From User_Service Where id_phone = ?1", nativeQuery = true)
    List<UserTarif> findByPhoneId(int id);
}