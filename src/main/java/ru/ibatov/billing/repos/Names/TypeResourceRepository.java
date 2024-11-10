package ru.ibatov.billing.repos.Names;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.Names.TypeResource;

import java.util.List;

@Repository
public interface TypeResourceRepository extends JpaRepository<TypeResource, Integer> {

    @Modifying
    @Query(value = "UPDATE type_resource SET name = ?2 WHERE id = ?1", nativeQuery = true)
    void updateById(int id, String name);

}