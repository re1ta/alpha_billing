package ru.ibatov.billing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ibatov.billing.dto.*;
import ru.ibatov.billing.entity.People.RefreshToken;
import ru.ibatov.billing.service.Auth.AuthService;
import ru.ibatov.billing.service.Auth.EmailService;
import ru.ibatov.billing.service.Auth.JwtService;
import ru.ibatov.billing.service.Auth.RefreshTokenService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/checkEmail")
    public Boolean checkEmail(@RequestBody Email email) {
        return authService.findEmailInDb(email.getEmail());
    }

    @PostMapping("/phone")
    public JwtResponse phoneAuth(@RequestBody PhoneDto phoneDto) {
        return authService.findPhoneInDb(phoneDto);
    }

    @PostMapping("/checkPhone")
    public Boolean checkPhone(@RequestBody PhoneDto phoneDto) {
        return authService.checkPhone(phoneDto);
    }

    @PostMapping("/sendCodeEmail")
    public void sendCode(@RequestBody Email email) {
        emailService.sendCodeToEmail(email.getEmail());
    }

    @PostMapping("/sendCodeTelegram")
    public String sendCodeTelegram(@RequestBody String number){
        return authService.sendCodeToTelegram(number);
    }

    @PostMapping("/checkCode")
    public JwtResponse checkCode(@RequestBody SourceCode sourceCode) {
        return authService.checkCodeInDb(sourceCode);
    }

    @PostMapping("/logout")
    public void logout() {
    }

    @PostMapping("/refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser_id)
                .map(user_id -> {
                    String accessToken = jwtService.generateToken(user_id, 60000);
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }
}
