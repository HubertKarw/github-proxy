package com.hubertkarw.github_proxy.controller;

import com.hubertkarw.github_proxy.feignClient.GithubRepositoryClient;
import com.hubertkarw.github_proxy.mapper.RepositoryStructMapper;
import com.hubertkarw.github_proxy.model.GitRepositoryDTO;
import com.hubertkarw.github_proxy.service.GitRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/repositories")
public class GitRepositoryController {
    private final GitRepositoryService service;

    @GetMapping("/{owner}/{repository}")
    public GitRepositoryDTO getRepository(@PathVariable(name = "owner") String owner, @PathVariable(name = "repository")String repository){
        return service.getRepositoryInfo(owner,repository);
    }

    @PostMapping("/{owner}/{repository}")
    public GitRepositoryDTO saveRepo(@PathVariable(name = "owner") String owner, @PathVariable(name = "repository")String repository){
        return service.saveRepo(owner,repository);
    }

    @PutMapping("/{owner}/{repository}")
    public GitRepositoryDTO updateRepo(@PathVariable(name = "owner") String owner, @PathVariable(name = "repository")String repository){
        return service.updateRepo(owner,repository);
    }

    @DeleteMapping("/{owner}/{repository}")
    public void deleteRepo(@PathVariable(name = "owner") String owner, @PathVariable(name = "repository")String repository){
        service.deleteRepo(owner,repository);
    }
}
