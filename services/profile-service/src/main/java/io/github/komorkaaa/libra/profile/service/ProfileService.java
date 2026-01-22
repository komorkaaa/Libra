package io.github.komorkaaa.libra.profile.service;

import io.github.komorkaaa.libra.profile.dto.CreateProfileRequest;
import io.github.komorkaaa.libra.profile.dto.ProfileResponse;
import io.github.komorkaaa.libra.profile.dto.UpdateProfileRequest;
import io.github.komorkaaa.libra.profile.entity.Profile;
import io.github.komorkaaa.libra.profile.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

  private final ProfileRepository repo;

  public ProfileService(ProfileRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public UUID createProfile(CreateProfileRequest req) {
    Optional<Profile> existing = repo.findByUserId(req.getUserId());
    if (existing.isPresent()) {
      return existing.get().getId();
    }

    Profile profile = Profile.builder()
            .userId(req.getUserId())
            .username(req.getUsername())
            .email(req.getEmail())
            .phone(req.getPhone())
            .preferences(req.getPreferencesJson())
            .build();

    repo.save(profile);
    return profile.getId();
  }

  @Transactional(readOnly = true)
  public Optional<ProfileResponse> getProfile(UUID profileId) {
    return repo.findById(profileId).map(this::toResponse);
  }

  @Transactional(readOnly = true)
  public Optional<ProfileResponse> getByUserId(UUID userId) {
    return repo.findByUserId(userId).map(this::toResponse);
  }

  @Transactional
  public boolean updateProfile(UUID profileId, UpdateProfileRequest req) {
    Profile profile = repo.findById(profileId).orElse(null);
    if (profile == null) {
      return false;
    }

    if (req.getUsername() != null) {
      profile.setUsername(req.getUsername());
    }
    if (req.getEmail() != null) {
      profile.setEmail(req.getEmail());
    }
    if (req.getPhone() != null) {
      profile.setPhone(req.getPhone());
    }
    if (req.getPreferencesJson() != null) {
      profile.setPreferences(req.getPreferencesJson());
    }

    return true;
  }

  private ProfileResponse toResponse(Profile profile) {
    ProfileResponse r = new ProfileResponse();
    r.setId(profile.getId());
    r.setUserId(profile.getUserId());
    r.setUsername(profile.getUsername());
    r.setEmail(profile.getEmail());
    r.setPhone(profile.getPhone());
    r.setPreferencesJson(profile.getPreferences());
    r.setCreatedAt(profile.getCreatedAt());
    r.setUpdatedAt(profile.getUpdatedAt());
    return r;
  }

  @Transactional
  public boolean deleteProfile(UUID profileId) {
    if (!repo.existsById(profileId)) {
      return false;
    }
    repo.deleteById(profileId);
    return true;
  }
}