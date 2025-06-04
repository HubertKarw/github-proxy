package com.hubertkarw.github_proxy.controller;

import com.hubertkarw.github_proxy.model.GitRepositoryDTO;
import com.hubertkarw.github_proxy.service.GitRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/local/repositories")
public class GitLocalRepositoryController {
    private final GitRepositoryService service;

    @GetMapping("/{owner}/{repository}")
    public GitRepositoryDTO getLocalRepo(@PathVariable(name = "owner") String owner, @PathVariable(name = "repository") String repository) {
        return service.getLocalRepo(owner, repository);
    }
}
