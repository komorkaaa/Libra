package io.github.komorkaaa.libra.gateway.filter;

import io.github.komorkaaa.libra.gateway.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthGatewayFilterFactory
        extends AbstractGatewayFilterFactory<JwtAuthGatewayFilterFactory.Config> {

  private final JwtProperties jwtProperties;

  public JwtAuthGatewayFilterFactory(JwtProperties jwtProperties) {
    super(Config.class);
    this.jwtProperties = jwtProperties;
  }

  @PostConstruct
  public void logSecret() {
    System.out.println("GATEWAY JWT SECRET HASH = "
            + jwtProperties.getSecret().hashCode());
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {

      System.out.println(">>> JWT FILTER CALLED");

      String authHeader = exchange.getRequest()
              .getHeaders()
              .getFirst("Authorization");

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      }

      String token = authHeader.substring(7);

      try {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes())
                )
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userId = claims.getSubject();

        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header("X-User-Id", userId)
                .build();

        return chain.filter(
                exchange.mutate().request(mutatedRequest).build()
        );

      } catch (Exception e) {
        System.out.println(">>> JWT ERROR: " + e.getClass().getName());
        e.printStackTrace();
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      }
    };
  }

  public static class Config {}
}
