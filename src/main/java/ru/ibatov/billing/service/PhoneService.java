package ru.ibatov.billing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.dto.UserPhoneDto;
import ru.ibatov.billing.entity.Phone;
import ru.ibatov.billing.repos.PhoneRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public Phone createPhone(UserPhoneDto userPhoneDto){
        Phone phone = Phone.builder()
                .number(userPhoneDto.getPhoneNumber())
                .id_user(userPhoneDto.getUserId())
                .balance(0)
                .active(true)
                .build();
        return phoneRepository.save(phone);
    }

    public List<Phone> findAllNumbers(){
        return phoneRepository.findAll();
    }

    public Boolean checkNumber(){
        return true;
    }
}
