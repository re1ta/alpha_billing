package ru.ibatov.billing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.entity.History.HistoryTariffWaste;
import ru.ibatov.billing.entity.Phone;
import ru.ibatov.billing.entity.RemainTarif;
import ru.ibatov.billing.repos.History.HistoryTariffWasteRepository;
import ru.ibatov.billing.repos.PhoneRepository;
import ru.ibatov.billing.repos.RemainTarifRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RemainTarifService {

    private final RemainTarifRepository remainTarifRepo;
    private final HistoryTariffWasteRepository historyTariffWasteRepo;
    private final PhoneRepository phoneRepo;

    public List<RemainTarif> getAllRemainTarifs() {
        return remainTarifRepo.findAll();
    }

    public RemainTarif save(RemainTarif remainTarif) {
        return remainTarifRepo.save(remainTarif);
    }

    @Transactional
    public void update(RemainTarif remainTarif) {
        Phone phone = phoneRepo.findById(remainTarif.getId_phone()).get();
        if(phone.getActive()) {
            RemainTarif changedStatus = subtractRemainTarif(remainTarif);
            toHistoryWaste(remainTarif);
            remainTarifRepo.updateByIdPhone(changedStatus.getId_phone(), changedStatus.getInternet(),
                    changedStatus.getSms(), changedStatus.getMinutes());
        }
    }

    private RemainTarif subtractRemainTarif(RemainTarif remainTarif) {
        RemainTarif currentStatus = remainTarifRepo.getCurrentStatus(remainTarif.getId_phone());
        return RemainTarif.builder()
                .id_phone(remainTarif.getId_phone())
                .internet(currentStatus.getInternet() - remainTarif.getInternet())
                .minutes(currentStatus.getMinutes() - remainTarif.getMinutes())
                .sms(currentStatus.getSms() - remainTarif.getSms())
                .build();
    }

    private void toHistoryWaste(RemainTarif remainTarif){
        if(remainTarif.getInternet() != 0){
            historyTariffWasteRepo.save(new HistoryTariffWaste(remainTarif.getId_phone(), remainTarif.getInternet(), 1,new Date()));
        }
        if(remainTarif.getMinutes() != 0.0){
            historyTariffWasteRepo.save(new HistoryTariffWaste(remainTarif.getId_phone(), remainTarif.getMinutes(), 3,new Date()));
        }
        if(remainTarif.getSms() != 0){
            historyTariffWasteRepo.save(new HistoryTariffWaste(remainTarif.getId_phone(), remainTarif.getSms(), 2,new Date()));
        }
    }
}
