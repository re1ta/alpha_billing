package ru.ibatov.billing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.dto.UserDto;
import ru.ibatov.billing.dto.UserPhonesDto;
import ru.ibatov.billing.entity.People.Role;
import ru.ibatov.billing.entity.People.User;
import ru.ibatov.billing.entity.Phone;
import ru.ibatov.billing.repos.People.UserRepository;
import ru.ibatov.billing.repos.PhoneRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PhoneRepository phoneRepo;

    public void createUser(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .patronymic(userDto.getPatronymic())
                .email(userDto.getEmail())
                .roles(Collections.singleton(Role.USER))
                .build();
        userRepo.save(user);
    }

    public void createAdmin(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .patronymic(userDto.getPatronymic())
                .email(userDto.getEmail())
                .roles(Collections.singleton(Role.ADMIN))
                .build();
        userRepo.save(user);
    }

    public UserPhonesDto fullInfoUser(Long id) {
        User user = userRepo.findById(id).get();
        List<Phone> phones = phoneRepo.findAllByUserId(id);
        return new UserPhonesDto(user, phones);
    }

    @Transactional
    public List<User> deleteUser(Long id) {
        userRepo.deleteById(id);
        return findAllUsers();
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }
}
