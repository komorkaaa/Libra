package io.github.komorkaaa.libra.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "users",
        schema = "auth",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User {

  @Id
  @Column(columnDefinition = "uuid", nullable = false)
  private UUID id;

  @Column(nullable = false, length = 255)
  private String email;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @Column(nullable = false)
  private boolean enabled;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @PrePersist
  public void prePersist() {
    if (id == null) {
      id = UUID.randomUUID();
    }
    if (createdAt == null) {
      createdAt = OffsetDateTime.now();
    }
    enabled = true;
  }
}

