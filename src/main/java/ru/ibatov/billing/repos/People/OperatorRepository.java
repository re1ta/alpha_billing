package ru.ibatov.billing.repos.People;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.People.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
}