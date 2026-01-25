package io.github.komorkaaa.libra.auth.dto;

public record LoginRequest(
        String email,
        String password
) {}
