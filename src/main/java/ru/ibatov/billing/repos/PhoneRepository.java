package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {
}