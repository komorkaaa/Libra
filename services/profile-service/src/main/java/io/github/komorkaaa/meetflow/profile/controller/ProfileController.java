package io.github.komorkaaa.meetflow.profile.controller;

import io.github.komorkaaa.meetflow.profile.dto.CreateProfileRequest;
import io.github.komorkaaa.meetflow.profile.dto.ProfileResponse;
import io.github.komorkaaa.meetflow.profile.dto.UpdateProfileRequest;
import io.github.komorkaaa.meetflow.profile.service.ProfileService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping
public class ProfileController {

  private final ProfileService service;

  public ProfileController(ProfileService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody CreateProfileRequest req) {
    UUID id = service.createProfile(req);
    return ResponseEntity.ok(Map.of("profileId", id));
  }

  @GetMapping("/{profileId}")
  public ResponseEntity<ProfileResponse> getById(@PathVariable UUID profileId) {
    Optional<ProfileResponse> p = service.getProfile(profileId);
    return p.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{profileId}")
  public ResponseEntity<Map<String, Object>> update(@PathVariable UUID profileId,
                                                    @RequestBody UpdateProfileRequest req) {
    boolean ok = service.updateProfile(profileId, req);
    if (!ok) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(Map.of("success", true));
  }

  @GetMapping("/me")
  public ResponseEntity<ProfileResponse> me(
          @RequestHeader(value = "X-User-Id", required = false) UUID userId,
          @RequestHeader(value = "X-Profile-Id", required = false) UUID profileId
  ) {
    if (profileId != null) {
      return getById(profileId);
    }
    if (userId != null) {
      Optional<ProfileResponse> p = service.getByUserId(userId);
      return p.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    return ResponseEntity.badRequest().build();
  }
}
