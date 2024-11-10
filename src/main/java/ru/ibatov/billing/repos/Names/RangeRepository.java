package ru.ibatov.billing.repos.Names;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.Names.Range;
import ru.ibatov.billing.entity.Phone;

import java.util.List;

@Repository
public interface RangeRepository extends JpaRepository<Range, Long> {

    @Modifying
    @Query(value = "UPDATE Range SET name = ?2 WHERE id = ?1", nativeQuery = true)
    void updateById(Long id, String name);
}