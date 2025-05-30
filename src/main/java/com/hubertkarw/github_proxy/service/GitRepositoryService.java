package com.hubertkarw.github_proxy.service;

import com.hubertkarw.github_proxy.repository.GitRepositoryJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GitRepositoryService {
    private final GitRepositoryJpaRepository repository;
}
