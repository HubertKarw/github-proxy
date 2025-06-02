package com.hubertkarw.github_proxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitRepositoryInfo {
    private String fullName;
    private String description;
    private String cloneURL;
    private long stars;
    private LocalDateTime createdAt;
}
