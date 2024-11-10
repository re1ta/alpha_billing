package ru.ibatov.billing.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.Tarif;
import ru.ibatov.billing.entity.UserTarif;

import java.util.List;

@Repository
public interface TarifRepository extends JpaRepository<Tarif, Long> {

    @Modifying
    @Query(value = "UPDATE Service SET id_range = ?2, id_typeResource = ?3, coefficient = ?4 WHERE id = ?1", nativeQuery = true)
    void updateById(Long id, int id_range, int id_typeResource, float coefficient);

    @Query(value = "Select * From Service Where id_type_resource = 1 order by min ASC", nativeQuery = true)
    List<Tarif> getInternetTarif();

    @Query(value = "Select * From Service Where id_type_resource = 2", nativeQuery = true)
    Tarif getSmsTarif();

    @Query(value = "Select * From Service Where id_type_resource = 3 order by min ASC", nativeQuery = true)
    List<Tarif> getMinuteTarif();

    @Query(value = "Select new ru.ibatov.billing.entity.Tarif(a.id, a.id_range, a.id_typeResource, a.min, a.max, a.coefficient) From Tarif a Where a.id = :id")
    Tarif getTarif(@Param("id") Long id);

}