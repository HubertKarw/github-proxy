package com.hubertkarw.github_proxy.controller;

import com.hubertkarw.github_proxy.feignClient.GithubRepositoryClient;
import com.hubertkarw.github_proxy.mapper.RepositoryStructMapper;
import com.hubertkarw.github_proxy.model.GitRepositoryDTO;
import com.hubertkarw.github_proxy.service.GitRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/repositories")
public class GitRepositoryController {
    private final GitRepositoryService service;
    private final GithubRepositoryClient client;
    private final RepositoryStructMapper mapper;

    @GetMapping("/{owner}/{repository}")
    GitRepositoryDTO getRepository(@PathVariable(name = "owner") String owner, @PathVariable(name = "repository")String repository){
        return mapper.mapToDTO(client.getGitRepository(owner,repository));
    }
}
