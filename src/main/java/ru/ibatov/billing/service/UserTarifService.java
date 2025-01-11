package ru.ibatov.billing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.dto.NameValue;
import ru.ibatov.billing.dto.NewTarif;
import ru.ibatov.billing.dto.SortedTarifs;
import ru.ibatov.billing.dto.UserTarifWithResource;
import ru.ibatov.billing.entity.RemainTarif;
import ru.ibatov.billing.entity.Tarif;
import ru.ibatov.billing.entity.UserPayment;
import ru.ibatov.billing.entity.UserTarif;
import ru.ibatov.billing.repos.*;
import ru.ibatov.billing.repos.Names.TypeResourceRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserTarifService {

    private final UserTarifRepository userTarifRepo;
    private final TarifRepository tarifRepo;
    private final UserPaymentRepository userPaymentRepo;
    private final TypeResourceRepository typeResourceRepo;
    private final RemainTarifRepository remainTariffRepo;
    private final OutcomeService outcomeService;
    private final RemainTarifService remainTarifService;

    public List<UserTarif> getAll() {
        return userTarifRepo.findAll();
    }

    @Transactional
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

    @Transactional
    public ArrayList<UserTarif> save(NewTarif newTarif) {
        SortedTarifs sortedTarifs = getAllTarifs();
        ArrayList<UserTarif> userTarifList = new ArrayList<>();
        int id_phone = newTarif.getId_phone();
        userTarifList.add(findTarif(sortedTarifs.getInternetList(), newTarif.getInternet(), id_phone));
        userTarifList.add(findTarif(sortedTarifs.getMinuteList(), newTarif.getMinutes(), id_phone));
        userTarifList.add(smsTarif(sortedTarifs.getSmsList(), newTarif.getSms(), id_phone));

        userPaymentRepo.save(buildUserPayment(newTarif));
        remainTariffRepo.save(buildRemainTarif(newTarif));
        outcomeService.payForTarif(newTarif.getId_phone());

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

    @Transactional
    public UserTarifWithResource update(NewTarif newTarif) {
        deleteOldUserTarif(newTarif);
        save(newTarif);
        return getUserTarifs(newTarif.getId_phone());
    }

    @Transactional
    private void deleteOldUserTarif(NewTarif newTarif){
        remainTariffRepo.deleteByIdPhone(newTarif.getId_phone());
        userPaymentRepo.deleteByIdPhone(newTarif.getId_phone());
        userTarifRepo.deleteByIdPhone(newTarif.getId_phone());
    }

    @Transactional
    public void wasteTarifs() {
        Random r = new Random();
        List<RemainTarif> remainTarifs = remainTariffRepo.findAll();
        for(RemainTarif remainTarif : remainTarifs){
            RemainTarif newRemain = new RemainTarif(remainTarif.getId_phone(),
                    r.nextFloat(remainTarif.getInternet()),
                    r.nextFloat(remainTarif.getMinutes()),
                    r.nextInt(remainTarif.getSms()));
            remainTarifService.update(newRemain);
        }
        System.out.println(remainTariffRepo.findAll());
    }
}