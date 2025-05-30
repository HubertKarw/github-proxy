package com.hubertkarw.github_proxy.controller;

import com.hubertkarw.github_proxy.service.GitRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/repos")
public class GitRepositoryController {
    private final GitRepositoryService service;
}
