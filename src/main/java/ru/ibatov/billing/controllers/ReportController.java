package ru.ibatov.billing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ibatov.billing.dto.InOutcomePhone;
import ru.ibatov.billing.dto.TarifWaste.AvgAllTarifWasteDto;
import ru.ibatov.billing.dto.TarifWaste.AvgTarifWasteDto;
import ru.ibatov.billing.service.ReportService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/userAvgTarifWaste/{id_phone}")
    public List<AvgTarifWasteDto> userAvgTarifWaste(@PathVariable int id_phone) {
        return reportService.getUserAvgTarifWaste(id_phone);
    }

    @PostMapping("/allAvgTarifWaste/")
    public List<AvgAllTarifWasteDto> AllAvgTarifWaste() {
        return reportService.getAllAvgTarifWaste();
    }

    @PostMapping("/userAvgIncome/{id_phone}")
    public List<InOutcomePhone> userAvgIncome(@PathVariable int id_phone) {
        return reportService.getUserAvgIncome(id_phone);
    }

    @PostMapping("/allAvgIncome/")
    public double AllAvgIncome() {
        return reportService.getAllAvgIncome();
    }

    @PostMapping("/userAvgOutcome/{id_phone}")
    public List<InOutcomePhone> userAvgOutcome(@PathVariable int id_phone) {
        return reportService.getUserAvgOutcome(id_phone);
    }

    @PostMapping("/allAvgOutcome/")
    public double AllAvgOutcome() {
        return reportService.getAllAvgOutcome();
    }
}
