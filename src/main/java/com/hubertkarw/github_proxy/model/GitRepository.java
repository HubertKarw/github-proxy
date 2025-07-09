package com.hubertkarw.github_proxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class GitRepository {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private String description;
    private String cloneUrl;
    private Long stars;
    private LocalDateTime createdAt;

    public static String generateFullName(String owner, String repo) {
        return owner + "/" + repo;
    }

    public void update(GitRepository updated) {
        this.fullName = updated.getFullName();
        this.description = updated.getDescription();
        this.cloneUrl = updated.getCloneUrl();
        this.stars = updated.getStars();
        this.createdAt = updated.getCreatedAt();

    }
}
