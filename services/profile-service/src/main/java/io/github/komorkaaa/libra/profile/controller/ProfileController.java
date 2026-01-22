package io.github.komorkaaa.libra.profile.controller;

import io.github.komorkaaa.libra.profile.dto.CreateProfileRequest;
import io.github.komorkaaa.libra.profile.dto.ProfileResponse;
import io.github.komorkaaa.libra.profile.dto.UpdateProfileRequest;
import io.github.komorkaaa.libra.profile.service.ProfileService;

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
  public ResponseEntity<Map<String, UUID>> create(@Valid @RequestBody CreateProfileRequest req) {
    UUID profileId = service.createProfile(req);
    return ResponseEntity.ok(Map.of("profileId", profileId));
  }

  @GetMapping("/{profileId}")
  public ResponseEntity<ProfileResponse> getById(@PathVariable UUID profileId) {
    return service.getProfile(profileId)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/by-user/{userId}")
  public ResponseEntity<ProfileResponse> getByUserId(@PathVariable UUID userId) {
    return service.getByUserId(userId)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PatchMapping("/{profileId}")
  public ResponseEntity<Map<String, Boolean>> update(
          @PathVariable UUID profileId,
          @RequestBody UpdateProfileRequest req
  ) {
    boolean updated = service.updateProfile(profileId, req);
    if (!updated) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(Map.of("success", true));
  }

  @DeleteMapping("/{profileId}")
  public ResponseEntity<Void> delete(@PathVariable UUID profileId) {
    boolean deleted = service.deleteProfile(profileId);
    if (!deleted) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
  }
}
