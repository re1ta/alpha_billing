package ru.ibatov.billing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.dto.InOutcomePhone;
import ru.ibatov.billing.dto.TarifWaste.AvgAllTarifWasteDto;
import ru.ibatov.billing.dto.TarifWaste.AvgTarifWasteDto;
import ru.ibatov.billing.repos.History.HistoryTariffWasteRepository;
import ru.ibatov.billing.repos.History.IncomeRepository;
import ru.ibatov.billing.repos.History.OutcomeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final HistoryTariffWasteRepository htwRepo;
    private final IncomeRepository incomeRepository;
    private final OutcomeRepository outcomeRepository;

    public List<AvgTarifWasteDto> getUserAvgTarifWaste(int id_phone) {
        return htwRepo.findAvgTarifWaste(id_phone);
    }

    public List<AvgAllTarifWasteDto> getAllAvgTarifWaste() {
        return htwRepo.findAllAvgTarifWaste();
    }

    public List<InOutcomePhone> getUserAvgIncome(int id_phone) {
        return incomeRepository.findAvgIncome(id_phone);
    }

    public Double getAllAvgIncome() {
        return incomeRepository.findAllAvgIncome();
    }

    public List<InOutcomePhone> getUserAvgOutcome(int id_phone) {
        return outcomeRepository.findAvgOutcome(id_phone);
    }

    public Double getAllAvgOutcome() {
        return outcomeRepository.findAllAvgOutcome();
    }
}
