package ru.ibatov.billing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ibatov.billing.dto.UserPhonesDto;
import ru.ibatov.billing.service.UserService;
import ru.ibatov.billing.dto.UserDto;
import ru.ibatov.billing.entity.People.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserPhonesDto getUser(@PathVariable Long id){
        return userService.fullInfoUser(id);
    }

    @PostMapping("/")
    public void saveUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
    }

    @DeleteMapping("/{id}")
    public List<User> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @PutMapping("/")
    public void updateUser(){

    }
}
