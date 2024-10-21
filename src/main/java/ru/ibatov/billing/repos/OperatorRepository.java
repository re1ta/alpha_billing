package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
}