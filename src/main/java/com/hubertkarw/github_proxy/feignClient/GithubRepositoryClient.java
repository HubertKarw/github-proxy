package com.hubertkarw.github_proxy.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "github", url = "https://api.github.com")
public class GithubRepositoryClient {
}
