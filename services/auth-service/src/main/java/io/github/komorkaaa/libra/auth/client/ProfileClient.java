package io.github.komorkaaa.libra.auth.client;

import io.github.komorkaaa.libra.auth.dto.CreateProfileInternalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ProfileClient {

  private final WebClient profileWebClient;

  public void createProfile(CreateProfileInternalRequest req) {
    profileWebClient.post()
            .uri("/internal/profiles")
            .bodyValue(req)
            .retrieve()
            .toBodilessEntity()
            .block();
  }
}
