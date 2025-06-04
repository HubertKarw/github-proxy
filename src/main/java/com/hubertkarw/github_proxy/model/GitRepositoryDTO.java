package com.hubertkarw.github_proxy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GitRepositoryDTO {
    private String fullName;
    private String description;
    private String cloneUrl;
    private Long stars;
    private LocalDateTime createdAt;
}
