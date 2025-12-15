package io.github.komorkaaa.meetflow.profile.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class ProfileResponse {

  private UUID id;
  private UUID userId;
  private String username;
  private String avatarUrl;
  private String phone;
  private String preferencesJson;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;

}
