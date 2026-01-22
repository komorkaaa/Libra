package io.github.komorkaaa.meetflow.profile.service;

import io.github.komorkaaa.meetflow.profile.dto.CreateProfileRequest;
import io.github.komorkaaa.meetflow.profile.dto.ProfileResponse;
import io.github.komorkaaa.meetflow.profile.entity.Profile;
import io.github.komorkaaa.meetflow.profile.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProfileServiceTest {

  ProfileRepository repository;
  ProfileService service;

  @BeforeEach
  void setUp() {
    repository = mock(ProfileRepository.class);
    service = new ProfileService(repository);
  }

  @Test
  void createProfile_newProfile_saved() {
    UUID userId = UUID.randomUUID();

    CreateProfileRequest req = new CreateProfileRequest();
    req.setUserId(userId);
    req.setUsername("ivan");

    when(repository.existsByUserId(userId)).thenReturn(false);

    UUID result = service.createProfile(req);

    assertThat(result).isNotNull();

    ArgumentCaptor<Profile> captor = ArgumentCaptor.forClass(Profile.class);
    verify(repository).save(captor.capture());

    Profile saved = captor.getValue();
    assertThat(saved.getUserId()).isEqualTo(userId);
    assertThat(saved.getUsername()).isEqualTo("ivan");
  }

  @Test
  void createProfile_existingUser_returnsExistingId() {
    UUID userId = UUID.randomUUID();
    UUID profileId = UUID.randomUUID();

    Profile existing = new Profile();
    existing.setId(profileId);
    existing.setUserId(userId);

    when(repository.existsByUserId(userId)).thenReturn(true);
    when(repository.findByUserId(userId)).thenReturn(Optional.of(existing));

    CreateProfileRequest req = new CreateProfileRequest();
    req.setUserId(userId);
    req.setUsername("ignored");

    UUID result = service.createProfile(req);

    assertThat(result).isEqualTo(profileId);
    verify(repository, never()).save(any());
  }

  @Test
  void getProfile_found() {
    UUID profileId = UUID.randomUUID();

    Profile profile = new Profile();
    profile.setId(profileId);
    profile.setUsername("ivan");
    profile.setCreatedAt(OffsetDateTime.now());
    profile.setUpdatedAt(OffsetDateTime.now());

    when(repository.findById(profileId)).thenReturn(Optional.of(profile));

    Optional<ProfileResponse> result = service.getProfile(profileId);

    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(profileId);
    assertThat(result.get().getUsername()).isEqualTo("ivan");
  }

  @Test
  void getProfile_notFound() {
    UUID profileId = UUID.randomUUID();

    when(repository.findById(profileId)).thenReturn(Optional.empty());

    Optional<ProfileResponse> result = service.getProfile(profileId);

    assertThat(result).isEmpty();
  }

  @Test
  void updateProfile_found_updatesFields() {
    UUID profileId = UUID.randomUUID();

    Profile profile = new Profile();
    profile.setId(profileId);
    profile.setUsername("old");

    when(repository.findById(profileId)).thenReturn(Optional.of(profile));

    var req = new io.github.komorkaaa.meetflow.profile.dto.UpdateProfileRequest();
    req.setUsername("new");

    boolean updated = service.updateProfile(profileId, req);

    assertThat(updated).isTrue();
    assertThat(profile.getUsername()).isEqualTo("new");
    verify(repository).save(profile);
  }

  @Test
  void updateProfile_notFound_returnsFalse() {
    UUID profileId = UUID.randomUUID();

    when(repository.findById(profileId)).thenReturn(Optional.empty());

    var req = new io.github.komorkaaa.meetflow.profile.dto.UpdateProfileRequest();

    boolean updated = service.updateProfile(profileId, req);

    assertThat(updated).isFalse();
    verify(repository, never()).save(any());
  }
}

