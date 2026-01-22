package io.github.komorkaaa.libra.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileRequest {

  @NotNull
  private UUID userId;

  @NotBlank
  private String username;

  private String email;
  private String phone;
  private String preferencesJson;
}
