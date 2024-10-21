package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.TypeResource;

@Repository
public interface TypeResourceRepository extends JpaRepository<TypeResource, Integer> {
}