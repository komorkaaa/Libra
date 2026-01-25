package io.github.komorkaaa.libra.auth.controller;

import io.github.komorkaaa.libra.auth.dto.AuthResponse;
import io.github.komorkaaa.libra.auth.dto.LoginRequest;
import io.github.komorkaaa.libra.auth.dto.RegisterRequest;
import io.github.komorkaaa.libra.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
    UUID userId = authService.register(
            request.email(),
            request.password()
    );

    return ResponseEntity.ok(Map.of("userId", userId));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    String token = authService.login(request);
    return ResponseEntity.ok(new AuthResponse(token));
  }
}
