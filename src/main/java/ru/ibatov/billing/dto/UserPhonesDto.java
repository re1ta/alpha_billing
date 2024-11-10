package ru.ibatov.billing.dto;

import lombok.Data;
import ru.ibatov.billing.entity.People.User;
import ru.ibatov.billing.entity.Phone;

import java.util.List;

@Data
public class UserPhonesDto {

    private User user;
    private List<Phone> phones;

    public UserPhonesDto(User user, List<Phone> phones) {
        this.user = user;
        this.phones = phones;
    }
}
