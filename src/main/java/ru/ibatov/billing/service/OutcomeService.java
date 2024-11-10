package ru.ibatov.billing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.entity.History.Income;
import ru.ibatov.billing.entity.History.Outcome;
import ru.ibatov.billing.entity.Phone;
import ru.ibatov.billing.entity.UserPayment;
import ru.ibatov.billing.repos.History.OutcomeRepository;
import ru.ibatov.billing.repos.PhoneRepository;
import ru.ibatov.billing.repos.UserPaymentRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutcomeService {

    private final OutcomeRepository outcomeRepo;
    private final UserPaymentRepository userPaymentRepo;
    private final PhoneRepository phoneRepo;

    public List<Outcome> getOutcomes() {
        return outcomeRepo.findAll();
    }

    public List<Outcome> getUserOutcomes(int id_phone){
        return outcomeRepo.findOutcomesByIdPhone(id_phone);
    }

    @Transactional
    public Outcome payForTarif(int id_phone){
        float tarifPrice = userPaymentRepo.getMoneyTarif(id_phone);
        Phone phone = phoneRepo.findById(id_phone).get();
        float newBalance = phone.getBalance() - tarifPrice;
        phoneRepo.changeBalance(newBalance, id_phone);
        if(phone.getActive() && newBalance < 0){
            phoneRepo.setActive(id_phone,false);
        }
        return outcomeRepo.save(new Outcome(id_phone, tarifPrice, "Оплата тарифа", new Date()));
    }
}
