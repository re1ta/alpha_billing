package ru.ibatov.billing.service.Auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.entity.People.RefreshToken;
import ru.ibatov.billing.entity.People.User;
import ru.ibatov.billing.repos.People.RefreshTokenRepository;
import ru.ibatov.billing.repos.People.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepo;
    private final UserRepository userRepo;

    @Transactional
    public RefreshToken createRefreshToken(Long user_id) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user_id(user_id)
                .token(UUID.randomUUID().toString())
                .expireDate(Instant.now().plusMillis(600000))//10
                .build();
        if(refreshTokenRepo.findByUserId(user_id).isEmpty()) {
            return refreshTokenRepo.save(refreshToken);
        }
        else {
            refreshTokenRepo.updateToken(refreshToken.getToken(), refreshToken.getExpireDate(), user_id);
            return refreshToken;
        }
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpireDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
