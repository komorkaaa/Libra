package io.github.komorkaaa.libra.auth.dto;

import java.util.UUID;

public record CreateProfileInternalRequest(
        UUID userId,
        String email
) {}
