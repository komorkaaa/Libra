package io.github.komorkaaa.meetflow.profile.service;

import io.github.komorkaaa.meetflow.profile.dto.CreateProfileRequest;
import io.github.komorkaaa.meetflow.profile.dto.ProfileResponse;
import io.github.komorkaaa.meetflow.profile.dto.UpdateProfileRequest;
import io.github.komorkaaa.meetflow.profile.entity.Profile;
import io.github.komorkaaa.meetflow.profile.repository.ProfileRepository;

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
    if (repo.existsByUserId(req.getUserId())) {
      return repo.findByUserId(req.getUserId()).get().getId();
    }

    Profile p = new Profile();
    p.setId(UUID.randomUUID());
    p.setUserId(req.getUserId());
    p.setUsername(req.getUsername());
    p.setAvatarUrl(req.getAvatarUrl());
    p.setPhone(req.getPhone());
    p.setPreferences(req.getPreferencesJson());
    repo.save(p);
    return p.getId();
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
    Optional<Profile> byId = repo.findById(profileId);
    if (byId.isEmpty()) return false;
    Profile p = byId.get();
    if (req.getUsername() != null) p.setUsername(req.getUsername());
    if (req.getAvatarUrl() != null) p.setAvatarUrl(req.getAvatarUrl());
    if (req.getPhone() != null) p.setPhone(req.getPhone());
    if (req.getPreferencesJson() != null) p.setPreferences(req.getPreferencesJson());
    repo.save(p);
    return true;
  }

  private ProfileResponse toResponse(Profile p) {
    ProfileResponse r = new ProfileResponse();
    r.setId(p.getId());
    r.setUserId(p.getUserId());
    r.setUsername(p.getUsername());
    r.setAvatarUrl(p.getAvatarUrl());
    r.setPhone(p.getPhone());
    r.setPreferencesJson(p.getPreferences());
    r.setCreatedAt(p.getCreatedAt());
    r.setUpdatedAt(p.getUpdatedAt());
    return r;
  }

}
