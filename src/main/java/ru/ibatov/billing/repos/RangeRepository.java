package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.Range;

@Repository
public interface RangeRepository extends JpaRepository<Range, Long> {
}