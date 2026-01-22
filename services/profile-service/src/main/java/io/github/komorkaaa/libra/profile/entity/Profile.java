package io.github.komorkaaa.meetflow.profile.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Table(name = "profiles", uniqueConstraints = {@UniqueConstraint(columnNames = "user_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

  @Id
  @Column(columnDefinition = "uuid")
  private UUID id;

  @Column(name = "user_id", columnDefinition = "uuid", unique = true, nullable = false)
  private UUID userId;

  @Column(name = "username", length = 50, nullable = false)
  private String username;

  @Column(name = "avatar_url", length = 255)
  private String avatarUrl;

  @Column(length = 20)
  private String phone;

  @Column(columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private String preferences;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;

  @PrePersist
  public void prePersist() {
    if (id == null) id = UUID.randomUUID();
    createdAt = OffsetDateTime.now();
    updatedAt = createdAt;
  }

  @PreUpdate
  public void preUpdate() {
    updatedAt = OffsetDateTime.now();
  }
}
