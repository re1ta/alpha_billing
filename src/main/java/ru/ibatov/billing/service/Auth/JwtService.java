package ru.ibatov.billing.service.Auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import ru.ibatov.billing.repos.People.UserRepository;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private final UserRepository userRepo;

    public JwtService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    public String extractUserId(String token) {
        return extractClaim(token, claims -> String.valueOf(claims.getId()));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, String user_id) {
        final String id = extractUserId(token);
        return (id.equals(user_id) && !isTokenExpired(token));
    }


    public String generateToken(Long user_id,int time){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,user_id,time);
    }

    private String createToken(Map<String, Object> claims, Long user_id, int time) {
        var user = userRepo.findById(user_id).get();
        return Jwts.builder()
                .setClaims(claims)
                .claim("id",user.getId())
                .claim("role", user.getRoles().iterator().next().getRoleName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
