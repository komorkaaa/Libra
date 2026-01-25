package io.github.komorkaaa.libra.auth.service;

import io.github.komorkaaa.libra.auth.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtProperties properties;
  private SecretKey signingKey;

  @PostConstruct
  void init() {
    signingKey = Keys.hmacShaKeyFor(
            properties.getSecret().getBytes(StandardCharsets.UTF_8)
    );
  }

  @PostConstruct
  public void logSecret() {
    System.out.println("AUTH JWT SECRET HASH = "
            + properties.getSecret().hashCode());
  }

  public String generateToken(UUID userId, String email) {
    Instant now = Instant.now();

    return Jwts.builder()
            .setSubject(userId.toString())
            .claim("email", email)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
            .signWith(signingKey)
            .compact();
  }

  public Claims parse(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(properties.getSecret().getBytes()))
            .build()
            .parseClaimsJws(token)
            .getBody();
  }
}
