package io.github.komorkaaa.meetflow.profile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.komorkaaa.meetflow.profile.dto.CreateProfileRequest;
import io.github.komorkaaa.meetflow.profile.dto.ProfileResponse;
import io.github.komorkaaa.meetflow.profile.dto.UpdateProfileRequest;
import io.github.komorkaaa.meetflow.profile.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc(addFilters = false) // 🔥 убираем security
class ProfileControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  ProfileService profileService;

  @Test
  void createProfile_returnsProfileId() throws Exception {
    UUID profileId = UUID.randomUUID();

    when(profileService.createProfile(org.mockito.ArgumentMatchers.any()))
            .thenReturn(profileId);

    CreateProfileRequest req = new CreateProfileRequest();
    req.setUserId(UUID.randomUUID());
    req.setUsername("ivan");

    mockMvc.perform(
                    post("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.profileId").value(profileId.toString()));
  }

  @Test
  void getProfileById_found() throws Exception {
    UUID profileId = UUID.randomUUID();

    ProfileResponse resp = new ProfileResponse();
    resp.setId(profileId);
    resp.setUserId(UUID.randomUUID());
    resp.setUsername("ivan");

    when(profileService.getProfile(profileId))
            .thenReturn(Optional.of(resp));

    mockMvc.perform(get("/" + profileId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(profileId.toString()))
            .andExpect(jsonPath("$.username").value("ivan"));
  }

  @Test
  void getProfileById_notFound() throws Exception {
    UUID profileId = UUID.randomUUID();

    when(profileService.getProfile(profileId))
            .thenReturn(Optional.empty());

    mockMvc.perform(get("/" + profileId))
            .andExpect(status().isNotFound());
  }

  @Test
  void updateProfile_ok() throws Exception {
    UUID profileId = UUID.randomUUID();

    when(profileService.updateProfile(
            org.mockito.ArgumentMatchers.eq(profileId),
            org.mockito.ArgumentMatchers.any()
    )).thenReturn(true);

    UpdateProfileRequest req = new UpdateProfileRequest();
    req.setUsername("newname");

    mockMvc.perform(
                    put("/" + profileId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(req))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
  }

  @Test
  void me_byUserId() throws Exception {
    UUID userId = UUID.randomUUID();

    ProfileResponse resp = new ProfileResponse();
    resp.setId(UUID.randomUUID());
    resp.setUserId(userId);
    resp.setUsername("ivan");

    when(profileService.getByUserId(userId))
            .thenReturn(Optional.of(resp));

    mockMvc.perform(
                    get("/me")
                            .header("X-User-Id", userId.toString())
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("ivan"));
  }
}
