package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.Outcome;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {
}