package ru.ibatov.billing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.entity.RemainTarif;
import ru.ibatov.billing.repos.RemainTarifRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemainTarifService {

    private final RemainTarifRepository remainTarifRepo;

    public List<RemainTarif> getAllRemainTarifs() {
        return remainTarifRepo.findAll();
    }

    public RemainTarif save(RemainTarif remainTarif) {
        return remainTarifRepo.save(remainTarif);
    }

    @Transactional
    public void update(RemainTarif remainTarif) {
        RemainTarif changedStatus = subtractRemainTarif(remainTarif);
        remainTarifRepo.updateByIdPhone(changedStatus.getId_phone(), changedStatus.getInternet(),
                changedStatus.getSms(), changedStatus.getMinutes());
    }

    private RemainTarif subtractRemainTarif(RemainTarif remainTarif) {
        System.out.println(remainTarif);
        RemainTarif currentStatus = remainTarifRepo.getCurrentStatus(remainTarif.getId_phone());
        System.out.println(currentStatus.getInternet() - remainTarif.getInternet());
        System.out.println(currentStatus.getMinutes() - remainTarif.getMinutes());
        System.out.println(currentStatus.getSms() - remainTarif.getSms());
        return RemainTarif.builder()
                .id_phone(remainTarif.getId_phone())
                .internet(currentStatus.getInternet() - remainTarif.getInternet())
                .minutes(currentStatus.getMinutes() - remainTarif.getMinutes())
                .sms(currentStatus.getSms() - remainTarif.getSms())
                .build();
    }
}
