package ru.ibatov.billing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.entity.History.Income;
import ru.ibatov.billing.entity.Phone;
import ru.ibatov.billing.repos.History.IncomeRepository;
import ru.ibatov.billing.repos.PhoneRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepo;
    private final PhoneRepository phoneRepo;

    public List<Income> getIncomes() {
        return incomeRepo.findAll();
    }

    public List<Income> getUserIncomes(int id_phone){
        return incomeRepo.findIncomesByIdPhone(id_phone);
    }

    @Transactional
    public Income saveDeposit(Income income) {
        int id_phone = income.getId_phone();
        Phone phone = phoneRepo.findById(id_phone).get();
        float newBalance = phone.getBalance() + income.getMoney();
        phoneRepo.changeBalance(newBalance, id_phone);
        if (!phone.getActive() && newBalance >= 0.0) {
            phoneRepo.setActive(id_phone, true);
        }
        income.setDate(new Date());
        return incomeRepo.save(income);
    }
}
