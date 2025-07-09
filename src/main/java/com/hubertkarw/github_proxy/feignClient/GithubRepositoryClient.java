package com.hubertkarw.github_proxy.feignClient;


import com.hubertkarw.github_proxy.config.FeignClientConfig;
import com.hubertkarw.github_proxy.model.GitRepositoryInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "github", configuration = {FeignClientConfig.class})
public interface GithubRepositoryClient {

    @GetMapping("/repos/{owner}/{repository}")
    GitRepositoryInfo getGitRepository(
            @PathVariable("owner") String owner,
            @PathVariable("repository") String repo
    );
}
