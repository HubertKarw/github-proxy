package com.hubertkarw.github_proxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class CreateGitRepositoryCommand {
    private String fullName;
    private String description;
    private String cloneURL;
    private long stars;
    private LocalDateTime createdAt;
}
