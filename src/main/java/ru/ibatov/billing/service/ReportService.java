package ru.ibatov.billing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.dto.InOutcomePhone;
import ru.ibatov.billing.dto.NameValue;
import ru.ibatov.billing.dto.TarifWaste.AvgAllTarifWasteDto;
import ru.ibatov.billing.dto.TarifWaste.AvgTarifWasteDto;
import ru.ibatov.billing.entity.MonthNameValue;
import ru.ibatov.billing.repos.History.HistoryTariffWasteRepository;
import ru.ibatov.billing.repos.History.IncomeRepository;
import ru.ibatov.billing.repos.History.OutcomeRepository;
import ru.ibatov.billing.repos.People.UserRepository;
import ru.ibatov.billing.repos.PhoneRepository;
import ru.ibatov.billing.repos.UserPaymentRepository;
import ru.ibatov.billing.repos.UserTarifRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final HistoryTariffWasteRepository htwRepo;
    private final IncomeRepository incomeRepo;
    private final OutcomeRepository outcomeRepo;
    private final UserRepository userRepo;
    private final UserPaymentRepository userPaymentRepo;
    private final PhoneRepository phoneRepo;
    private final UserTarifRepository userTarifRepo;

    public List<AvgTarifWasteDto> getUserAvgTarifWaste(int id_phone) {
        return htwRepo.findAvgTarifWaste(id_phone);
    }

    public List<AvgAllTarifWasteDto> getAllAvgTarifWaste() {
        return htwRepo.findAllAvgTarifWaste();
    }

    public List<InOutcomePhone> getUserAvgIncome(int id_phone) {
        return incomeRepo.findAvgIncome(id_phone);
    }

    public Double getAllAvgIncome() {
        return incomeRepo.findAllAvgIncome();
    }

    public List<InOutcomePhone> getUserAvgOutcome(int id_phone) {
        return outcomeRepo.findAvgOutcome(id_phone);
    }

    public Double getAllAvgOutcome() {
        return outcomeRepo.findAllAvgOutcome();
    }

    public int getCountUser() {
        return userRepo.getCount();
    }

    public float getAvgPayment() {
        return userPaymentRepo.getAvgUserPayment();
    }

    public float getProcActive() {
        long countPhones = phoneRepo.count();
        int active = phoneRepo.countActivePhones();
        return ((float) active / countPhones) * 100;
    }

    public List<NameValue> countProcInternetUse() {
        int[] from = new int[]{0, 11, 21, 31};
        int[] to = new int[]{10, 20, 30, 1000};
        List<NameValue> nameValueList = new ArrayList<>();
        long allUsers = userTarifRepo.countInternetUsers();
        int userCount;
        for (int i = 0; i < from.length; i++) {
            userCount = userTarifRepo.countProcInternet(from[i], to[i]);
            nameValueList.add(new NameValue(from[i] + "-" + to[i], ((float) userCount / allUsers) * 100));
        }
        return nameValueList;
    }

    public List<NameValue> countPopularIncome() {
        int[] from = new int[]{100, 301, 501, 1001};
        int[] to = new int[]{300, 500, 1000, 5000};
        List<NameValue> nameValueList = new ArrayList<>();
        long allUsers = incomeRepo.count();
        int userCount;
        for (int i = 0; i < from.length; i++) {
            userCount = incomeRepo.countUserIncome((float) from[i], (float) to[i]);
            nameValueList.add(new NameValue(from[i] + "-" + to[i], ((float) userCount / allUsers) * 100));
        }
        return nameValueList;
    }

    public List<MonthNameValue> getAvgMonthResourceUser() {
        String[] months = new String[]{"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        String[] resources = new String[]{"Среднее количество интернета", "Среднее количество смс", "Среднее количество минут"};
        List<MonthNameValue> monthNameValues = new ArrayList<>();
        List<NameValue> nameValueList = new ArrayList<>();
        for (int i = 0; i < resources.length; i++) {
            for (int j = 0; j < months.length; j++) {
                nameValueList.add(new NameValue(months[j], htwRepo.avgUseResource(j+1, i+1)));
            }
            monthNameValues.add(new MonthNameValue(resources[i], nameValueList));
            nameValueList= new ArrayList<>();
        }
        return monthNameValues;
    }
}
