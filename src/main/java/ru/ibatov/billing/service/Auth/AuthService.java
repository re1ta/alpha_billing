package ru.ibatov.billing.service.Auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.dto.EmailCode;
import ru.ibatov.billing.dto.JwtResponse;
import ru.ibatov.billing.dto.PhoneDto;
import ru.ibatov.billing.entity.People.Code;
import ru.ibatov.billing.entity.People.RefreshToken;
import ru.ibatov.billing.entity.People.User;
import ru.ibatov.billing.entity.Phone;
import ru.ibatov.billing.repos.People.CodeRepository;
import ru.ibatov.billing.repos.People.UserRepository;
import ru.ibatov.billing.repos.PhoneRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
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

    public Boolean checkPhone(PhoneDto phoneDto){
        return phoneRepo.findByNumber(phoneDto.getPhoneNumber()).isPresent();
    }

    public JwtResponse findPhoneInDb(PhoneDto phoneDto){
        Phone phone = phoneRepo.findByNumber(phoneDto.getPhoneNumber()).get();
        return getToken(phone.getId_user());
    }

    public JwtResponse checkCodeInDb(EmailCode emailCode){
        Optional<Code> existCode = codeRepo.findByEmail(emailCode.getEmail());
        if(existCode.isPresent()){
            return checkAndGetToken(existCode,emailCode);
        }
        else{
            throw new RuntimeException("У этой почты нет кода!");
        }
    }

    @Transactional
    public JwtResponse checkAndGetToken(Optional<Code> existCode, EmailCode emailCode){
        if(emailCode.getCode().equals(existCode.get().getCode())){
            if(Instant.now().isBefore(existCode.get().getExpireTime())){
                codeRepo.deleteById(existCode.get().getId());
                Optional<User> user = userRepo.findByEmail(emailCode.getEmail());
                Long userId = user.get().getId();
                return getToken(userId);
            }
            else{
                codeRepo.deleteById(existCode.get().getId());
                throw new RuntimeException("Ваш код истёк!");
            }
        }
        else{
            throw new RuntimeException("Это неверный код!");
        }
    }

    private JwtResponse getToken(Long userId){
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);
        return JwtResponse.builder()
                .accessToken(jwtService.generateToken(userId, 900000))
                .token(refreshToken.getToken())
                .build();
    }
}
