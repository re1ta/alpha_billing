package ru.ibatov.billing.repos.People;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.People.Code;

import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
    Optional<Code> findByEmail(String email);
}
