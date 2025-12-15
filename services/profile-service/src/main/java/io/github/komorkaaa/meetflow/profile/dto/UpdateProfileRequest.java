package io.github.komorkaaa.meetflow.profile.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateProfileRequest {

  private String username;
  private String avatarUrl;
  private String phone;
  private String preferencesJson;

}
