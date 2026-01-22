package io.github.komorkaaa.libra.profile.repository;

import io.github.komorkaaa.libra.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
  Optional<Profile> findByUserId(UUID userId);
  boolean existsByUserId(UUID userId);
}
