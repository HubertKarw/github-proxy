package com.hubertkarw.github_proxy.service;

import com.hubertkarw.github_proxy.feignClient.GithubRepositoryClient;
import com.hubertkarw.github_proxy.mapper.RepositoryStructMapper;
import com.hubertkarw.github_proxy.mapper.RepositoryStructMapperImpl;
import com.hubertkarw.github_proxy.model.GitRepositoryDTO;
import com.hubertkarw.github_proxy.model.GitRepositoryInfo;
import com.hubertkarw.github_proxy.repository.GitRepositoryJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GitRepositoryServiceTest {
    GithubRepositoryClient client;
    RepositoryStructMapper mapper;
    GitRepositoryJpaRepository jpaRepository;
    GitRepositoryService service;

    @BeforeEach
    void setup() {
        this.jpaRepository = Mockito.mock(GitRepositoryJpaRepository.class);
        this.client = Mockito.mock(GithubRepositoryClient.class);
        this.mapper = new RepositoryStructMapperImpl();
        this.service = new GitRepositoryService(jpaRepository, client, mapper);
    }

    @Test
    void getRepositoryInfo_repositoryExists_repositoryInfoReturned() {
        //given
        GitRepositoryInfo info = new GitRepositoryInfo("xxx/yyy", "desc", "clone.url/123", 1L, LocalDateTime.of(2022, 12, 11, 11, 11, 11));
        when(client.getGitRepository(any(), any())).thenReturn(info);
        //when
        GitRepositoryDTO result = service.getRepositoryInfo("xxx", "yyy");
        //then
        assertAll(
                () -> assertEquals("xxx/yyy", result.getFullName()),
                () -> assertEquals("desc", result.getDescription()),
                () -> assertEquals("clone.url/123", result.getCloneUrl()),
                () -> assertEquals(1L, result.getStars()),
                () -> assertEquals(LocalDateTime.of(2022, 12, 11, 11, 11, 11), result.getCreatedAt())
        );
    }
}
