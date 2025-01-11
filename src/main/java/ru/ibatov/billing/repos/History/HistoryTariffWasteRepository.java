package ru.ibatov.billing.repos.History;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.dto.TarifWaste.AvgAllTarifWasteDto;
import ru.ibatov.billing.dto.TarifWaste.AvgTarifWasteDto;
import ru.ibatov.billing.entity.History.HistoryTariffWaste;

import java.util.List;

@Repository
public interface HistoryTariffWasteRepository extends JpaRepository<HistoryTariffWaste, Long> {

    @Modifying
    @Query(value = "Select new ru.ibatov.billing.dto.TarifWaste.AvgTarifWasteDto(c.number, avg(a.value), b.name) From HistoryTariffWaste a Left Join TypeResource b on a.id_typeResource = b.id Left Join Phone c on a.id_phone = c.id Where a.id_phone = ?1 group by c.number, b.name")
    List<AvgTarifWasteDto> findAvgTarifWaste(int id_phone);

    @Modifying
    @Query(value = "Select new ru.ibatov.billing.dto.TarifWaste.AvgAllTarifWasteDto(avg(a.value), b.name) From HistoryTariffWaste a Left Join TypeResource b on a.id_typeResource = b.id group by b.name")
    List<AvgAllTarifWasteDto> findAllAvgTarifWaste();

    @Query(value = "Select COALESCE(avg(value),0) From HistoryTariffWaste Where id_typeResource = :res and MONTH(date) = :month")
    float avgUseResource(@Param("month") int month, @Param("res") int resource);
}