package ru.ibatov.billing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.dto.NameValue;
import ru.ibatov.billing.dto.NewTarif;
import ru.ibatov.billing.dto.SortedTarifs;
import ru.ibatov.billing.dto.UserTarifWithResource;
import ru.ibatov.billing.entity.Names.TypeResource;
import ru.ibatov.billing.entity.RemainTarif;
import ru.ibatov.billing.entity.Tarif;
import ru.ibatov.billing.entity.UserPayment;
import ru.ibatov.billing.entity.UserTarif;
import ru.ibatov.billing.repos.Names.TypeResourceRepository;
import ru.ibatov.billing.repos.RemainTarifRepository;
import ru.ibatov.billing.repos.TarifRepository;
import ru.ibatov.billing.repos.UserPaymentRepository;
import ru.ibatov.billing.repos.UserTarifRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTarifService {

    private final UserTarifRepository userTarifRepo;
    private final TarifRepository tarifRepo;
    private final UserPaymentRepository userPaymentRepo;
    private final TypeResourceRepository typeResourceRepo;
    private final RemainTarifRepository remainTariffRepo;

    public List<UserTarif> getAll() {
        return userTarifRepo.findAll();
    }

    public UserTarifWithResource getUserTarifs(int id_phone) {
        List<UserTarif> userTarifList = userTarifRepo.findByPhoneId(id_phone);
        RemainTarif currentUserTarif = remainTariffRepo.getCurrentStatus(id_phone);
        ArrayList<NameValue> changedUserTarifsName = new ArrayList<>();
        for (int i = 0; i < userTarifList.toArray().length; i++) {
            changedUserTarifsName.add(changeIdToName(userTarifList.get(i)));
        }
        return UserTarifWithResource.builder()
                .tarifValues(changedUserTarifsName)
                .CurrentTarifValue(addNameResources(currentUserTarif))
                .money(userPaymentRepo.getMoneyTarif(id_phone))
                .build();
    }

    private ArrayList<NameValue> addNameResources(RemainTarif currentUserTarif){
        ArrayList<NameValue> nameValues = new ArrayList<>();
        nameValues.add(new NameValue("Интернет", currentUserTarif.getInternet()));
        nameValues.add(new NameValue("Смс", currentUserTarif.getSms()));
        nameValues.add(new NameValue("Минуты", currentUserTarif.getMinutes()));
        return nameValues;
    }

    private NameValue changeIdToName(UserTarif userTarif) {
        Tarif tarif = tarifRepo.getTarif(userTarif.getId_service());
        String name = typeResourceRepo.findById(tarif.getId_typeResource()).get().getName();
        return NameValue.builder()
                .name(name)
                .value(userTarif.getValue())
                .build();
    }

    public ArrayList<UserTarif> save(NewTarif newTarif) {
        SortedTarifs sortedTarifs = getAllTarifs();
        ArrayList<UserTarif> userTarifList = new ArrayList<>();
        int id_phone = newTarif.getId_phone();
        userTarifList.add(findTarif(sortedTarifs.getInternetList(), newTarif.getInternet(), id_phone));
        userTarifList.add(findTarif(sortedTarifs.getMinuteList(), newTarif.getMinutes(), id_phone));
        userTarifList.add(smsTarif(sortedTarifs.getSmsList(), newTarif.getSms(), id_phone));

        userPaymentRepo.save(buildUserPayment(newTarif));
        remainTariffRepo.save(buildRemainTarif(newTarif));

        return userTarifList;
    }

    public SortedTarifs getAllTarifs() {
        return SortedTarifs.builder()
                .internetList(tarifRepo.getInternetTarif())
                .minuteList(tarifRepo.getMinuteTarif())
                .smsList(tarifRepo.getSmsTarif())
                .build();
    }

    private UserTarif findTarif(List<Tarif> tarifList, int value, int id_phone) {
        Tarif tarif;
        for (int i = 0; i < tarifList.toArray().length; i++) {
            tarif = tarifList.get(i);
            if (tarif.getMin() <= value && tarif.getMax() >= value) {
                UserTarif userTarif = UserTarif.builder()
                        .id_service(tarif.getId())
                        .id_phone(id_phone)
                        .value(value)
                        .build();
                return userTarifRepo.save(userTarif);
            }
        }
        throw new RuntimeException("гг вп не работает");
    }

    private UserTarif smsTarif(Tarif tarif, int value, int id_phone) {
        UserTarif userTarif = UserTarif.builder()
                .id_service(tarif.getId())
                .id_phone(id_phone)
                .value(value)
                .build();
        return userTarifRepo.save(userTarif);
    }

    private UserPayment buildUserPayment(NewTarif newTarif) {
        return UserPayment.builder()
                .id_phone(newTarif.getId_phone())
                .money(newTarif.getMoney())
                .dayPayment(getNextMonth())
                .build();
    }

    private Date getNextMonth(){
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    private RemainTarif buildRemainTarif(NewTarif newTarif) {
        return RemainTarif.builder()
                .id_phone(newTarif.getId_phone())
                .sms(newTarif.getSms())
                .minutes(newTarif.getMinutes())
                .internet(newTarif.getInternet())
                .build();
    }
}