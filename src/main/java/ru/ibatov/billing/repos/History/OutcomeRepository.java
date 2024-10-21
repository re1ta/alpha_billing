package ru.ibatov.billing.repos.History;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.History.Outcome;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {
}