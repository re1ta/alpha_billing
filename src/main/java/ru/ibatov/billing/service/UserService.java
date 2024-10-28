package ru.ibatov.billing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.dto.UserDto;
import ru.ibatov.billing.entity.People.User;
import ru.ibatov.billing.repos.People.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserDto userDto){
        User user = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .patronymic(userDto.getPatronymic())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public List<User> deleteUser(Long id){
        userRepository.deleteById(id);
        return findAllUsers();
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
}
