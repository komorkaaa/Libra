package io.github.komorkaaa.libra.profile.dto;

import java.util.UUID;

public record CreateProfileInternalRequest(
        UUID userId,
        String email
) {}
