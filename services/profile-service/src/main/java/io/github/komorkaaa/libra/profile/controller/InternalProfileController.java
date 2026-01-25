package io.github.komorkaaa.libra.profile.controller;

import io.github.komorkaaa.libra.profile.dto.CreateProfileInternalRequest;
import io.github.komorkaaa.libra.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/profiles")
@RequiredArgsConstructor
public class InternalProfileController {

  private final ProfileService profileService;

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody CreateProfileInternalRequest req) {
    profileService.createProfile(req.userId(), req.email());
    return ResponseEntity.ok().build();
  }
}

