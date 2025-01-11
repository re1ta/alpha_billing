package ru.ibatov.billing.service.Auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.dto.JwtResponse;
import ru.ibatov.billing.dto.PhoneDto;
import ru.ibatov.billing.dto.SourceCode;
import ru.ibatov.billing.entity.People.Code;
import ru.ibatov.billing.entity.People.RefreshToken;
import ru.ibatov.billing.entity.People.User;
import ru.ibatov.billing.entity.Phone;
import ru.ibatov.billing.repos.People.CodeRepository;
import ru.ibatov.billing.repos.People.UserRepository;
import ru.ibatov.billing.repos.PhoneRepository;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final CodeRepository codeRepo;
    private final JwtService jwtService;
    private final PhoneRepository phoneRepo;
    private final RefreshTokenService refreshTokenService;

    public Boolean findEmailInDb(String email) {
        Optional<User> user = userRepo.findByEmail(email);
        return user.isPresent();
    }

    public Boolean checkPhone(PhoneDto phoneDto) {
        return phoneRepo.findByNumber(phoneDto.getPhoneNumber()).isPresent();
    }

    public JwtResponse findPhoneInDb(PhoneDto phoneDto) {
        Phone phone = phoneRepo.findByNumber(phoneDto.getPhoneNumber()).get();
        return getToken(phone.getId_user());
    }

    public JwtResponse checkCodeInDb(SourceCode sourceCode) {
        Optional<Code> existCode = codeRepo.findBySource(sourceCode.getSource());
        if (existCode.isPresent()) {
            return checkAndGetToken(existCode, sourceCode);
        } else {
            throw new RuntimeException("Нет такого кода!");
        }
    }

    @Transactional
    public JwtResponse checkAndGetToken(Optional<Code> existCode, SourceCode sourceCode) {
        if (sourceCode.getCode().equals(existCode.get().getCode())) {
            if (Instant.now().isBefore(existCode.get().getExpireTime())) {
                codeRepo.deleteById(existCode.get().getId());
                if(sourceCode.getSource().contains("@")) {
                     return getToken(userRepo.findByEmail(sourceCode.getSource()).get().getId());
                }
                else{
                    return getToken(phoneRepo.findByNumber(sourceCode.getSource()).get().getId_user());
                }
            } else {
                codeRepo.deleteById(existCode.get().getId());
                throw new RuntimeException("Ваш код истёк!");
            }
        } else {
            throw new RuntimeException("Это неверный код!");
        }
    }

    private JwtResponse getToken(Long userId) {
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);
        return JwtResponse.builder()
                .accessToken(jwtService.generateToken(userId, 900000))
                .token(refreshToken.getToken())
                .build();
    }

    public String sendCodeToTelegram(String number) {
        String code = generateCode();
        codeRepo.save(makeObjectCode(number,code));
        return code;
    }

    public String generateCode() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int CODE_LENGTH = 6;
        SecureRandom RANDOM = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }

    public Code makeObjectCode(String source, String code){
        return Code.builder()
                .source(source)
                .code(code)
                .expireTime((Instant.now().plus(Duration.ofMinutes(5))))
                .build();
    }
}
