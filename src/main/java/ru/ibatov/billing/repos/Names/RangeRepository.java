package ru.ibatov.billing.repos.Names;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.Names.Range;

@Repository
public interface RangeRepository extends JpaRepository<Range, Long> {
}