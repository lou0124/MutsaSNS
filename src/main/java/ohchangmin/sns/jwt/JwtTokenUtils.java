package ohchangmin.sns.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenUtils {

    private final Key signingKey;
    private final JwtParser jwtParser;

    public JwtTokenUtils(@Value("${jwt.secret}") String jwtSecret) {
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(this.signingKey)
                .build();
    }

    public String getSubject(String subject) {
        try {
            return jwtParser
                    .parseClaimsJws(subject)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            log.warn("jwt 파싱에 실패했습니다.", e);
            throw e;
        }
    }

    public String generateToken(String subject) {
        return Jwts.builder()
                .setClaims(createClaims(subject))
                .signWith(signingKey)
                .compact();
    }

    private Claims createClaims(String subject) {
        return Jwts.claims()
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(3600)));
    }
}
