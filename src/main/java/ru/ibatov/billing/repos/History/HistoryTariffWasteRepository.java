package ru.ibatov.billing.repos.History;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.History.HistoryTariffWaste;

@Repository
public interface HistoryTariffWasteRepository extends JpaRepository<HistoryTariffWaste, Long> {
}