package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.RemainTariff;

@Repository
public interface RemainTariffRepository extends JpaRepository<RemainTariff, Long> {
}