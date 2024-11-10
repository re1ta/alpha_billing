package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.Phone;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

    @Modifying
    @Query(value = "Select * From Phone Where id_user = ?1", nativeQuery = true)
    List<Phone> findAllByUserId(Long id);

    @Modifying
    @Query(value = "Update Phone Set balance = ?1 Where id = ?2", nativeQuery = true)
    void changeBalance(float balance, int id_phone);

    @Modifying
    @Query(value = "Update Phone Set active = ?2 Where id = ?1", nativeQuery = true)
    void setActive(int id_phone, boolean active);
}