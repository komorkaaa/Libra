package io.github.komorkaaa.meetflow.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CreateProfileRequest {

  @NotNull
  private UUID userId;

  @NotBlank
  private String username;

  private String avatarUrl;
  private String phone;
  private String preferencesJson;
}
