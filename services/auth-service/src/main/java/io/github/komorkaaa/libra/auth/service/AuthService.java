package io.github.komorkaaa.libra.auth.service;

import io.github.komorkaaa.libra.auth.client.ProfileClient;
import io.github.komorkaaa.libra.auth.dto.CreateProfileInternalRequest;
import io.github.komorkaaa.libra.auth.dto.LoginRequest;
import io.github.komorkaaa.libra.auth.entity.User;
import io.github.komorkaaa.libra.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final ProfileClient profileClient;


  public UUID register(String email, String rawPassword) {
    if(userRepository.existsByEmail(email)){
      throw new IllegalArgumentException("Email already exists");
    }

    User user = new User();
    user.setId(UUID.randomUUID());
    user.setEmail(email);
    user.setPasswordHash(passwordEncoder.encode(rawPassword));

    CreateProfileInternalRequest req = new CreateProfileInternalRequest(user.getId(), email);
    profileClient.createProfile(req);

    userRepository.save(user);
    return user.getId();
  }

  public String login(LoginRequest request) {
    User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

    if (!user.isEnabled()) {
      throw new RuntimeException("User disabled");
    }

    if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
      throw new RuntimeException("Invalid password");
    }

    return jwtService.generateToken(user.getId(), user.getEmail());
  }

}
