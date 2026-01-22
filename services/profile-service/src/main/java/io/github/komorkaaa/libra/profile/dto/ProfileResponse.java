package io.github.komorkaaa.libra.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

  private UUID id;
  private UUID userId;
  private String username;
  private String email;
  private String phone;
  private String preferencesJson;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;

}
